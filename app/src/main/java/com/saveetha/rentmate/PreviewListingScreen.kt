package com.saveetha.rentmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewListingScreen(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onPublishClick: () -> Unit,
    propertyTitle: String = "Modern Downtown Apartment",
    address: String = "123 Main St",
    city: String = "San Francisco",
    state: String = "CA",
    monthlyRent: String = "1,200",
    bhkType: String = "2BHK",
    bathrooms: String = "1",
    squareFeet: String = "850",
    listingId: String? = null,
    isEditing: Boolean = false
) {
    val tealColor = Color(0xFF009688)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preview Listing") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
        ) {
            // Progress Indicator
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Step 5 of 5",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "100%",
                        fontSize = 14.sp,
                        color = tealColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 1.0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = tealColor,
                    trackColor = Color(0xFFE0E0E0)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Review Your Listing Header
                Text(
                    text = "Review Your Listing",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Property Photo Placeholder
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE8F5F3)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Property photos would appear here",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Property Title
                Text(
                    text = propertyTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Address
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "$address, $city, $state",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                // Price
                Text(
                    text = "₹$monthlyRent/mo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = tealColor
                )

                // Property Stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PropertyStat(
                        icon = Icons.Default.Bed,
                        value = bhkType.replace("BHK", ""),
                        label = "Beds"
                    )
                    
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(50.dp),
                        color = Color.LightGray
                    )
                    
                    PropertyStat(
                        icon = Icons.Default.Bathtub,
                        value = bathrooms,
                        label = "Baths"
                    )
                    
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(50.dp),
                        color = Color.LightGray
                    )
                    
                    PropertyStat(
                        icon = Icons.Default.SquareFoot,
                        value = squareFeet,
                        label = "Sq Ft"
                    )
                }

                // Amenities
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Amenities",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AmenityChip("WiFi")
                        AmenityChip("Parking")
                        AmenityChip("Laundry")
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AmenityChip("Pet Friendly")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Edit Button
                    OutlinedButton(
                        onClick = onEditClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = tealColor
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, tealColor)
                    ) {
                        Text("Edit", fontSize = 16.sp)
                    }

                    // Publish Button
                    Button(
                        onClick = onPublishClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = tealColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (isEditing) "Update Listing" else "Publish Listing", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyStat(
    icon: ImageVector,
    value: String,
    label: String
) {
    val tealColor = Color(0xFF009688)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = tealColor,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun AmenityChip(text: String) {
    val tealColor = Color(0xFF009688)
    
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = tealColor.copy(alpha = 0.1f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = tealColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListingScreenPreview() {
    PreviewListingScreen(
        onBackClick = {},
        onEditClick = {},
        onPublishClick = {}
    )
}
