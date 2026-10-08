package com.example.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.CoachingData
import com.example.data.GeminiService
import com.example.data.SampleData
import com.example.data.SampleQuestion
import com.example.data.TuitionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SolutionState(
    val questionBitmap: Bitmap? = null,
    val questionPrompt: String = "",
    val solutionText: String = "",
    val originalSolution: String = "",
    val currentMode: SolutionMode = SolutionMode.ORIGINAL,
    val isLoading: Boolean = false,
    val loadingStep: String = "",
    val errorMessage: String? = null,
    val currentSampleId: String? = null
)

enum class SolutionMode {
    ORIGINAL,
    SIMPLER_BENGALI,
    ENGLISH
}

data class TuitionUiState(
    val solutionState: SolutionState = SolutionState(),
    val selectedService: TuitionService? = null,
    val isApiKeyPresent: Boolean = false,
    val toastMessage: String? = null
)

class TuitionViewModel(
    private val geminiService: GeminiService = GeminiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TuitionUiState())
    val uiState: StateFlow<TuitionUiState> = _uiState.asStateFlow()

    init {
        val key = BuildConfig.GEMINI_API_KEY
        val hasKey = key.isNotBlank() && key != "MY_GEMINI_API_KEY"
        _uiState.update { it.copy(isApiKeyPresent = hasKey) }
    }

    fun onImageSelected(bitmap: Bitmap, customPrompt: String? = null) {
        val prompt = customPrompt?.takeIf { it.isNotBlank() } ?: "ছবির প্রশ্নটির সমাধান"
        _uiState.update {
            it.copy(
                solutionState = SolutionState(
                    questionBitmap = bitmap,
                    questionPrompt = prompt,
                    isLoading = true,
                    loadingStep = "ছবিটি স্ক্যান ও বিশ্লেষণ করা হচ্ছে...",
                    currentMode = SolutionMode.ORIGINAL
                )
            )
        }

        viewModelScope.launch {
            val result = geminiService.solveQuestionWithImage(bitmap, customPrompt)
            result.onSuccess { solution ->
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = solution,
                            originalSolution = solution,
                            isLoading = false,
                            loadingStep = "",
                            errorMessage = null
                        )
                    )
                }
            }.onFailure { error ->
                handleFailure(error, isImage = true, fallbackSample = null)
            }
        }
    }

    fun onSampleQuestionSelected(sample: SampleQuestion) {
        _uiState.update {
            it.copy(
                solutionState = SolutionState(
                    questionPrompt = sample.question,
                    isLoading = true,
                    loadingStep = "স্যারের এআই দিয়ে '${sample.subject}' এর প্রশ্ন সমাধান হচ্ছে...",
                    currentSampleId = sample.id,
                    currentMode = SolutionMode.ORIGINAL
                )
            )
        }

        viewModelScope.launch {
            val result = geminiService.solveTextQuestion(sample.question)
            result.onSuccess { solution ->
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = solution,
                            originalSolution = solution,
                            isLoading = false,
                            loadingStep = "",
                            errorMessage = null
                        )
                    )
                }
            }.onFailure { error ->
                // If API key is missing or network fails, provide the pre-verified educational solution
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = sample.sampleSolutionBengali,
                            originalSolution = sample.sampleSolutionBengali,
                            isLoading = false,
                            loadingStep = "",
                            errorMessage = if (error.message == "API_KEY_MISSING") {
                                "স্যারের লাইভ ডেমো সমাধান প্রদর্শিত হচ্ছে। লাইভ জেনারেটিভ AI এর জন্য Secrets প্যানেলে Gemini API Key সেট করতে পারেন।"
                            } else null
                        )
                    )
                }
            }
        }
    }

    fun onTextQuestionSubmit(question: String) {
        if (question.isBlank()) return
        _uiState.update {
            it.copy(
                solutionState = SolutionState(
                    questionPrompt = question,
                    isLoading = true,
                    loadingStep = "প্রশ্নটির সমাধান তৈরি করা হচ্ছে...",
                    currentMode = SolutionMode.ORIGINAL
                )
            )
        }

        viewModelScope.launch {
            val result = geminiService.solveTextQuestion(question)
            result.onSuccess { solution ->
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = solution,
                            originalSolution = solution,
                            isLoading = false,
                            loadingStep = "",
                            errorMessage = null
                        )
                    )
                }
            }.onFailure { error ->
                handleFailure(error, isImage = false, fallbackSample = null)
            }
        }
    }

    fun explainSimpler() {
        val currentState = _uiState.value.solutionState
        if (currentState.solutionText.isBlank()) return

        // If sample question has pre-baked simpler text and API key is missing
        val sample = SampleData.sampleQuestions.find { it.id == currentState.currentSampleId }
        val apiKeyMissing = !_uiState.value.isApiKeyPresent

        if (apiKeyMissing && sample != null) {
            _uiState.update {
                it.copy(
                    solutionState = currentState.copy(
                        solutionText = sample.simplerBengali,
                        currentMode = SolutionMode.SIMPLER_BENGALI
                    )
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                solutionState = currentState.copy(
                    isLoading = true,
                    loadingStep = "আরও সহজ ভাষায় ছোটদের মতো করে তৈরি করা হচ্ছে..."
                )
            )
        }

        viewModelScope.launch {
            val result = geminiService.explainSimpler(
                originalQuestion = currentState.questionPrompt,
                currentSolution = currentState.originalSolution
            )
            result.onSuccess { simplerText ->
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = simplerText,
                            currentMode = SolutionMode.SIMPLER_BENGALI,
                            isLoading = false,
                            loadingStep = ""
                        )
                    )
                }
            }.onFailure { error ->
                if (sample != null) {
                    _uiState.update { state ->
                        state.copy(
                            solutionState = state.solutionState.copy(
                                solutionText = sample.simplerBengali,
                                currentMode = SolutionMode.SIMPLER_BENGALI,
                                isLoading = false,
                                loadingStep = ""
                            )
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            solutionState = state.solutionState.copy(
                                isLoading = false,
                                errorMessage = "সহজ ব্যাখ্যা তৈরিতে সমস্যা হয়েছে: ${error.localizedMessage}"
                            )
                        )
                    }
                }
            }
        }
    }

    fun explainInEnglish() {
        val currentState = _uiState.value.solutionState
        if (currentState.solutionText.isBlank()) return

        val sample = SampleData.sampleQuestions.find { it.id == currentState.currentSampleId }
        val apiKeyMissing = !_uiState.value.isApiKeyPresent

        if (apiKeyMissing && sample != null) {
            _uiState.update {
                it.copy(
                    solutionState = currentState.copy(
                        solutionText = sample.englishSolution,
                        currentMode = SolutionMode.ENGLISH
                    )
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                solutionState = currentState.copy(
                    isLoading = true,
                    loadingStep = "সম্পূর্ণ সমাধান ইংরেজিতে অনুবাদ ও ব্যাখ্যা করা হচ্ছে..."
                )
            )
        }

        viewModelScope.launch {
            val result = geminiService.explainInEnglish(
                originalQuestion = currentState.questionPrompt,
                currentSolution = currentState.originalSolution
            )
            result.onSuccess { englishText ->
                _uiState.update { state ->
                    state.copy(
                        solutionState = state.solutionState.copy(
                            solutionText = englishText,
                            currentMode = SolutionMode.ENGLISH,
                            isLoading = false,
                            loadingStep = ""
                        )
                    )
                }
            }.onFailure { error ->
                if (sample != null) {
                    _uiState.update { state ->
                        state.copy(
                            solutionState = state.solutionState.copy(
                                solutionText = sample.englishSolution,
                                currentMode = SolutionMode.ENGLISH,
                                isLoading = false,
                                loadingStep = ""
                            )
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            solutionState = state.solutionState.copy(
                                isLoading = false,
                                errorMessage = "ইংরেজি ব্যাখ্যা তৈরিতে সমস্যা হয়েছে: ${error.localizedMessage}"
                            )
                        )
                    }
                }
            }
        }
    }

    fun resetToOriginalSolution() {
        val currentState = _uiState.value.solutionState
        if (currentState.originalSolution.isNotBlank()) {
            _uiState.update {
                it.copy(
                    solutionState = currentState.copy(
                        solutionText = currentState.originalSolution,
                        currentMode = SolutionMode.ORIGINAL
                    )
                )
            }
        }
    }

    fun openWhatsAppToSir(context: Context, extraMessage: String? = null) {
        try {
            val current = _uiState.value.solutionState
            val baseMessage = buildString {
                append("আসসালামু আলাইকুম স্যার,\nআমি M Rahman AI Tuition অ্যাপ থেকে যোগাযোগ করছি।\n\n")
                if (extraMessage != null) {
                    append(extraMessage)
                } else {
                    if (current.questionPrompt.isNotBlank()) {
                        append("আমার প্রশ্ন:\n${current.questionPrompt.take(150)}\n\n")
                    }
                    if (current.solutionText.isNotBlank()) {
                        append("এআই এর সমাধান সম্পর্কে স্যার আপনার পরামর্শ বা ক্লাসে বিস্তারিত জানতে চাই।")
                    } else {
                        append("স্যার আপনার কোচিং ক্লাসে ভর্তির বিষয়ে কথা বলতে চাই।")
                    }
                }
            }
            val encodedMsg = Uri.encode(baseMessage)
            val url = "https://wa.me/${CoachingData.WHATSAPP_NUMBER}?text=$encodedMsg"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp ওপেন করা যায়নি: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun callSir(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${CoachingData.PHONE_NUMBER}")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "কল করা সম্ভব হয়নি: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectService(service: TuitionService?) {
        _uiState.update { it.copy(selectedService = service) }
    }

    fun clearSolution() {
        _uiState.update { it.copy(solutionState = SolutionState()) }
    }

    private fun handleFailure(error: Throwable, isImage: Boolean, fallbackSample: SampleQuestion?) {
        val isApiKey = error.message == "API_KEY_MISSING" || error.message?.contains("API_KEY_INVALID") == true
        val errorMsg = if (isApiKey) {
            "Gemini API কী প্রয়োজন। AI Studio-র Secrets প্যানেলে GEMINI_API_KEY কনফিগার করুন। অথবা নিচের প্রস্তুতকৃত নমুনা প্রশ্নগুলো ট্রাই করুন।"
        } else {
            "সমাধান তৈরি করতে সমস্যা হয়েছে: ${error.localizedMessage ?: "দয়া করে আবার চেষ্টা করুন"}"
        }

        _uiState.update { state ->
            state.copy(
                solutionState = state.solutionState.copy(
                    isLoading = false,
                    loadingStep = "",
                    errorMessage = errorMsg,
                    solutionText = if (isApiKey && fallbackSample != null) fallbackSample.sampleSolutionBengali else ""
                )
            )
        }
    }
}
