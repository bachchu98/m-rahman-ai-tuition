package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CoachingData
import com.example.ui.components.FooterSection
import com.example.ui.components.HeaderSection
import com.example.ui.components.ServicesSection
import com.example.ui.components.SolutionCard
import com.example.ui.components.UploadSection

@Composable
fun TuitionApp(
    viewModel: TuitionViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val solutionState = uiState.solutionState

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF8FAFC),
        contentWindowInsets = WindowInsets.statusBars,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openWhatsAppToSir(context) },
                containerColor = Color(0xFF25D366),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .testTag("fab_whatsapp")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp এ স্যারের সাথে চ্যাট করুন",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 640.dp)
                    .testTag("main_tuition_scroll_view"),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Header Section
                item {
                    HeaderSection(
                        onCallClick = { viewModel.callSir(context) },
                        onWhatsAppClick = { viewModel.openWhatsAppToSir(context) }
                    )
                }

                // 2. Upload Section (Big button "প্রশ্নের ছবি আপলোড করো")
                item {
                    UploadSection(
                        onImageSelected = { bitmap ->
                            viewModel.onImageSelected(bitmap)
                        },
                        onTextQuestionSubmit = { text ->
                            viewModel.onTextQuestionSubmit(text)
                        },
                        onSampleSelected = { sample ->
                            viewModel.onSampleQuestionSelected(sample)
                        }
                    )
                }

                // 3. Solution Card (shows when loading or solution available)
                if (solutionState.isLoading || solutionState.solutionText.isNotBlank() || solutionState.errorMessage != null) {
                    item {
                        SolutionCard(
                            state = solutionState,
                            onExplainSimpler = { viewModel.explainSimpler() },
                            onExplainInEnglish = { viewModel.explainInEnglish() },
                            onSendWhatsApp = { viewModel.openWhatsAppToSir(context) },
                            onResetOriginal = { viewModel.resetToOriginalSolution() },
                            onDismiss = { viewModel.clearSolution() }
                        )
                    }
                }

                // 4. "আমাদের সেবা" (4 cards: Class 5-12 Tuition, AI Education, Notes, Online Class)
                item {
                    ServicesSection(
                        services = CoachingData.services,
                        selectedService = uiState.selectedService,
                        onServiceClick = { service -> viewModel.selectService(service) },
                        onDismissServiceDialog = { viewModel.selectService(null) },
                        onContactWhatsApp = { msg -> viewModel.openWhatsAppToSir(context, msg) },
                        onCallClick = { viewModel.callSir(context) }
                    )
                }

                // 5. Footer Section ("Made by M Rahman | AI Hub And Knowledge And Education")
                item {
                    FooterSection(
                        onCallClick = { viewModel.callSir(context) },
                        onWhatsAppClick = { viewModel.openWhatsAppToSir(context) }
                    )
                }

                // Bottom spacer for FAB clearance
                item {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}
