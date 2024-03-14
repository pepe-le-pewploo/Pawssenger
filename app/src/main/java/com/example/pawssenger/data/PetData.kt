package com.example.pawssenger.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PetData(
    @DrawableRes val image:Int,
    @StringRes val petOwner:Int,
    @StringRes val petName:Int,
    @StringRes val pickUpPoint:Int,
    @StringRes val Destination:Int,
    @StringRes val contactInfo:Int
)