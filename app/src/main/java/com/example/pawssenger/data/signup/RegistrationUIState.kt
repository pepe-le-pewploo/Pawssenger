package com.example.pawssenger.data.signup

data class RegistrationUIState(
    var firstName: String="",
    var lastName: String="",
    var email:String="",
    var password:String="",
    val userName:String="",
    var asUser: Boolean = true,
    val asTransporter: Boolean = false,

    var firstNameError :Boolean = false,
    var lastNameError : Boolean = false,
    var emailError :Boolean = false,
    var userNameError:Boolean = false,
    var passwordError : Boolean = false,
    var privacyPolicyError:Boolean = false
)
