package com.saveetha.rentmate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListingPublishedScreen(
    onViewListingClick: () -> Unit,
    onBackToHomeClick: () -> Unit
) {
    val tealColor = Color(0xFF009688)
    val lightTeal = Color(0xFFE0F2F1)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Success Icon
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = lightTeal
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Success",
                        tint = tealColor,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Listing Published!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = "Your property is now live and visible to\nthousands of potential tenants",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // What's Next Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF5F5F5)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "What's Next?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Step 1
                    NextStepItem(
                        number = "1",
                        text = "Respond to inquiries from interested\nrenters",
                        tealColor = tealColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Step 2
                    NextStepItem(
                        number = "2",
                        text = "Schedule property viewings",
                        tealColor = tealColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Step 3
                    NextStepItem(
                        number = "3",
                        text = "Find your perfect tenant!",
                        tealColor = tealColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // View My Listing Button
            Button(
                onClick = onViewListingClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tealColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "View",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("View My Listing", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Back to Home Button
            OutlinedButton(
                onClick = onBackToHomeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = tealColor
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, tealColor)
            ) {
                Text("Back to Home", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun NextStepItem(
    number: String,
    text: String,
    tealColor: Color
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Number Badge
        Surface(
            modifier = Modifier.size(28.dp),
            shape = CircleShape,
            color = tealColor.copy(alpha = 0.2f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = tealColor
                )
            }
        }

        // Text
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListingPublishedScreenPreview() {
    ListingPublishedScreen(
        onViewListingClick = {},
        onBackToHomeClick = {}
    )
}
