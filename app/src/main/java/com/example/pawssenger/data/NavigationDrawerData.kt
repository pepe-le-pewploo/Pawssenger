package com.example.pawssenger.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pawssenger.R

data class NavigationDrawerData(
    @StringRes val name: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class NavigationDrawerContent(){
    fun drawerContent():List<NavigationDrawerData>
    {
        return listOf<NavigationDrawerData>(
            NavigationDrawerData(
                name = R.string.profile,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            ),
            NavigationDrawerData(
                name = R.string.dashboard,
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List
            ),
            NavigationDrawerData(
                name = R.string.locate,
                selectedIcon = Icons.Filled.LocationOn,
                unselectedIcon = Icons.Outlined.LocationOn
            ),
            NavigationDrawerData(
                name = R.string.filter,
                selectedIcon = Icons.Filled.FilterList,
                unselectedIcon = Icons.Outlined.FilterList
            ),
            NavigationDrawerData(
                name = R.string.logout,
                selectedIcon = Icons.Filled.ExitToApp,
                unselectedIcon = Icons.Outlined.ExitToApp
            )
        )
    }

}
