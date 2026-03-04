package com.saveetha.rentmate.network

import com.google.gson.annotations.SerializedName

// Generic API response wrapper
data class ApiResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
)

// Auth Data Models
data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class VerifyOtpResponse(
    @SerializedName("token") val token: String,
    @SerializedName("role") val role: String,
    @SerializedName("phone") val phone: String
)

data class RegisterResponse(
    @SerializedName("otp_sent") val otpSent: Boolean
)

// Entity Models
data class Listing(
    @SerializedName("id") val id: Int,
    @SerializedName("lister_id") val listerId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("location") val location: String,
    @SerializedName("bhk_type") val bhkType: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("lister_name") val listerName: String? = null
)

data class Booking(
    @SerializedName("booking_id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("name") val userName: String? = null,
    @SerializedName("phone") val userPhone: String? = null,
    @SerializedName("email") val userEmail: String? = null
)

// Request Models
data class LoginRequest(
    val email: String,
    val password: String,
    val role: String
)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val password: String,
    val phone: String,
    val role: String
)

data class VerifyOtpRequest(
    val phone: String? = null,
    val token: String? = null,
    val otp: String
)

data class CreateListingRequest(
    val title: String,
    val price: Double,
    val location: String,
    val bhk_type: String
)
