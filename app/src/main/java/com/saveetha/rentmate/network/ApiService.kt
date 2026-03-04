package com.saveetha.rentmate.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // Auth Endpoints
    @POST("api/auth/register.php")
    fun register(@Body request: RegisterRequest): Call<ApiResponse<RegisterResponse>>

    @POST("api/auth/login.php")
    fun login(@Body request: LoginRequest): Call<ApiResponse<LoginResponse>>

    @POST("api/auth/verify_otp.php")
    fun verifyOtp(@Body request: VerifyOtpRequest): Call<ApiResponse<VerifyOtpResponse>>

    // Lister Endpoints
    @POST("api/lister/create_listing.php")
    fun createListing(
        @Header("Authorization") token: String,
        @Body request: CreateListingRequest
    ): Call<ApiResponse<Map<String, Int>>>

    @GET("api/lister/get_my_listings.php")
    fun getMyListings(@Header("Authorization") token: String): Call<ApiResponse<List<Listing>>>

    @POST("api/lister/view_applicants.php")
    fun viewApplicants(
        @Header("Authorization") token: String,
        @Body request: Map<String, Int>
    ): Call<ApiResponse<List<Booking>>>

    // Renter Endpoints
    @GET("api/renter/get_all_listings.php")
    fun getAllListings(@Header("Authorization") token: String): Call<ApiResponse<List<Listing>>>

    @POST("api/renter/book_room.php")
    fun bookRoom(
        @Header("Authorization") token: String,
        @Body request: Map<String, Int>
    ): Call<ApiResponse<Map<String, Int>>>

    // Profile Endpoints
    @POST("api/profile/update.php")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): Call<ApiResponse<Unit>>
}
