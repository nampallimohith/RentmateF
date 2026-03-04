package com.saveetha.rentmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListerProfileScreen(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val tealColor = Color(0xFF009688)
    val profile = ProfileRepository.profile
    var emailNotifications by remember { mutableStateOf(profile.emailNotifications) }
    var smsAlerts by remember { mutableStateOf(profile.smsAlerts) }
    var weeklyReports by remember { mutableStateOf(profile.weeklyReports) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = tealColor
                        )
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
            // Profile Header with Teal Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(tealColor)
            ) {
                // Profile Picture
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 40.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(100.dp),
                        shadowElevation = 4.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.LightGray,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(50.dp),
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                    // Online Indicator
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (-8).dp, y = (-8).dp),
                        border = androidx.compose.foundation.BorderStroke(3.dp, Color.White)
                    ) {}
                }
            }
            
            Spacer(modifier = Modifier.height(50.dp))
            
            // Profile Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = profile.fullName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = profile.title,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = profile.bio,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Contact Information
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContactInfoRow(
                        icon = Icons.Default.Email,
                        text = profile.email,
                        tealColor = tealColor
                    )
                    ContactInfoRow(
                        icon = Icons.Default.Phone,
                        text = profile.phoneNumber,
                        tealColor = tealColor
                    )
                    ContactInfoRow(
                        icon = Icons.Default.Business,
                        text = profile.companyName,
                        tealColor = tealColor
                    )
                    ContactInfoRow(
                        icon = Icons.Default.LocationOn,
                        text = profile.location,
                        tealColor = tealColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Statistics
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    value = "${ListingRepository.getActiveListingsCount()}",
                    label = "Listings",
                    modifier = Modifier.weight(1f),
                    tealColor = tealColor
                )
                StatCard(
                    value = "${ListingRepository.getNewApplicantsCount()}",
                    label = "Applicants",
                    modifier = Modifier.weight(1f),
                    tealColor = tealColor
                )
                StatCard(
                    value = "95%",
                    label = "Response\nRate",
                    modifier = Modifier.weight(1f),
                    tealColor = tealColor
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Notification Settings
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Notification Settings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    NotificationToggle(
                        title = "Email Notifications",
                        subtitle = "Receive updates via email",
                        checked = emailNotifications,
                        onCheckedChange = {
                            emailNotifications = it
                            ProfileRepository.updateNotificationSettings(emailNotifications = it)
                        },
                        tealColor = tealColor
                    )
                    
                    NotificationToggle(
                        title = "SMS Alerts",
                        subtitle = "Get instant alerts on your phone",
                        checked = smsAlerts,
                        onCheckedChange = {
                            smsAlerts = it
                            ProfileRepository.updateNotificationSettings(smsAlerts = it)
                        },
                        tealColor = tealColor
                    )
                    
                    NotificationToggle(
                        title = "Weekly Reports",
                        subtitle = "Summary of your listing performance",
                        checked = weeklyReports,
                        onCheckedChange = {
                            weeklyReports = it
                            ProfileRepository.updateNotificationSettings(weeklyReports = it)
                        },
                        tealColor = tealColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Log Out Button
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE53935)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE53935))
            ) {
                Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ContactInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    tealColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = tealColor.copy(alpha = 0.1f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = tealColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    tealColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = tealColor
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun NotificationToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    tealColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = tealColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListerProfileScreenPreview() {
    ListerProfileScreen(
        onBackClick = {},
        onEditClick = {},
        onLogoutClick = {}
    )
}
