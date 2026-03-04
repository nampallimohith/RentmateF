package com.saveetha.rentmate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saveetha.rentmate.network.ApiResponse
import com.saveetha.rentmate.network.LoginRequest
import com.saveetha.rentmate.network.LoginResponse
import com.saveetha.rentmate.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)
    var userToken by mutableStateOf<String?>(null)
    var userName by mutableStateOf<String?>(null)
    var userEmail by mutableStateOf<String?>(null)
    var userRole by mutableStateOf<String?>(null)

    fun loginUser(email: String, password: String, role: String) {
        isLoading = true
        errorMessage = null
        loginSuccess = false  // Reset on new login attempt
        
        val request = LoginRequest(email, password, role)
        
        RetrofitClient.instance.login(request).enqueue(object : Callback<ApiResponse<LoginResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<LoginResponse>>,
                response: Response<ApiResponse<LoginResponse>>
            ) {
                isLoading = false
                if (response.isSuccessful && response.body()?.status == "success") {
                    val data = response.body()?.data
                    userToken = data?.token
                    userName = data?.name
                    userEmail = data?.email
                    userRole = data?.role
                    loginSuccess = true
                } else {
                    val errorMsg = try {
                        val errorStr = response.errorBody()?.string()
                        if (errorStr != null) {
                            val jsonObject = JSONObject(errorStr)
                            jsonObject.optString("message", "Login failed. Please check your credentials.")
                        } else {
                            "Login failed. Please check your credentials."
                        }
                    } catch (e: Exception) {
                        "Login failed. Please check your credentials."
                    }
                    errorMessage = errorMsg
                }
            }

            override fun onFailure(call: Call<ApiResponse<LoginResponse>>, t: Throwable) {
                isLoading = false
                errorMessage = "Connection error: ${t.message}"
            }
        })
    }

    fun resetState() {
        isLoading = false
        errorMessage = null
        loginSuccess = false
        userToken = null
        userName = null
        userEmail = null
        userRole = null
    }
}
