package com.example.pawssenger.data.login

import androidx.navigation.NavController
import java.lang.IllegalStateException

sealed class LoginUIEvent {
    data class EmailChanged(val email:String): LoginUIEvent()
    data class PasswordChanged(val password:String): LoginUIEvent()
    data class LoginButtonClicked(val navController: NavController): LoginUIEvent()
    object LogoutButtonClicked: LoginUIEvent()
}
