package com.saveetha.rentmate

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TealColor = Color(0xFF009688)
private val LightTeal = Color(0xFFE0F2F1)

@Composable
fun RenterProfileSetupIntroScreen(onContinueClick: () -> Unit) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = LightTeal,
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = TealColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Let's set up your profile",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Help us find you the perfect match by answering a few questions about yourself and what you're looking for.",
                    color = Color.Gray,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // Powered by SIMATS
            Text(
                text = "Powered by SIMATS",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenterPersonalInfoScreen(
    onBackClick: () -> Unit,
    onContinueClick: (String, String, String, String, String, String) -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Select") }
    var occupation by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var genderExpanded by remember { mutableStateOf(false) }
    var occupationExpanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Men", "Women")
    val occupationOptions = listOf("Student", "Employee", "Self-employed", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Info", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Step 1 of 3", color = Color.Gray, fontSize = 12.sp)
                Text("33%", color = TealColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 0.33f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = TealColor,
                trackColor = LightTeal
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Tell us about yourself",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "This helps us find the best matches for you",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            ProfileTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it }, placeholder = "Enter your name")
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    ProfileTextField(label = "Age", value = age, onValueChange = { age = it }, placeholder = "25")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Gender *", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { genderExpanded = true },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(gender, color = if (gender == "Select") Color.Gray else Color.Black)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
                        }
                    }
                    DropdownMenu(expanded = genderExpanded, onDismissRequest = { genderExpanded = false }) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    gender = option
                                    genderExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text("Occupation *", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { occupationExpanded = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(if (occupation.isEmpty()) "Select Occupation" else occupation, color = if (occupation.isEmpty()) Color.Gray else Color.Black)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
                    }
                }
                DropdownMenu(expanded = occupationExpanded, onDismissRequest = { occupationExpanded = false }) {
                    occupationOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                occupation = option
                                occupationExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField(label = "Phone Number *", value = phone, onValueChange = { phone = it }, placeholder = "+1 (555) 123-4567")
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTextField(label = "Current Location *", value = location, onValueChange = { location = it }, placeholder = "e.g., Bangalore")

            Spacer(modifier = Modifier.height(24.dp))
            Surface(
                color = Color(0xFFF1F8E9),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF43A047), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Privacy: Your information is kept private and only shared with verified matches",
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32),
                        lineHeight = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { onContinueClick(fullName, age, gender, occupation, location, phone) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Column {
        Text(text = "$label *", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TealColor,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}

@Composable
fun RenterProfileDashboardScreen(
    name: String,
    email: String,
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") }, selected = false, onClick = onBackClick)
                NavigationBarItem(icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Group, null) }, label = { Text("Roommates") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Chat, null) }, label = { Text("Chats") }, selected = false, onClick = {})
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") }, selected = true, onClick = {})
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(TealColor)
                    .padding(24.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = if (name.isNotEmpty()) name.take(2).uppercase() else "JD",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(text = email, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    }
                }
                Text(
                    text = "Profile",
                    modifier = Modifier.align(Alignment.TopStart),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                DashboardItem("Edit Profile", "Update your personal information", Icons.Default.Edit, onEditProfileClick)
                DashboardItem("Account Settings", "", Icons.Default.Settings, {})
                DashboardItem("Subscription & Billing", "", Icons.Default.CreditCard, {})
                DashboardItem("Help & Support", "", Icons.Default.Help, {})
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Log Out", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardItem(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LightTeal.copy(alpha = 0.5f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = TealColor, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                if (subtitle.isNotEmpty()) {
                    Text(text = subtitle, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenterEditProfileScreen(
    name: String,
    email: String,
    phone: String,
    occupation: String,
    bio: String,
    onBackClick: () -> Unit,
    onSaveClick: (String, String, String, String, String) -> Unit
) {
    var editName by remember { mutableStateOf(name) }
    var editEmail by remember { mutableStateOf(email) }
    var editPhone by remember { mutableStateOf(phone) }
    var editOccupation by remember { mutableStateOf(occupation) }
    var editBio by remember { mutableStateOf(bio) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = TealColor,
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (name.isNotEmpty()) name.take(2).uppercase() else "JD",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            TextButton(onClick = {}) {
                Text("Change Photo", color = TealColor, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            EditProfileTextField(label = "Full Name", value = editName, onValueChange = { editName = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileTextField(label = "Email", value = editEmail, onValueChange = { editEmail = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileTextField(label = "Phone", value = editPhone, onValueChange = { editPhone = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileTextField(label = "Occupation", value = editOccupation, onValueChange = { editOccupation = it })
            Spacer(modifier = Modifier.height(16.dp))
            
            Column {
                Text("Bio", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = editBio,
                    onValueChange = { editBio = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TealColor)
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { onSaveClick(editName, editEmail, editPhone, editOccupation, editBio) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealColor.copy(alpha = 0.1f), contentColor = TealColor),
                elevation = null,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Changes", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun EditProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TealColor)
        )
    }
}
