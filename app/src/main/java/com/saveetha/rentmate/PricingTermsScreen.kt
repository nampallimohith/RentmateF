package com.saveetha.rentmate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingTermsScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    existingListing: Listing? = null,
    onDataChange: (String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, -> }
) {
    var monthlyRent by remember { mutableStateOf(existingListing?.monthlyRent ?: "") }
    var securityDeposit by remember { mutableStateOf(existingListing?.securityDeposit ?: "") }
    var monthlyMaintenance by remember { mutableStateOf(existingListing?.monthlyMaintenance ?: "") }
    var selectedBrokerage by remember { mutableStateOf(existingListing?.brokerage ?: "") }
    var availableFrom by remember { mutableStateOf(existingListing?.availableFrom ?: "") }
    var selectedNoticePeriod by remember { mutableStateOf(existingListing?.noticePeriod ?: "") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Notify parent of initial data if editing
    LaunchedEffect(Unit) {
        if (existingListing != null) {
            onDataChange(
                monthlyRent, securityDeposit, monthlyMaintenance,
                selectedBrokerage, availableFrom, selectedNoticePeriod
            )
        }
    }

    // Effect to update data whenever fields change
    LaunchedEffect(
        monthlyRent, securityDeposit, monthlyMaintenance,
        selectedBrokerage, availableFrom, selectedNoticePeriod
    ) {
        onDataChange(
            monthlyRent, securityDeposit, monthlyMaintenance,
            selectedBrokerage, availableFrom, selectedNoticePeriod
        )
    }

    val tealColor = Color(0xFF009688)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pricing & Terms") },
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
                        text = "Step 2 of 5",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "40%",
                        fontSize = 14.sp,
                        color = tealColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.4f,
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
                // Monthly Rent
                Column {
                    Row {
                        Text(
                            text = "Monthly Rent",
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
                        value = monthlyRent,
                        onValueChange = { monthlyRent = it },
                        placeholder = { Text("₹8000", color = Color.LightGray) },
                        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
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

                // Security Deposit
                Column {
                    Row {
                        Text(
                            text = "Security Deposit",
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
                        value = securityDeposit,
                        onValueChange = { securityDeposit = it },
                        placeholder = { Text("₹6000", color = Color.LightGray) },
                        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA),
                            focusedIndicatorColor = tealColor,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Text(
                        text = "Typically 2-3 months rent",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }

                // Monthly Maintenance
                Column {
                    Text(
                        text = "Monthly Maintenance",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = monthlyMaintenance,
                        onValueChange = { monthlyMaintenance = it },
                        placeholder = { Text("₹000", color = Color.LightGray) },
                        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
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

                // Brokerage
                Column {
                    Text(
                        text = "Brokerage",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedBrokerage == "Zero Brokerage",
                            onClick = { selectedBrokerage = "Zero Brokerage" },
                            label = { Text("Zero Brokerage", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                        FilterChip(
                            selected = selectedBrokerage == "Half Month",
                            onClick = { selectedBrokerage = "Half Month" },
                            label = { Text("Half Month", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedBrokerage == "One Month",
                            onClick = { selectedBrokerage = "One Month" },
                            label = { Text("One Month", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                        FilterChip(
                            selected = selectedBrokerage == "Negotiable",
                            onClick = { selectedBrokerage = "Negotiable" },
                            label = { Text("Negotiable", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                    }
                }

                // Available From
                Column {
                    Row {
                        Text(
                            text = "Available From",
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
                        value = availableFrom,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("mm/dd/yyyy", color = Color.LightGray) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Calendar",
                                    tint = Color.Gray
                                )
                            }
                        },
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

                // Notice Period
                Column {
                    Text(
                        text = "Notice Period",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedNoticePeriod == "15 Days",
                            onClick = { selectedNoticePeriod = "15 Days" },
                            label = { Text("15 Days", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                        FilterChip(
                            selected = selectedNoticePeriod == "1 Month",
                            onClick = { selectedNoticePeriod = "1 Month" },
                            label = { Text("1 Month", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedNoticePeriod == "2 Months",
                            onClick = { selectedNoticePeriod = "2 Months" },
                            label = { Text("2 Months", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                        FilterChip(
                            selected = selectedNoticePeriod == "Negotiable",
                            onClick = { selectedNoticePeriod = "Negotiable" },
                            label = { Text("Negotiable", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = tealColor.copy(alpha = 0.2f),
                                selectedLabelColor = tealColor,
                                containerColor = Color.White,
                                labelColor = Color.Gray
                            )
                        )
                    }
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
        
        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val calendar = java.util.Calendar.getInstance()
                                calendar.timeInMillis = millis
                                val month = calendar.get(java.util.Calendar.MONTH) + 1
                                val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                                val year = calendar.get(java.util.Calendar.YEAR)
                                availableFrom = String.format("%02d/%02d/%04d", month, day, year)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK", color = tealColor)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = tealColor,
                        todayContentColor = tealColor,
                        todayDateBorderColor = tealColor
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PricingTermsScreenPreview() {
    PricingTermsScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}
