package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.SolutionMode
import com.example.ui.SolutionState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SolutionCard(
    state: SolutionState,
    onExplainSimpler: () -> Unit,
    onExplainInEnglish: () -> Unit,
    onSendWhatsApp: () -> Unit,
    onResetOriginal: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showFullImageDialog by remember { mutableStateOf(false) }

    if (showFullImageDialog && state.questionBitmap != null) {
        Dialog(onDismissRequest = { showFullImageDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = state.questionBitmap.asImageBitmap(),
                        contentDescription = "প্রশ্নের বড় ছবি",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { showFullImageDialog = false }) {
                        Text("বন্ধ করুন")
                    }
                }
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("solution_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.5.dp, Color(0xFF90CAF9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header: Solution Status & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI সমাধান",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "এম রহমান স্যারের এআই সমাধান",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D47A1)
                    )
                    Text(
                        text = when (state.currentMode) {
                            SolutionMode.ORIGINAL -> "ধাপে ধাপে বিস্তারিত সমাধান (বাংলা)"
                            SolutionMode.SIMPLER_BENGALI -> "ছোটদের উপযোগী আরও সহজ ভাষায় ব্যাখ্যা"
                            SolutionMode.ENGLISH -> "English Step-by-Step Explanation"
                        },
                        fontSize = 11.5.sp,
                        color = Color(0xFF0288D1),
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("dismiss_solution_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "মুছে ফেলুন",
                        tint = Color(0xFF64748B)
                    )
                }
            }

            // Uploaded Image Thumbnail Preview
            if (state.questionBitmap != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF1F5F9))
                        .clickable { showFullImageDialog = true }
                        .padding(8.dp)
                ) {
                    Image(
                        bitmap = state.questionBitmap.asImageBitmap(),
                        contentDescription = "প্রশ্নের থাম্বনেল",
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "আপলোডকৃত প্রশ্নের ছবি",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "ছবিটি বড় করে দেখতে এখানে চাপ দিন",
                            fontSize = 11.sp,
                            color = Color(0xFF0288D1)
                        )
                    }
                }
            } else if (state.questionPrompt.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "প্রশ্ন: ${state.questionPrompt}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Loading State
            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF0D47A1),
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.loadingStep.ifBlank { "সমাধান তৈরি হচ্ছে..." },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D47A1)
                    )
                    Text(
                        text = "স্যারের এআই নির্ভুল সমাধান ও নিয়ম তৈরি করছে",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            } else if (state.errorMessage != null && state.solutionText.isBlank()) {
                // Error State
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF1F2),
                    border = BorderStroke(1.dp, Color(0xFFFECDD3)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "একটি সমস্যা হয়েছে",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFBE123C)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = state.errorMessage,
                            fontSize = 12.5.sp,
                            color = Color(0xFF881337)
                        )
                    }
                }
            } else {
                // Render Solution Content
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Copy button top row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFE3F2FD)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF0D47A1),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "যাচাইকৃত সমাধান",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0D47A1)
                                    )
                                }
                            }

                            FilledTonalButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("সমাধান", state.solutionText)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "সমাধান কপি করা হয়েছে!", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .height(32.dp)
                                    .testTag("copy_solution_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "কপি",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("কপি", fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Solution Text
                        Text(
                            text = state.solutionText,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.testTag("solution_text_view")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // The 3 MANDATORY BUTTONS
                // 1. "আরো সহজ করে বোঝাও"
                // 2. "ইংরেজিতে বলো"
                // 3. "WhatsApp এ স্যারকে পাঠাও" (wa.me/919851374847)
                Text(
                    text = "পরবর্তী পদক্ষেপ নিন:",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Row of 2 follow-up explanation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Button 1: "আরো সহজ করে বোঝাও"
                    Button(
                        onClick = onExplainSimpler,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("explain_simpler_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.currentMode == SolutionMode.SIMPLER_BENGALI) Color(0xFF00695C) else Color(0xFF0288D1)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "সহজ ব্যাখ্যা",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "আরো সহজ করে বোঝাও",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1
                        )
                    }

                    // Button 2: "ইংরেজিতে বলো"
                    Button(
                        onClick = onExplainInEnglish,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("explain_english_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.currentMode == SolutionMode.ENGLISH) Color(0xFF4527A0) else Color(0xFF1565C0)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "ইংরেজি",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ইংরেজিতে বলো",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Button 3: "WhatsApp এ স্যারকে পাঠাও" -> opens wa.me/919851374847
                Button(
                    onClick = onSendWhatsApp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("send_whatsapp_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366) // Official WhatsApp Green
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "WhatsApp এ স্যারকে পাঠাও",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // If in non-original mode, allow viewing original solution again
                if (state.currentMode != SolutionMode.ORIGINAL) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onResetOriginal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "পুনরায় মূল সমাধান",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("মূল সমাধানে ফিরে যান", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
