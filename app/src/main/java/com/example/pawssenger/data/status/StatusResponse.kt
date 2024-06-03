package com.example.pawssenger.data.status

data class StatusResponse(
    val version: String,
    val statusCode: String,
    val statusDetail: String,
    val subscriptionStatus: String
)
