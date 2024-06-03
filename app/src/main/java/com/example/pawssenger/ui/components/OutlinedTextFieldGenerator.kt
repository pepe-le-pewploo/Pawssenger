package com.example.pawssenger.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawssenger.R
import com.example.pawssenger.ui.theme.PawssengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFiledGenerator(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    labelText: Int,
    leadingImageVector: ImageVector?,
    trailingImageVector: ImageVector?,
    iconButtonOnClick: () -> Unit,
    visibility: Boolean?,
    error:Boolean
) {
    var filledText by rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = filledText,
        onValueChange = {
            filledText = it
            onValueChange(it)
        },
        label = {
            Text(
                text = stringResource(id = labelText),
                textAlign = TextAlign.Center
            )
        },
        leadingIcon = {
            if (leadingImageVector != null) {
                IconButton(onClick = iconButtonOnClick){
                    Icon(
                        imageVector = leadingImageVector,
                        contentDescription = null
                    )
                }
            }
        },

        trailingIcon = {
            IconButton(onClick = iconButtonOnClick) {
                if (trailingImageVector != null) {
                    Icon(
                        imageVector = trailingImageVector,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,

        visualTransformation = if (visibility != null) {
            if (visibility) VisualTransformation.None else PasswordVisualTransformation()
        } else VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType = if (visibility != null) KeyboardType.Password
            else KeyboardType.Email,
            imeAction = if(visibility!=null) ImeAction.Done else ImeAction.Next
        ),
        isError = !error,
        modifier = modifier

    )
}

//@Preview(showBackground = true)
//@Composable
//fun OutlinedFieldPreview() {
//    PawssengerTheme {
//        OutlinedTextFiledGenerator(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            labelText = R.string.username,
//            leadingImageVector = Icons.Outlined.Email,
//            trailingImageVector = Icons.Outlined.VisibilityOff,
//
//        )
//    }
//}