package com.saveetha.rentmate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

fun PropertyDetailsScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    existingListing: Listing? = null,
    onDataChange: (String, String, String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, _, _, -> }
) {
    var propertyTitle by remember { mutableStateOf(existingListing?.title ?: "") }
    var selectedBHK by remember { mutableStateOf(existingListing?.bhkType ?: "") }
    var selectedPropertyType by remember { mutableStateOf(existingListing?.propertyType ?: "") }
    var selectedTenant by remember { mutableStateOf(existingListing?.preferredTenant ?: "") }
    var selectedFurnishing by remember { mutableStateOf(existingListing?.furnishing ?: "") }
    var bathrooms by remember { mutableStateOf(existingListing?.bathrooms?.toString() ?: "") }
    var squareFeet by remember { mutableStateOf(existingListing?.squareFeet?.toString() ?: "") }
    var description by remember { mutableStateOf(existingListing?.description ?: "") }

    // Notify parent of initial data if editing
    LaunchedEffect(Unit) {
        if (existingListing != null) {
            onDataChange(
                propertyTitle, selectedBHK, selectedPropertyType, selectedTenant,
                selectedFurnishing, bathrooms, squareFeet, description
            )
        }
    }

    // Effect to update data whenever fields change
    LaunchedEffect(
        propertyTitle, selectedBHK, selectedPropertyType, selectedTenant,
        selectedFurnishing, bathrooms, squareFeet, description
    ) {
        onDataChange(
            propertyTitle, selectedBHK, selectedPropertyType, selectedTenant,
            selectedFurnishing, bathrooms, squareFeet, description
        )
    }

    val tealColor = Color(0xFF009688)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Property Details") },
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
                        text = "Step 1 of 5",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "20%",
                        fontSize = 14.sp,
                        color = tealColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.2f,
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
                // Property Title
                Column {
                    Row {
                        Text(
                            text = "Property Title",
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
                        value = propertyTitle,
                        onValueChange = { propertyTitle = it },
                        placeholder = { Text("e.g., Spacious 2BHK in Koramangala", color = Color.LightGray) },
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

                // BHK Type
                Column {
                    Text(
                        text = "BHK Type",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ChipGroup(
                        items = listOf("1BHK", "1.5BHK", "2BHK", "3BHK", "4BHK", "4BHK+"),
                        selectedItem = selectedBHK,
                        onItemSelected = { selectedBHK = it }
                    )
                }

                // Property Type
                Column {
                    Text(
                        text = "Property Type",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ChipGroup(
                        items = listOf("Flat", "PG", "Independent House", "Builder Floor"),
                        selectedItem = selectedPropertyType,
                        onItemSelected = { selectedPropertyType = it }
                    )
                }

                // Preferred Tenant
                Column {
                    Text(
                        text = "Preferred Tenant",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TenantChip(
                            label = "Boys Only",
                            subtitle = "Male tenants only",
                            icon = "♂",
                            selected = selectedTenant == "Boys Only",
                            onClick = { selectedTenant = "Boys Only" },
                            modifier = Modifier.weight(1f)
                        )
                        TenantChip(
                            label = "Girls Only",
                            subtitle = "Female tenants only",
                            icon = "♀",
                            selected = selectedTenant == "Girls Only",
                            onClick = { selectedTenant = "Girls Only" },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TenantChip(
                            label = "Family/Co-ed",
                            subtitle = "Families or mixed",
                            icon = "👥",
                            selected = selectedTenant == "Family/Co-ed",
                            onClick = { selectedTenant = "Family/Co-ed" },
                            modifier = Modifier.weight(1f)
                        )
                        TenantChip(
                            label = "Any",
                            subtitle = "No preference",
                            icon = "✓",
                            selected = selectedTenant == "Any",
                            onClick = { selectedTenant = "Any" },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Furnishing Status
                Column {
                    Text(
                        text = "Furnishing Status",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ChipGroup(
                        items = listOf("Fully Furnished", "Semi-Furnished", "Unfurnished"),
                        selectedItem = selectedFurnishing,
                        onItemSelected = { selectedFurnishing = it }
                    )
                }

                // Bathrooms and Square Feet
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bathrooms",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = bathrooms,
                            onValueChange = { bathrooms = it },
                            placeholder = { Text("2", color = Color.LightGray) },
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
                        Text(
                            text = "Square Feet",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = squareFeet,
                            onValueChange = { squareFeet = it },
                            placeholder = { Text("1100", color = Color.LightGray) },
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

                // Description
                Column {
                    Text(
                        text = "Description",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Describe your property...", color = Color.LightGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
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

@Composable
fun ChipGroup(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val tealColor = Color(0xFF009688)
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    FilterChip(
                        selected = selectedItem == item,
                        onClick = { onItemSelected(item) },
                        label = { Text(item, fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = tealColor.copy(alpha = 0.2f),
                            selectedLabelColor = tealColor,
                            containerColor = Color.White,
                            labelColor = Color.Gray
                        )
                    )
                }
                // Fill remaining space if row is not complete
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun TenantChip(
    label: String,
    subtitle: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tealColor = Color(0xFF009688)
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = if (selected) tealColor.copy(alpha = 0.1f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) tealColor else Color.LightGray
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selected) tealColor else Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyDetailsScreenPreview() {
    PropertyDetailsScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}
