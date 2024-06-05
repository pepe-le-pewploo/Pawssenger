package com.example.pawssenger.retrofit.callFunctions

import android.util.Log
import com.example.pawssenger.data.otpRequest.ApiResponse
import com.example.pawssenger.data.otpRequest.RequestParameters
import com.example.pawssenger.retrofit.MyApiService
import com.example.pawssenger.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val requestParameters = RequestParameters(
    appId = "APP_118867",
    password = "a46785b83fc98b81ae80778189c0687a",
    mobile = ""
)
fun submit() {
    val destinationService = ServiceBuilder.buildService(MyApiService::class.java)
    val requestCall = destinationService.requestOtp(requestParameters)

    requestCall.enqueue(object : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    verifyParameters.referenceNo = apiResponse.referenceNo
                }
                Log.d("MyActivity", "OTP sent successfully: $apiResponse")
            } else {
                // Handle unsuccessful response
                Log.e("MyActivity", "Failed to send OTP: ${response.errorBody()?.string()}")
            }
        }
        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            // Handle failure
            Log.e("MyActivity", "Network error: ${t.message}")
        }
    })
}