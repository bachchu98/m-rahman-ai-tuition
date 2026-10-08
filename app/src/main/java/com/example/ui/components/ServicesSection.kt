package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LaptopChromebook
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CoachingData
import com.example.data.TuitionService

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServicesSection(
    services: List<TuitionService>,
    selectedService: TuitionService?,
    onServiceClick: (TuitionService) -> Unit,
    onDismissServiceDialog: () -> Unit,
    onContactWhatsApp: (String) -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Detail Dialog when a card is clicked
    if (selectedService != null) {
        ServiceDetailDialog(
            service = selectedService,
            onDismiss = onDismissServiceDialog,
            onContactWhatsApp = { onContactWhatsApp("আমি '${selectedService.titleBengali}' সেবাটি সম্পর্কে জানতে আগ্রহী।") },
            onCallClick = onCallClick
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("services_section")
    ) {
        // Section Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFF0D47A1),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "আমাদের সেবা",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = "আমাদের সেবা",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.testTag("services_section_heading")
                )
                Text(
                    text = "কালিয়াচকের শিক্ষার্থীদের জন্য সর্বাধুনিক শিক্ষা ব্যবস্থা",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4 Service Cards Grid (2x2 layout on mobile)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val chunked = services.chunked(2)
            chunked.forEachIndexed { rowIndex, rowServices ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowServices.forEach { service ->
                        ServiceCardItem(
                            service = service,
                            onClick = { onServiceClick(service) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowServices.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCardItem(
    service: TuitionService,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon, iconBgColor, iconTint) = when (service.iconType) {
        "school" -> Triple(Icons.Default.School, Color(0xFFE3F2FD), Color(0xFF0D47A1))
        "ai" -> Triple(Icons.Default.Psychology, Color(0xFFEDE7F6), Color(0xFF5E35B1))
        "notes" -> Triple(Icons.Default.Description, Color(0xFFFFF3E0), Color(0xFFE65100))
        "online" -> Triple(Icons.Default.LaptopChromebook, Color(0xFFE8F5E9), Color(0xFF2E7D32))
        else -> Triple(Icons.Default.School, Color(0xFFE3F2FD), Color(0xFF0D47A1))
    }

    Card(
        modifier = modifier
            .clickable { onClick() }
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Top Badge & Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = iconBgColor,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = service.titleBengali,
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = service.badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title Bengali
            Text(
                text = service.titleBengali,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                maxLines = 1
            )

            // English Sub-title
            Text(
                text = service.titleEnglish,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0288D1)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle Bengali
            Text(
                text = service.subtitleBengali,
                fontSize = 11.5.sp,
                color = Color(0xFF64748B),
                lineHeight = 16.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tap hint
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "বিস্তারিত দেখুন →",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
            }
        }
    }
}

@Composable
fun ServiceDetailDialog(
    service: TuitionService,
    onDismiss: () -> Unit,
    onContactWhatsApp: () -> Unit,
    onCallClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header with close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = service.titleBengali,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )
                        Text(
                            text = service.titleEnglish,
                            fontSize = 12.sp,
                            color = Color(0xFF0288D1),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "বন্ধ",
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Full Description
                Text(
                    text = service.descriptionBengali,
                    fontSize = 13.5.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF334155)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Highlights
                Text(
                    text = "বিশেষ সুবিধাসমূহ:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(6.dp))

                service.highlights.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item,
                            fontSize = 12.5.sp,
                            color = Color(0xFF475569)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Contact Actions
                Button(
                    onClick = onContactWhatsApp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "WhatsApp এ ভর্তি ও বিস্তারিত জানুন",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF0D47A1))
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "কল করুন",
                        tint = Color(0xFF0D47A1),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "সরাসরি ফোন করুন: ${CoachingData.PHONE_NUMBER}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D47A1)
                    )
                }
            }
        }
    }
}
