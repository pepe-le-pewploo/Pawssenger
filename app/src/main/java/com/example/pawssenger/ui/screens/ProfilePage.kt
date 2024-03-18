package com.example.pawssenger.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawssenger.R
import com.example.pawssenger.data.NavigationDrawerContent
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.ui.components.PawssengerTopAppBar
import com.example.pawssenger.ui.components.PresentDrawerContent
import com.example.pawssenger.ui.theme.PawssengerTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    drawerContent:List<NavigationDrawerData> = NavigationDrawerContent().drawerContent(),
    drawerState: DrawerState,
    scope: CoroutineScope,
    onLogOutClick: () ->Unit,
    onProfileClick:() ->Unit,
    onDashboardClick:() ->Unit,
    onLocateClick:()->Unit,
    selectedItemIndex:Int
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
            ) {
                LazyColumn(contentPadding = NavigationDrawerItemDefaults.ItemPadding){
                    itemsIndexed(drawerContent) { index, items ->
                        val passFunction:()->Unit = when(index){
                            0 -> onProfileClick
                            1 -> onDashboardClick
                            2 -> onLocateClick
                            3 -> onLogOutClick
                            else -> { {} }
                        }
                        PresentDrawerContent(
                            index = index,
                            items = items,
                            selectedIndex = selectedItemIndex,
                            onClick = passFunction
                        )
                        Divider()
                    }

                }
            }
        }
    ){
        Scaffold(
            topBar = {
                PawssengerTopAppBar(scope = scope, text = R.string.profile, drawerState = drawerState)
            }
        ) { it ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(it)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.minty),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                ) {
                    Detail(
                        imageVector = Icons.Default.Person,
                        headText = "Name",
                        descriptText = "Nazmul Hossen Rahul"
                    )

                    giveSpace()

                    Detail(
                        imageVector = Icons.Default.Email,
                        headText = "Email Id",
                        descriptText = "nazmul@gmail.com"
                    )

                    giveSpace()

                    Detail(
                        imageVector = Icons.Default.Phone,
                        headText = "Contact No.",
                        descriptText = "012345678"
                    )

                    giveSpace()

                    Detail(imageVector = Icons.Default.Group,
                        headText = "User Role",
                        descriptText = "Pet Owner"
                    )
                }

            }
        }
    }
}

@Composable
fun giveSpace(){
    Spacer(modifier = Modifier.height(12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(

) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Profile")
        },
        navigationIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
        },
        actions = {
            IconButton(
                onClick = {}
            ){
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
            }
        }
    )
}

@Composable
fun Detail(imageVector: ImageVector, headText: String, descriptText: String) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = imageVector, contentDescription = null)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = headText,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descriptText
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePagePreview() {
    PawssengerTheme {
//        ProfilePage(
//            drawerState = ,
//            scope = ,
//            onLogOutClick = { /*TODO*/ },
//            onProfileClick = { /*TODO*/ },
//            onDashboardClick = { /*TODO*/ },
//            onLocateClick = { /*TODO*/ },
//            selectedItemIndex =
//        )
    }
}


