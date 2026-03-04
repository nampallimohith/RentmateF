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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListerBoardScreen(
    onPostNewListingClick: () -> Unit,
    onManageListingsClick: () -> Unit,
    onReviewApplicantsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onVisitsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onProfileClick: () -> Unit = {}
) {
    // Get statistics from repository
    val activeListings = ListingRepository.getActiveListingsCount()
    val newApplicants = ListingRepository.getNewApplicantsCount()
    val totalViews = ListingRepository.getTotalViews()
    val tealColor = Color(0xFF009688)
    val lightTeal = Color(0xFFE0F2F1)

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 2.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = tealColor,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "Lister Board",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onNotificationsClick) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.Gray
                            )
                        }
                        IconButton(onClick = onProfileClick) {
                            Surface(
                                shape = CircleShape,
                                color = tealColor,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = "Profile",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Statistics Cards
            StatsCard(
                icon = Icons.Outlined.Home,
                label = "Active Listings",
                value = activeListings.toString(),
                subtitle = "Properties",
                iconColor = tealColor
            )

            StatsCard(
                icon = Icons.Outlined.Person,
                label = "New Applicants",
                value = newApplicants.toString(),
                subtitle = "Candidates",
                iconColor = Color(0xFF2196F3)
            )

            StatsCard(
                icon = Icons.Outlined.TrendingUp,
                label = "Total Views",
                value = totalViews.toString(),
                subtitle = "Engagement",
                iconColor = Color(0xFF9C27B0)
            )

            // Quick Actions
            Text(
                text = "Quick Actions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Review Applicants
                QuickActionCard(
                    icon = Icons.Outlined.People,
                    label = "Review\nApplicants",
                    onClick = onReviewApplicantsClick,
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color.White
                )

                // Post New Listing (Primary)
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = tealColor,
                    shadowElevation = 2.dp,
                    onClick = onPostNewListingClick
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Post New\nListing",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Outlined.Home,
                    label = "Manage\nListings ($activeListings)",
                    onClick = onManageListingsClick,
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color.White
                )

                QuickActionCard(
                    icon = Icons.Default.Event,
                    label = "Scheduled\nVisits",
                    onClick = onVisitsClick,
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color.White
                )
            }

            // Recent Activity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Activity",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { }) {
                    Text("View All", color = tealColor)
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = tealColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            ActivityItem(
                icon = Icons.Outlined.Description,
                iconColor = Color(0xFF2196F3),
                title = "New Application",
                subtitle = "John applied for 456 Oak Ave on March 5t",
                iconBackground = Color(0xFFE3F2FD)
            )

            ActivityItem(
                icon = Icons.Outlined.Home,
                iconColor = Color(0xFFFF9800),
                title = "Listing Edited",
                subtitle = "Updated price for 456 Oak Ave",
                iconBackground = Color(0xFFFFF3E0)
            )

            ActivityItem(
                icon = Icons.Outlined.Message,
                iconColor = tealColor,
                title = "New Message",
                subtitle = "Sarah sent a question about parking",
                iconBackground = lightTeal
            )

            ActivityItem(
                icon = Icons.Outlined.TrendingUp,
                iconColor = Color(0xFF9C27B0),
                title = "View Spike",
                subtitle = "789 Pine Rd: views up 50% today",
                iconBackground = Color(0xFFF3E5F5)
            )

            // Logout Button
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Icon(
                    Icons.Outlined.Logout,
                    contentDescription = "Logout",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out")
            }
        }
    }
}

@Composable
fun StatsCard(
    icon: ImageVector,
    label: String,
    value: String,
    subtitle: String,
    iconColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF009688),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun ActivityItem(
    icon: ImageVector,
    iconColor: Color,
    iconBackground: Color,
    title: String,
    subtitle: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = iconBackground,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListerBoardScreenPreview() {
    ListerBoardScreen(
        onPostNewListingClick = {},
        onManageListingsClick = {},
        onReviewApplicantsClick = {},
        onNotificationsClick = {},
        onVisitsClick = {},
        onLogoutClick = {}
    )
}
