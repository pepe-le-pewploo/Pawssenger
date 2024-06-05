package com.example.pawssenger.ui.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.Subscriptions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pawssenger.R
import com.example.pawssenger.data.NavigationDrawerContent
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.data.ProfileUiState
import com.example.pawssenger.data.ProfileViewModel
import com.example.pawssenger.data.login.LogInViewModel
import com.example.pawssenger.data.login.LoginUIEvent
import com.example.pawssenger.data.signup.SignUpViewModel
import com.example.pawssenger.retrofit.callFunctions.subscribeRequestParameters
import com.example.pawssenger.retrofit.callFunctions.subscriptionOff
import com.example.pawssenger.retrofit.callFunctions.subscriptionOn
import com.example.pawssenger.retrofit.callFunctions.unsubscribeRequestParameters
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
    onFilterClick:()->Unit,
    selectedItemIndex:Int,
    loginViewModel:LogInViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    profileViewModel: ProfileViewModel =viewModel(),
    actionButtonOnClick:() -> Unit,
    fromLogIn:Boolean = false,
    navController: NavController = rememberNavController()
) {
    val email= if(!fromLogIn) signUpViewModel.loginUIState.value.email else loginViewModel.loginUIState.value.email
    val profiles=profileViewModel.stateList
    var profile= mutableStateOf(ProfileUiState())
    for(p in profiles.value){
        if(p.email==email){
            profile.value=p
            Log.d("MySelfTag", p.toString())
        }
    }
    val context = LocalContext.current
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
                            3 -> onFilterClick
                            4 -> {
                                {
                                    subscribeRequestParameters.mobile = profile.value.contactNo
                                    subscriptionOn(navController = navController, popBack = true, context = context)
                                }
                            }
                            5 -> {
                                {
                                    unsubscribeRequestParameters.mobile = profile.value.contactNo
                                    subscriptionOff(navController = navController, popBack = true)
                                }
                            }
                            6 -> onLogOutClick
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
                PawssengerTopAppBar(scope = scope, text = R.string.profile, drawerState = drawerState, actionButtonOnClick = actionButtonOnClick)
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
                        descriptText = "${profile.value.firstName} ${profile.value.lastName}"
                    )

                    giveSpace()

                    Detail(
                        imageVector = Icons.Default.Email,
                        headText = "Email Id",
                        descriptText = "${profile.value.email}"
                    )

                    giveSpace()

                    Detail(
                        imageVector = Icons.Default.Phone,
                        headText = "Contact No.",
                        descriptText = "${profile.value.contactNo}"
                    )

                    giveSpace()

                    Detail(imageVector = Icons.Default.Group,
                        headText = "User Role",
                        descriptText = "${profile.value.role}"
                    )

                    giveSpace()

                    Detail(imageVector = Icons.Default.Subscriptions,
                        headText = "Subscription Status",
                        descriptText = if(subscriptionStatus.isRegistered) "Registered" else "Unregistered"
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

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Profile(loginViewModel: LogInViewModel = viewModel(), profileViewModel: ProfileViewModel =viewModel()) {
//    val email=loginViewModel.loginUIState.value.email
//    val profiles=profileViewModel.stateList
//    var profile= mutableStateOf(ProfileUiState())
//    for(p in profiles.value){
//        if(p.Email==email){
//            profile.value=p
//        }
//    }
//    Scaffold(
//        topBar = {
//            ProfileTopBar()
//        }
//    ) { it ->
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(it)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.minty),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
//            ) {
//                Detail(
//                    imageVector = Icons.Default.Person,
//                    headText = "Name",
//                    descriptText = "${profile.value.Fname} ${profile.value.Lname}"
//                )
//
//                giveSpace()
//
//                Detail(
//                    imageVector = Icons.Default.Email,
//                    headText = "Email Id",
//                    descriptText = "${profile.value.Email}"
//                )
//
//                giveSpace()
//
//                Detail(
//                    imageVector = Icons.Default.Phone,
//                    headText = "Contact No.",
//                    descriptText = "012345678"
//                )
//
//                Spacer(modifier = Modifier.height(64.dp))
//
//               // CardStuffs()
//            }
//
//        }
//    }
////    SystemBackButtonHandler {
////        AppRouter.navigateTo(Screen.HomeScreen)
////    }
//}
//
//@Composable
//fun giveSpace(){
//    Spacer(modifier = Modifier.height(12.dp))
//}
//
////@Composable
////fun CardStuffs() {
////    Row(
////        modifier = Modifier.fillMaxWidth(),
////        horizontalArrangement = Arrangement.SpaceEvenly
////    ) {
////        MakeCards(event={AppRouter.navigateTo(Screen.Preference)},text = "Preferences", imageVector = Icons.Default.Add)
//////        Spacer(modifier = Modifier.width(20.dp))
////        MakeCards(event={AppRouter.navigateTo(Screen.MyListings)},text = "Listings", imageVector = Icons.Default.Home)
////    }
////}
//
////@Composable
////fun MakeCards(event:()->Unit,text:String, imageVector: ImageVector){
////    Card(
////        modifier = Modifier
////            .size(150.dp)
////            .clickable {event.invoke() }
////    ) {
////        Column(
////            modifier = Modifier.fillMaxSize(),
////            horizontalAlignment = Alignment.CenterHorizontally,
////            verticalArrangement = Arrangement.Center
////        ) {
////            Text(text = text)
////            Icon(imageVector = imageVector, contentDescription =null )
////
////        }
////    }
////}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileTopBar(
//    loginViewModel: LogInViewModel=viewModel()
//) {
//    CenterAlignedTopAppBar(
//        title = {
//            Text(text = "Profile")
//        },
//        navigationIcon = {
//            IconButton(
//                onClick = {}
//            ){
//                Icon(imageVector = Icons.Default.Home, contentDescription = null)
//            }
//        },
//        actions = {
//            IconButton(
//                onClick = {loginViewModel.onEvent(LoginUIEvent.LogoutButtonClicked)}
//            ){
//                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
//            }
//        }
//    )
//}
//
//@Composable
//fun Detail(imageVector: ImageVector, headText: String, descriptText: String) {
//    Row(
//        modifier = Modifier.padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(imageVector = imageVector, contentDescription = null)
//
//        Spacer(modifier = Modifier.width(12.dp))
//
//        Column {
//            Text(
//                text = headText,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = descriptText
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProfilePagePreview() {
//    PawssengerTheme {
//        Profile()
//    }
//}

