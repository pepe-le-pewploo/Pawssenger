package com.example.pawssenger.retrofit.callFunctions

import android.util.Log
import com.example.pawssenger.data.otpVerify.OtpVerifyRespone
import com.example.pawssenger.data.otpVerify.VerifyParameters
import com.example.pawssenger.data.status.StatusResponse
import com.example.pawssenger.data.status.VerifyParametersStatus
import com.example.pawssenger.retrofit.MyApiService
import com.example.pawssenger.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val verifyParameters = VerifyParameters(
    appId = "APP_118867",
    password = "a46785b83fc98b81ae80778189c0687a",
    referenceNo = "",
    otp = ""
)
fun verify(otp: String) {
    verifyParameters.otp = otp
    Log.d("MyActivity", "${verifyParameters}")
    val destinationService = ServiceBuilder.buildService(MyApiService::class.java)
    val requestCall = destinationService.verifyOtp(verifyParameters)

    requestCall.enqueue(object : Callback<OtpVerifyRespone> {
        override fun onResponse(
            call: Call<OtpVerifyRespone>,
            response: Response<OtpVerifyRespone>
        ) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("MyActivity", "OTP verified successfully: $apiResponse")
            } else {
                // Handle unsuccessful response
                Log.e("MyActivity", "Failed to verify OTP: ${response.errorBody()?.string()}")
            }
        }
        override fun onFailure(call: Call<OtpVerifyRespone>, t: Throwable) {
            // Handle failure
            Log.e("MyActivity", "Network error: ${t.message}")
        }
    })
}

fun verifyStatus() {
    val verifyParametersStatus = VerifyParametersStatus(
        appId = "APP_118867",
        password = "a46785b83fc98b81ae80778189c0687a",
        mobile = "8801812498064"
    )
    val destinationService = ServiceBuilder.buildService(MyApiService::class.java)
    val requestCall = destinationService.verifySubscription(verifyParametersStatus)

    requestCall.enqueue(object : Callback<StatusResponse> {
        override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("MyActivity", "Subscription Status verified successfully: $apiResponse")
            } else {
                // Handle unsuccessful response
                Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
            }
        }
        override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
            // Handle failure
            Log.e("MyActivity", "Network error: ${t.message}")
        }
    })
}