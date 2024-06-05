package com.example.pawssenger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawssenger.EntryPageButtons
import com.example.pawssenger.R
import com.example.pawssenger.data.signup.SignUpViewModel
import com.example.pawssenger.data.signup.SignupUIEvent
import com.example.pawssenger.retrofit.callFunctions.verify
import com.example.pawssenger.retrofit.callFunctions.verifyStatus
import com.example.pawssenger.ui.components.OutlinedTextFiledGenerator
import com.example.pawssenger.ui.theme.PawssengerTheme

@Composable
fun OtpVerifyScreen(
    navController: NavController = rememberNavController(),
    signUpViewModel: SignUpViewModel = SignUpViewModel(),

    ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var otp by rememberSaveable {
            mutableStateOf("")
        }
        OutlinedTextFiledGenerator(
            onValueChange = {otp = it},
            labelText = R.string.OTP,
            leadingImageVector = Icons.Filled.PhoneAndroid,
            trailingImageVector = null,
            iconButtonOnClick = { /*TODO*/ },
            visibility = null,
            error = true,
            modifier = Modifier.fillMaxWidth(9/11f)
        )

        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            EntryPageButtons(
//                text = R.string.subscribe,
//                onClick = { /*TODO*/ },
//                modifier = Modifier.width(140.dp),
//                enabled = true
//            )
//
//            EntryPageButtons(
//                text = R.string.unsubscribe,
//                onClick = { /*TODO*/ },
//                modifier = Modifier.width(140.dp),
//                enabled = true
//            )
//        }

        EntryPageButtons(
                text = R.string.verify,
                onClick = { verify(otp = otp, navController = navController) },
                modifier = Modifier.width(140.dp),
                enabled = true
            )
    }
}

@Preview(showBackground = true)
@Composable
fun SubscribeScreenPreview() {
    PawssengerTheme {
        OtpVerifyScreen()
    }
}