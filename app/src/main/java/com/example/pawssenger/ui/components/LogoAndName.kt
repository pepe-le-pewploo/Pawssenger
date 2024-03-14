package com.example.pawssenger.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pawssenger.R

@Composable
fun LogoAndName() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier.size(264.dp),
        contentScale = ContentScale.Fit
    )

    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.displayLarge
    )

    Text(
        text = stringResource(R.string.app_description)
    )
}