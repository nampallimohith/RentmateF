package com.saveetha.rentmate

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable

fun ListerHomeScreen(
    onLogoutClick: () -> Unit,
    onPostNewListingClick: () -> Unit,
    onManageListingsClick: () -> Unit,
    onReviewApplicantsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onVisitsClick: () -> Unit,
    onProfileClick: () -> Unit = {}
) {
    // Directly show the Lister Board (Dashboard)
    ListerBoardScreen(
        onPostNewListingClick = onPostNewListingClick,
        onManageListingsClick = onManageListingsClick,
        onReviewApplicantsClick = onReviewApplicantsClick,
        onNotificationsClick = onNotificationsClick,
        onVisitsClick = onVisitsClick,
        onLogoutClick = onLogoutClick,
        onProfileClick = onProfileClick
    )
}

@Preview(showBackground = true)
@Composable
fun ListerHomeScreenPreview() {
    ListerHomeScreen(
        onLogoutClick = {},
        onPostNewListingClick = {},
        onManageListingsClick = {},
        onReviewApplicantsClick = {},
        onNotificationsClick = {},
        onVisitsClick = {}
    )
}
