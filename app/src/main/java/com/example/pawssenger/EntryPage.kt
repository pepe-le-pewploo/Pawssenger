package com.example.pawssenger

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawssenger.ui.components.LogoAndName
import com.example.pawssenger.ui.theme.PawssengerTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EntryPage(
    onClickLogInButton: () -> Unit,
    onClickSignUpButton: () -> Unit
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

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
                    enter = slideInVertically(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                            dampingRatio = Spring.DampingRatioLowBouncy
                        )
                    )
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            LogoAndName()

            Spacer(modifier = Modifier.height(320.dp))

            EntryPageButtons(
                text = R.string.log_in,
                onClick = onClickLogInButton,
                modifier = Modifier.width(128.dp),
                enabled = true
            )

            EntryPageButtons(
                text = R.string.sign_up,
                onClick = onClickSignUpButton,
                modifier = Modifier.width(128.dp),
                enabled = true
            )
        }
    }
}

@Composable
fun EntryPageButtons(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled:Boolean = false
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 10.dp,
            focusedElevation = 12.dp
        ),
        enabled = enabled
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EntryPagePreview() {
    PawssengerTheme(darkTheme = true) {
        EntryPage(
            onClickLogInButton = {},
            onClickSignUpButton = {}
        )
    }
}