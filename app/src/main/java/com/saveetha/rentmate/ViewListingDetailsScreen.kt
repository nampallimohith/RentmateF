package com.saveetha.rentmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
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
fun ViewListingDetailsScreen(
    listingId: String,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit
) {
    val listing = ListingRepository.getListingById(listingId)
    val tealColor = Color(0xFF009688)

    if (listing == null) {
        // Handle error case
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
                title = { Text("Property Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditClick(listingId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = tealColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
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
                // In a real app, load image from listing.photos
                Icon(
                    Icons.Outlined.Image,
                    contentDescription = "Property Image",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
                
                // Price Tag
                Surface(
                    color = tealColor,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = "₹${NumberFormat.getNumberInstance(Locale.US).format(listing.monthlyRent.toIntOrNull() ?: 0)}/mo",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title and Location
                Column {
                    Text(
                        text = listing.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${listing.city}, ${listing.state}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Divider(color = Color(0xFFEEEEEE))

                // Key Features
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FeatureItem(
                        icon = Icons.Outlined.Home,
                        label = listing.bhkType
                    )
                    FeatureItem(
                        icon = Icons.Outlined.Weekend, // Furnishing
                        label = listing.furnishing
                    )
                    FeatureItem(
                        icon = Icons.Outlined.SquareFoot,
                        label = "${listing.squareFeet} Sq.ft"
                    )
                    FeatureItem(
                        icon = Icons.Outlined.Bathtub,
                        label = "${listing.bathrooms} Baths"
                    )
                }

                Divider(color = Color(0xFFEEEEEE))

                // Description
                Column {
                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = listing.description,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }

                Divider(color = Color(0xFFEEEEEE))

                // Financial Details
                Column {
                    Text(
                        text = "Financial Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FinancialRow("Monthly Rent", "₹${listing.monthlyRent}")
                    FinancialRow("Security Deposit", "₹${listing.securityDeposit}")
                    FinancialRow("Maintenance", "₹${listing.monthlyMaintenance}")
                    FinancialRow("Brokerage", listing.brokerage)
                }

                Divider(color = Color(0xFFEEEEEE))

                // Additional Info
                Column {
                    Text(
                        text = "Additional Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FinancialRow("Property Type", listing.propertyType)
                    FinancialRow("Preferred Tenant", listing.preferredTenant)
                    FinancialRow("Available From", listing.availableFrom)
                    FinancialRow("Notice Period", listing.noticePeriod)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun FeatureItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF5F5F5),
            modifier = Modifier.size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Color(0xFF009688))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun FinancialRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
}
