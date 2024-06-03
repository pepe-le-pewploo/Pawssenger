package com.example.pawssenger.retrofit

import com.example.pawssenger.data.otpRequest.ApiResponse
import com.example.pawssenger.data.otpRequest.RequestParameters
import com.example.pawssenger.data.otpVerify.OtpVerifyRespone
import com.example.pawssenger.data.otpVerify.VerifyParameters
import com.example.pawssenger.data.status.StatusResponse
import com.example.pawssenger.data.status.VerifyParametersStatus
import com.example.pawssenger.data.subscribe.SubscribeRequestParameters
import com.example.pawssenger.data.subscribe.SubscribeResponse
import com.example.pawssenger.data.unsubscribe.UnsubscribeRequestParameters
import com.example.pawssenger.data.unsubscribe.UnsubscribeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApiService {
    @POST("nazmul/subscription/otp/request")
    fun requestOtp(@Body requestParameters: RequestParameters): Call<ApiResponse>

    @POST("nazmul/subscription/otp/verify")
    fun verifyOtp(@Body verifyParameters: VerifyParameters): Call<OtpVerifyRespone>

    @POST("nazmul/subscription/status")
    fun verifySubscription(@Body verifyParametersStatus: VerifyParametersStatus): Call<StatusResponse>

    @POST("nazmul/subscription/subscribe")
    fun subscribe(@Body subscribeRequestParameters: SubscribeRequestParameters): Call<SubscribeResponse>

    @POST("nazmul/subscription/unsubscribe")
    fun unsubscribe(@Body unsubscribeRequestParameters: UnsubscribeRequestParameters): Call<UnsubscribeResponse>
}