package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CoachingData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeaderSection(
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("header_section_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D47A1), // Deep Royal Blue
                            Color(0xFF1565C0)  // Cobalt Blue
                        )
                    )
                )
        ) {
            // Optional hero banner image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coaching_hero_1791455074905),
                    contentDescription = "কোচিং ব্যানার",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xCC0D47A1)
                                )
                            )
                        )
                )

                // Verified Badge Top Right
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xDDFFFFFF)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "ভেরিফায়েড",
                            tint = Color(0xFF0D47A1),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "কালিয়াচকের বিশ্বস্ত AI টিউটর",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )
                    }
                }
            }

            // Main Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Logo Icon Avatar
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(54.dp),
                        shadowElevation = 3.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "টিউটর লোগো",
                                tint = Color(0xFF0D47A1),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = CoachingData.INSTITUTE_NAME,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                modifier = Modifier.testTag("app_title_header")
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Powered",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        // Subtitle: AI Hub And Knowledge | Kaliachak, Malda | Call: 9851374847
                        Text(
                            text = CoachingData.SUBTITLE_TEXT,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFE3F2FD),
                            lineHeight = 17.sp,
                            modifier = Modifier.testTag("app_subtitle_header")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action chips: Call, WhatsApp, Location
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Call Button
                    AssistChip(
                        onClick = onCallClick,
                        label = {
                            Text(
                                text = "Call: ${CoachingData.PHONE_NUMBER}",
                                color = Color(0xFF0D47A1),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "ফোন করুন",
                                tint = Color(0xFF0D47A1),
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("header_call_chip")
                    )

                    // WhatsApp Button
                    AssistChip(
                        onClick = onWhatsAppClick,
                        label = {
                            Text(
                                text = "WhatsApp স্যার",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFF25D366) // WhatsApp green
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("header_whatsapp_chip")
                    )

                    // Location Badge
                    AssistChip(
                        onClick = { /* Informational */ },
                        label = {
                            Text(
                                text = "কালিয়াচক, মালদা",
                                color = Color(0xFFE3F2FD),
                                fontSize = 11.5.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "স্থান",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0x33FFFFFF)
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("header_location_chip")
                    )
                }
            }
        }
    }
}
