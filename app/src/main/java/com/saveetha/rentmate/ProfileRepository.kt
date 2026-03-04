package com.saveetha.rentmate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Profile Data Model
data class ProfileData(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val location: String,
    val companyName: String,
    val bio: String,
    val title: String,
    val emailNotifications: Boolean,
    val smsAlerts: Boolean,
    val weeklyReports: Boolean
)

// Singleton Profile Repository
object ProfileRepository {
    private var _profile by mutableStateOf(
        ProfileData(
            fullName = "John Smith",
            email = "john.smith@realestate.com",
            phoneNumber = "(555) 123-4567",
            location = "San Francisco, CA",
            companyName = "Smith Properties LLC",
            bio = "Experienced property manager with 10+ years in residential real estate. Specializing in luxury apartments and urban living spaces in the Bay Area.",
            title = "Property Manager",
            emailNotifications = true,
            smsAlerts = true,
            weeklyReports = false
        )
    )
    
    val profile: ProfileData get() = _profile
    
    fun updateProfile(newProfile: ProfileData) {
        _profile = newProfile
    }
    
    fun updateNotificationSettings(
        emailNotifications: Boolean? = null,
        smsAlerts: Boolean? = null,
        weeklyReports: Boolean? = null
    ) {
        _profile = _profile.copy(
            emailNotifications = emailNotifications ?: _profile.emailNotifications,
            smsAlerts = smsAlerts ?: _profile.smsAlerts,
            weeklyReports = weeklyReports ?: _profile.weeklyReports
        )
    }
}
