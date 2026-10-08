package com.example.data

import android.graphics.Bitmap
import android.util.Base64
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class GeminiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val modelName = "gemini-3.5-flash"
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"

    private val systemInstructionBengali = """
        তুমি কালিয়াচক, মালদার 'M Rahman AI Tuition - AI Hub And Knowledge' এর হেড টিউটর ও শিক্ষক এম রহমান স্যারের প্রধান এআই সহযোগী।
        তোমার কাজ হলো ৫ম থেকে ১২শ শ্রেণীর ছাত্রছাত্রীদের গণিত, পদার্থবিদ্যা, রসায়ন ও বিজ্ঞানের যেকোনো প্রশ্নের সমাধান অত্যন্ত সহজ, নির্ভুল ও পরিষ্কার বাংলা ভাষায় ধাপে ধাপে বুঝিয়ে দেওয়া।
        
        উত্তর উপস্থাপনের নিয়ম:
        ১. প্রথমে প্রশ্নটি সংক্ষেপে উল্লেখ করো (যদি ছবিতে থাকে)।
        ২. ধাপ ১: কী কী দেওয়া আছে (প্রদত্ত মান/উপাত্ত)।
        ৩. ধাপ ২: প্রয়োজনীয় সূত্র ও নিয়ম (সহজ ব্যাখ্যা সহ)।
        ৪. ধাপ ৩: বিস্তারিত সমাধান (প্রতিটি ক্যালকুলেশন ধাপে ধাপে)।
        ৫. ধাপ ৪: চূড়ান্ত উত্তর (বক্স বা বোল্ড করে)।
        ৬. ধাপ ৫: মনে রাখার স্পেশাল টিপ্স / শর্টকাট (যদি থাকে)।
        
        ভাষা সহজ বাংলা রাখবে, প্রয়োজনে সাধারণ ছাত্রছাত্রীদের সুবিধার জন্য পরিচিত পরিভাষা ব্যবহার করবে। ছাত্রছাত্রীদের সবসময় উৎসাহিত করবে।
    """.trimIndent()

    suspend fun solveQuestionWithImage(
        bitmap: Bitmap,
        userPrompt: String? = null
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext Result.failure(
                    IllegalStateException("API_KEY_MISSING")
                )
            }

            val base64Image = bitmapToBase64(bitmap)
            val promptText = userPrompt?.takeIf { it.isNotBlank() }
                ?: "অনুগ্রহ করে এই ছবির অংক বা বিজ্ঞানের প্রশ্নটি সনাক্ত করো এবং সহজ বাংলায় সম্পূর্ণ সমাধান ধাপে ধাপে বুঝিয়ে দাও।"

            val requestJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", promptText)
                            })
                            put(JSONObject().apply {
                                put("inlineData", JSONObject().apply {
                                    put("mimeType", "image/jpeg")
                                    put("data", base64Image)
                                })
                            })
                        }
                        put("parts", partsArray)
                    }
                    put(contentObj)
                }
                put("contents", contentsArray)

                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemInstructionBengali)
                        })
                    })
                })

                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.3)
                })
            }

            executeGeminiRequest(apiKey, requestJson)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun explainSimpler(
        originalQuestion: String,
        currentSolution: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext Result.failure(
                    IllegalStateException("API_KEY_MISSING")
                )
            }

            val prompt = """
                নিচের প্রশ্ন এবং সমাধানটি দেখো:
                প্রশ্ন: $originalQuestion
                পূর্ববর্তী সমাধান: $currentSolution
                
                অনুরোধ: এই সমাধানটি আরও অনেক বেশি সহজ ভাষায়, খুব সাধারণ বাস্তব জীবনের উদাহরণ দিয়ে আরও সহজ করে বুঝিয়ে দাও যাতে ৫ম-৭ম শ্রেণীর একজন শিক্ষার্থীও খুব সহজে বুঝতে পারে। কোন কঠিন ভাষা ব্যবহার করবে না। একদম সহজ ও প্রাঞ্জল বাংলায় পয়েন্ট করে লেখো।
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemInstructionBengali)
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.4)
                })
            }

            executeGeminiRequest(apiKey, requestJson)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun explainInEnglish(
        originalQuestion: String,
        currentSolution: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext Result.failure(
                    IllegalStateException("API_KEY_MISSING")
                )
            }

            val prompt = """
                Question: $originalQuestion
                Previous Solution: $currentSolution
                
                Request: Please explain this problem and solution completely in clear, polite, and student-friendly English. Provide:
                1. Given Information
                2. Key Formula / Concept Used
                3. Step-by-Step Mathematical/Scientific Derivation and Solution
                4. Final Answer (highlighted)
                5. Quick Concept Tip for Exams.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.3)
                })
            }

            executeGeminiRequest(apiKey, requestJson)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun solveTextQuestion(
        questionText: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext Result.failure(
                    IllegalStateException("API_KEY_MISSING")
                )
            }

            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", questionText)
                            })
                        })
                    })
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemInstructionBengali)
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.3)
                })
            }

            executeGeminiRequest(apiKey, requestJson)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun executeGeminiRequest(apiKey: String, jsonPayload: JSONObject): Result<String> {
        val url = "$baseUrl/$modelName:generateContent?key=$apiKey"
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonPayload.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("HTTP ${response.code}: $responseBody")
                )
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates == null || candidates.length() == 0) {
                return Result.failure(Exception("কোনো উত্তর পাওয়া যায়নি"))
            }

            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")

            return if (!text.isNullOrBlank()) {
                Result.success(text)
            } else {
                Result.failure(Exception("উত্তরের টেক্সট খালি এসেছে"))
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        // Resize if excessively large to keep request fast and well within payload limits
        val maxDim = 1280
        val scaledBitmap = if (bitmap.width > maxDim || bitmap.height > maxDim) {
            val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val newWidth = if (ratio > 1f) maxDim else (maxDim * ratio).toInt()
            val newHeight = if (ratio > 1f) (maxDim / ratio).toInt() else maxDim
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}
