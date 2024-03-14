package com.example.pawssenger.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pawssenger.data.NavigationDrawerData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresentDrawerContent(
    index:Int,
    items:NavigationDrawerData,
    selectedIndex:Int,
    onClick: () -> Unit
){
    NavigationDrawerItem(
        label = {
            Text(
                text = stringResource(id = items.name),
            )
        },
        selected = index == selectedIndex,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = if (index == selectedIndex) {
                    items.selectedIcon
                } else items.unselectedIcon,
                contentDescription = null
            )
        }
    )
}