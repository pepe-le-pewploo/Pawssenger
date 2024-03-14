package com.example.pawssenger.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pawssenger.ui.navigation.PawssengerScreen
import com.google.firebase.auth.FirebaseAuth
import com.nativemobilebits.loginflow.data.rules.Validator

class LogInViewModel : ViewModel() {
    private val TAG = LogInViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    fun onEvent(event: LoginUIEvent) {
        validateLoginUIDataWithRules()
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )

            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login(
                    email = loginUIState.value.email,
                    password = loginUIState.value.password,
                    navController = event.navController
                )
            }

            is LoginUIEvent.LogoutButtonClicked -> {
                logout()
                loginUIState.value = loginUIState.value.copy(
                    email = ""
                )
                loginUIState.value = loginUIState.value.copy(
                    password = ""
                )
            }
        }
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }

    private fun login(
        email: String,
        password: String,
        navController: NavController
    ) {
        loginInProgress.value = true
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_login_Success")
                Log.d(TAG, "&{it.isSuccessful}")
                if (it.isSuccessful) {
                    loginInProgress.value = false
                    navController.navigate(PawssengerScreen.RequestBrowse.name)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG, it.localizedMessage)

                loginInProgress.value = false
            }
    }
}