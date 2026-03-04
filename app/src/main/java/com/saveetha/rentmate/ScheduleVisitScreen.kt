package com.saveetha.rentmate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleVisitScreen(
    listingId: String,
    onBackClick: () -> Unit,
    onConfirmClick: (String) -> Unit // returns visitId
) {
    val listing = ListingRepository.getListingById(listingId)
    val tealColor = Color(0xFF009688)

    // State for selection
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }

    // Sample Data
    val dates = listOf("Mon, Jan 15", "Tue, Jan 16", "Wed, Jan 17", "Thu, Jan 18")
    val times = listOf("10:00 AM", "2:00 PM", "4:00 PM", "6:00 PM")

    if (listing == null) return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schedule Visit") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 16.dp,
                color = Color.White
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = {
                            if (selectedDate != null && selectedTime != null) {
                                // Add visit to repository
                                val visitId = UUID.randomUUID().toString()
                                val visit = Visit(
                                    id = visitId,
                                    propertyId = listing.id,
                                    propertyTitle = listing.title,
                                    propertyAddress = "${listing.address}, ${listing.city}",
                                    date = selectedDate!!,
                                    time = selectedTime!!
                                )
                                ListingRepository.addVisit(visit)
                                onConfirmClick(visitId)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = selectedDate != null && selectedTime != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5D4037), // Dark brown/grey color from mockup
                            disabledContainerColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Confirm Visit", color = Color.White)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Property Summary Card
            Surface(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = listing.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${listing.address}, ${listing.city}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Select Date
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = tealColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Date", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                // 2x2 Grid for Dates
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DateOption(dates[0], selectedDate == dates[0], Modifier.weight(1f)) { selectedDate = dates[0] }
                        DateOption(dates[1], selectedDate == dates[1], Modifier.weight(1f)) { selectedDate = dates[1] }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DateOption(dates[2], selectedDate == dates[2], Modifier.weight(1f)) { selectedDate = dates[2] }
                        DateOption(dates[3], selectedDate == dates[3], Modifier.weight(1f)) { selectedDate = dates[3] }
                    }
                }
            }

            // Select Time
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = tealColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Time", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                // 2x2 Grid for Times
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DateOption(times[0], selectedTime == times[0], Modifier.weight(1f)) { selectedTime = times[0] }
                        DateOption(times[1], selectedTime == times[1], Modifier.weight(1f)) { selectedTime = times[1] }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DateOption(times[2], selectedTime == times[2], Modifier.weight(1f)) { selectedTime = times[2] }
                        DateOption(times[3], selectedTime == times[3], Modifier.weight(1f)) { selectedTime = times[3] }
                    }
                }
            }

            // Info Note
            Surface(
                color = Color(0xFFE0F7FA),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFFB2EBF2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Red, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "The property owner will meet you at the location. Please arrive on time.",
                        fontSize = 12.sp,
                        color = Color(0xFF006064)
                    )
                }
            }
        }
    }
}

@Composable
fun DateOption(text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF009688) else Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) Color(0xFFE0F2F1) else Color.White
        )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF009688) else Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun VisitConfirmedScreen(
    visitId: String, // Pass ID to fetch details
    onHomeClick: () -> Unit,
    onMessageOwnerClick: () -> Unit
) {
    val visit = ListingRepository.visits.find { it.id == visitId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon
        Surface(
            shape = CircleShape,
            color = Color(0xFFE0F2F1),
            modifier = Modifier.size(120.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(80.dp),
                    border = BorderStroke(2.dp, Color(0xFF009688))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF009688),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Visit Confirmed!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your property visit has been scheduled successfully",
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Visit Details Card
        Surface(
            color = Color(0xFFF9FAFB),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                VisitDetailRow(Icons.Default.CalendarToday, "Date & Time", "${visit.date} at ${visit.time}")
                VisitDetailRow(Icons.Default.LocationOn, "Location", visit.propertyTitle) // Using Title as Location header for now, or use Address
                VisitDetailRow(Icons.Default.Schedule, "Duration", "30 minutes")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onHomeClick) {
            Text("Back to Home", color = Color(0xFF009688))
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedButton(
            onClick = onMessageOwnerClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFF009688))
        ) {
            Text("Message Owner", color = Color(0xFF009688))
        }
    }
}

@Composable
fun VisitDetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, contentDescription = null, tint = Color(0xFF009688), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
