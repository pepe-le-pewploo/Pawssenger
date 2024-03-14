package com.example.pawssenger.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawssenger.EntryPageButtons
import com.example.pawssenger.R
import com.example.pawssenger.data.LogInViewModel
import com.example.pawssenger.data.LoginUIEvent
import com.example.pawssenger.ui.components.LogoAndName
import com.example.pawssenger.ui.components.OutlinedTextFiledGenerator
import com.example.pawssenger.ui.theme.PawssengerTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LogInPage(
    onClickLogInButton: () -> Unit,
    onClickSignUpButton: () -> Unit,
    logInViewModel: LogInViewModel = viewModel()
) {
    var passWordVisibility by remember {
        mutableStateOf(false)
    }
    var error by remember {
        mutableStateOf(false)
    }

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            ),

            exit = fadeOut(),
            modifier = Modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .animateEnterExit(
                        enter = slideInHorizontally(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioLowBouncy
                            )
                        )
                    )
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                LogoAndName()

                Card(
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextFiledGenerator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            labelText = R.string.username,
                            leadingImageVector = Icons.Outlined.Email,
                            trailingImageVector = null,
                            onValueChange = {
                                logInViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                            },
                            iconButtonOnClick = {},
                            visibility = null,
                            error = logInViewModel.loginUIState.value.emailError
                        )

                        OutlinedTextFiledGenerator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            labelText = R.string.password,
                            leadingImageVector = Icons.Outlined.Lock,
                            trailingImageVector = if (passWordVisibility) Icons.Outlined.Visibility
                            else Icons.Outlined.VisibilityOff,
                            onValueChange = {
                                logInViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                            },
                            iconButtonOnClick = { passWordVisibility = !passWordVisibility },
                            visibility = passWordVisibility,
                            error = logInViewModel.loginUIState.value.passwordError
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EntryPageButtons(
                            text = R.string.log_in, onClick = onClickLogInButton,
                            modifier = Modifier.width(128.dp),
                            enabled = logInViewModel.allValidationsPassed.value
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.newuser),
                        modifier = Modifier.padding(top = 14.dp)
                    )

                    TextButton(
                        onClick = onClickSignUpButton
                    ) {
                        Text(text = stringResource(id = R.string.sign_up))
                    }
                }
            }
        }
        if(logInViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPagePreview() {
    PawssengerTheme {
        LogInPage(
            onClickLogInButton = {},
            onClickSignUpButton = {}
        )
    }
}