package com.saveetha.rentmate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosLocationScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    step: Int = 3,
    existingListing: Listing? = null,
    onDataChange: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> }
) {
    var address by remember { mutableStateOf(existingListing?.address ?: "") }
    var city by remember { mutableStateOf(existingListing?.city ?: "") }
    var state by remember { mutableStateOf(existingListing?.state ?: "") }
    var pincode by remember { mutableStateOf(existingListing?.pincode ?: "") }
    var landmarks by remember { mutableStateOf(existingListing?.landmarks ?: "") }

    // Notify parent of initial data if editing
    LaunchedEffect(Unit) {
        if (existingListing != null) {
            onDataChange(address, city, state, pincode, landmarks)
        }
    }

    // Effect to update data whenever fields change
    LaunchedEffect(address, city, state, pincode, landmarks) {
        onDataChange(address, city, state, pincode, landmarks)
    }

    val tealColor = Color(0xFF009688)
    val progress = if (step == 3) 0.6f else 0.8f
    val progressPercent = if (step == 3) "60%" else "80%"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (step == 3) "Photos & Location" else "Location Details") },
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
                        text = "Step $step of 5",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = progressPercent,
                        fontSize = 14.sp,
                        color = tealColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = tealColor,
                    trackColor = Color(0xFFE0E0E0)
                )
            }

            // Form Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Photo Upload Section (only show on step 3)
                if (step == 3) {
                    Column {
                        Text(
                            text = "Property Photos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Photo Upload Placeholder
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF5F5F5),
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                Color.LightGray.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = tealColor.copy(alpha = 0.1f),
                                    modifier = Modifier.size(56.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Add Photos",
                                            tint = tealColor,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Upload Property Photos",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Tap to select images",
                                    fontSize = 12.sp,
                                    color = Color.LightGray
                                )
                            }
                        }
                        
                        Text(
                            text = "Add at least 3 photos for better visibility",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }

                // Location Section
                Text(
                    text = "Location Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = if (step == 3) 8.dp else 0.dp)
                )

                // Address
                Column {
                    Row {
                        Text(
                            text = "Address",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = " *",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholder = { Text("123 Main St, San Francisco, CA", color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedIndicatorColor = tealColor,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                // City and State
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row {
                            Text(
                                text = "City",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = " *",
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            placeholder = { Text("San Francisco", color = Color.LightGray) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedIndicatorColor = tealColor,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row {
                            Text(
                                text = "State",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = " *",
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = state,
                            onValueChange = { state = it },
                            placeholder = { Text("California", color = Color.LightGray) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedIndicatorColor = tealColor,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                // Pincode
                Column {
                    Row {
                        Text(
                            text = "Pincode",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = " *",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pincode,
                        onValueChange = { pincode = it },
                        placeholder = { Text("94102", color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedIndicatorColor = tealColor,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                // Nearby Landmarks
                Column {
                    Text(
                        text = "Nearby Landmarks",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = landmarks,
                        onValueChange = { landmarks = it },
                        placeholder = { Text("e.g., Near Metro Station, Shopping Mall", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedIndicatorColor = tealColor,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                // Continue Button
                Button(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tealColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Continue", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosLocationScreenPreview() {
    PhotosLocationScreen(
        onBackClick = {},
        onContinueClick = {},
        step = 3
    )
}
