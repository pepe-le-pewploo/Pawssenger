package com.example.pawssenger.retrofit.callFunctions

import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawssenger.R
import com.example.pawssenger.data.subscribe.SubscribeRequestParameters
import com.example.pawssenger.data.subscribe.SubscribeResponse
import com.example.pawssenger.data.unsubscribe.UnsubscribeRequestParameters
import com.example.pawssenger.data.unsubscribe.UnsubscribeResponse
import com.example.pawssenger.retrofit.MyApiService
import com.example.pawssenger.retrofit.ServiceBuilder
import com.example.pawssenger.ui.screens.subscriptionStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val subscribeRequestParameters = SubscribeRequestParameters(
    appId = "APP_118867",
    password = "a46785b83fc98b81ae80778189c0687a",
    mobile = ""
)

val unsubscribeRequestParameters = UnsubscribeRequestParameters(
    appId = "APP_118867",
    password = "a46785b83fc98b81ae80778189c0687a",
    mobile = ""
)
fun subscriptionOn(navController: NavController, popBack: Boolean = false) {
    val destinationService = ServiceBuilder.buildService(MyApiService::class.java)
    val requestCall = destinationService.subscribe(subscribeRequestParameters)

    requestCall.enqueue(object : Callback<SubscribeResponse> {
        override fun onResponse(
            call: Call<SubscribeResponse>,
            response: Response<SubscribeResponse>
        ) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("MyActivity", "Subscription request sent successfully: $apiResponse")
                if(apiResponse?.subscriptionStatus == "INITIAL CHARGING PENDING") {
                    subscriptionStatus.isRegistered = true
                    verifyStatus(navController, popBack)
                } else {
                    //Log.e("MyActivity", "Confirm Subscription and press the button again")

                }
            } else {
                // Handle unsuccessful response
                Log.e("MyActivity", "Failed to send subscription request: ${response.errorBody()?.string()}")
            }
        }
        override fun onFailure(call: Call<SubscribeResponse>, t: Throwable) {
            // Handle failure
            Log.e("MyActivity", "Network error: ${t.message}")
        }
    })
}

fun subscriptionOff(navController: NavController, popBack: Boolean = false) {
    val destinationService = ServiceBuilder.buildService(MyApiService::class.java)
    val requestCall = destinationService.unsubscribe(unsubscribeRequestParameters)

    requestCall.enqueue(object : Callback<UnsubscribeResponse> {
        override fun onResponse(
            call: Call<UnsubscribeResponse>,
            response: Response<UnsubscribeResponse>
        ) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("MyActivity", "Unsubscription request sent successfully: $apiResponse")
                if(apiResponse?.subscriptionStatus == "UNREGISTERED") {
                    subscriptionStatus.isRegistered = false
                    verifyStatus(navController, popBack)
                }
            } else {
                // Handle unsuccessful response
                Log.e("MyActivity", "Failed to send unsubscription request: ${response.errorBody()?.string()}")
            }
        }
        override fun onFailure(call: Call<UnsubscribeResponse>, t: Throwable) {
            // Handle failure
            Log.e("MyActivity", "Network error: ${t.message}")
        }
    })
}