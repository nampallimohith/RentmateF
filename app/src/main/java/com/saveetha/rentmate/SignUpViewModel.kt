package com.saveetha.rentmate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saveetha.rentmate.network.ApiResponse
import com.saveetha.rentmate.network.RegisterRequest
import com.saveetha.rentmate.network.RegisterResponse
import com.saveetha.rentmate.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var registrationSuccess by mutableStateOf(false)
    var userToken by mutableStateOf<String?>(null)
    var userName by mutableStateOf<String?>(null)
    var userEmail by mutableStateOf<String?>(null)
    var userRole by mutableStateOf<String?>(null)

    fun registerUser(fullName: String, email: String, password: String, phone: String, role: String) {
        isLoading = true
        errorMessage = null
        registrationSuccess = false
        
        val request = RegisterRequest(fullName, email, password, phone, role)
        
        RetrofitClient.instance.register(request).enqueue(object : Callback<ApiResponse<RegisterResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<RegisterResponse>>,
                response: Response<ApiResponse<RegisterResponse>>
            ) {
                isLoading = false
                if (response.isSuccessful && response.body()?.status == "success") {
                    // Registration successful
                    registrationSuccess = true
                    userName = fullName
                    userEmail = email
                    userRole = role
                    // Note: Backend may or may not return token immediately
                    // If it does, we can store it here
                } else {
                    val errorMsg = try {
                        val errorStr = response.errorBody()?.string()
                        if (errorStr != null) {
                            val jsonObject = JSONObject(errorStr)
                            jsonObject.optString("message", "Registration failed. Please try again.")
                        } else {
                            "Registration failed. Please try again."
                        }
                    } catch (e: Exception) {
                        "Registration failed. Please try again."
                    }
                    errorMessage = errorMsg
                }
            }

            override fun onFailure(call: Call<ApiResponse<RegisterResponse>>, t: Throwable) {
                isLoading = false
                errorMessage = "Connection error: ${t.message}"
            }
        })
    }

    fun resetState() {
        isLoading = false
        errorMessage = null
        registrationSuccess = false
    }
}
