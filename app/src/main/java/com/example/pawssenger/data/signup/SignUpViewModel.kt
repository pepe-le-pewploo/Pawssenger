package com.example.pawssenger.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pawssenger.ui.navigation.PawssengerScreen
import com.google.firebase.auth.FirebaseAuth
import com.nativemobilebits.loginflow.data.rules.Validator

class SignUpViewModel: ViewModel() {

    private val  TAG = SignUpViewModel::class.simpleName

    var allValidationsPassed = mutableStateOf(false)

    var registrationUIState = mutableStateOf(RegistrationUIState())
    var signUpInProgress = mutableStateOf(false)

    fun onEvent(event: SignupUIEvent) {
        validateDataWithRules()
        when(event){
            is SignupUIEvent.FirstNameChanged->{
                registrationUIState.value=registrationUIState.value.copy(
                    firstName=event.firstName
                )
                printState()
            }
            is SignupUIEvent.LastNameChanged->{
                registrationUIState.value=registrationUIState.value.copy(
                    lastName = event.lastName
                )
                printState()
            }
            is SignupUIEvent.EmailChanged->{
                registrationUIState.value=registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }
            is SignupUIEvent.PasswordChanged->{
                registrationUIState.value=registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }
            is SignupUIEvent.SignUpButtonClicked->{
                signUp(navController = event.navController)
                printState()
            }

            is SignupUIEvent.UsernameChanged->{
                registrationUIState.value = registrationUIState.value.copy(
                    userName = event.userName
                )
            }
        }
    }

    private fun signUp(navController:NavController) {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email=registrationUIState.value.email,
            password=registrationUIState.value.password,
            navController = navController
        )
    }

    private fun createUserInFirebase(email:String,password:String, navController: NavController){
        signUpInProgress.value = true
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, " isSuccessful = ${it.isSuccessful}")
                signUpInProgress.value = false
                if(it.isSuccessful){
                    navController.navigate(PawssengerScreen.RequestBrowse.name)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception= ${it.message}")
                Log.d(TAG, "Exception= ${it.localizedMessage}")
            }
    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val userNameResult = Validator.validateUserName(
            uName = registrationUIState.value.userName
        )


        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            userNameError = userNameResult.status
        )

        allValidationsPassed.value = fNameResult.status && lNameResult.status &&
                emailResult.status && passwordResult.status && userNameResult.status
    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }
}