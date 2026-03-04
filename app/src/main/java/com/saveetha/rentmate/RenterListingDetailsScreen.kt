package com.saveetha.rentmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenterListingDetailsScreen(
    listingId: String,
    onBackClick: () -> Unit,
    onScheduleVisitClick: (String) -> Unit,
    onMessageOwnerClick: (String, String) -> Unit // listingId, ownerName
) {
    val listing = ListingRepository.getListingById(listingId)
    val tealColor = Color(0xFF009688)

    if (listing == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Listing not found")
            Button(onClick = onBackClick) {
                Text("Go Back")
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listing.title, fontSize = 18.sp) }, // Shorten if needed
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { onMessageOwnerClick(listingId, "Rajesh Kumar") }, // Hardcoded owner for now
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, tealColor)
                    ) {
                        Text("Message", color = tealColor)
                    }
                    Button(
                        onClick = { onScheduleVisitClick(listingId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = tealColor)
                    ) {
                        Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Schedule Visit")
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
        ) {
            // Property Image (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Image,
                    contentDescription = "Property Image",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
                
                // Price Label
                Surface(
                    color = tealColor,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = "₹${NumberFormat.getNumberInstance(Locale.US).format(listing.monthlyRent.toIntOrNull() ?: 0)}/month",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header Info
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = Color(0xFFE0F2F1),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = listing.bhkType,
                                fontSize = 12.sp,
                                color = tealColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Surface(
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = listing.furnishing,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = listing.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${listing.address}, ${listing.city}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
                
                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Rent Details Table
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Rent Details", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Monthly Rent", color = Color.Gray, fontSize = 14.sp)
                        Text("₹${listing.monthlyRent}", fontWeight = FontWeight.Medium)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Security Deposit", color = Color.Gray, fontSize = 14.sp)
                        Text("₹${listing.securityDeposit}", fontWeight = FontWeight.Medium)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Maintenance", color = Color.Gray, fontSize = 14.sp)
                        Text("₹${listing.monthlyMaintenance}", fontWeight = FontWeight.Medium)
                    }
                }

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Description
                Column {
                    Text("Description", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = listing.description,
                        color = Color.Gray,
                        lineHeight = 20.sp,
                        fontSize = 14.sp
                    )
                }

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Amenities (Simplified)
                Column {
                    Text("Amenities", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RenterAmenityChip("Wifi")
                        RenterAmenityChip("Power Backup")
                        RenterAmenityChip("Water Supply")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        RenterAmenityChip("CCTV")
                        RenterAmenityChip("Lift")
                        RenterAmenityChip("Parking")
                    }
                }

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Available Vacancies
                Column {
                    Text("Available Vacancies", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    VacancySlot(number = 1)
                    Spacer(modifier = Modifier.height(12.dp))
                    VacancySlot(number = 2)
                }

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Property Owner
                Column {
                    Text("Property Owner", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.LightGray,
                            modifier = Modifier.size(48.dp)
                        ) {
                            // Placeholder for owner image
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Rajesh Kumar", fontWeight = FontWeight.Bold)
                            Text("Property Owner", color = Color.Gray, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onMessageOwnerClick(listingId, "Rajesh Kumar") },
                            modifier = Modifier.background(Color(0xFFE0F2F1), CircleShape)
                        ) {
                            Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Message", tint = tealColor)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun RenterAmenityChip(text: String) {
    Surface(
        color = Color(0xFFE0F7FA),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF006064),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun VacancySlot(number: Int) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0F7FA)), // Light Cyan Border
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE0F7FA),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF009688))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Vacancy Slot # $number",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "Available",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Apply for this spot",
                fontSize = 12.sp,
                color = Color(0xFF009688),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
