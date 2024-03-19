package com.example.pawssenger.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pawssenger.EntryPage
import com.example.pawssenger.data.BrowsePetData
import com.example.pawssenger.data.login.LogInViewModel
import com.example.pawssenger.data.login.LoginUIEvent
import com.example.pawssenger.data.NavigationDrawerContent
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.data.ProfileViewModel
import com.example.pawssenger.data.signup.SignUpViewModel
import com.example.pawssenger.data.signup.SignupUIEvent
import com.example.pawssenger.ui.screens.LogInPage
import com.example.pawssenger.ui.screens.ProfilePage
import com.example.pawssenger.ui.screens.RequestBrowser
import com.example.pawssenger.ui.screens.RequestSubmissionPage
import com.example.pawssenger.ui.screens.SignUpPage

enum class PawssengerScreen() {
    Entry,
    Login,
    SignUp,
    RequestBrowse,
    RequestSubmit,
    Profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PawssengerApp(
    navController: NavHostController = rememberNavController(),
    signUpViewModel: SignUpViewModel = viewModel(),
    logInViewModel: LogInViewModel = viewModel(),
    //profileViewModel: ProfileViewModel = viewModel()
) {
    val TAG = "navigation"
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = PawssengerScreen.valueOf(
        backStackEntry?.destination?.route ?: PawssengerScreen.Entry.name
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerContent:List<NavigationDrawerData> = NavigationDrawerContent().drawerContent()

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var fromLogIn by rememberSaveable {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = PawssengerScreen.Entry.name,
        modifier = Modifier.padding(8.dp)
    ) {
        composable(route = PawssengerScreen.Entry.name) {
            EntryPage(
                onClickLogInButton = {
                    navController.navigate(PawssengerScreen.Login.name)
                },
                onClickSignUpButton = {
                    navController.navigate(PawssengerScreen.SignUp.name)
                }
            )
        }
        composable(route = PawssengerScreen.Login.name) {
            LogInPage(
                onClickLogInButton = {
                    fromLogIn = true
                    logInViewModel.onEvent(LoginUIEvent.LoginButtonClicked(navController = navController))
                },
                onClickSignUpButton = {
                    navController.navigate(PawssengerScreen.SignUp.name)
                },
                logInViewModel = logInViewModel
            )
        }

        composable(route = PawssengerScreen.SignUp.name) {
            SignUpPage(
                onClickLogInButton = {
                    navController.navigate(PawssengerScreen.Login.name)
                },
                onClickSignUpButton = {
                    fromLogIn = false
                    signUpViewModel.onEvent(SignupUIEvent.SignUpButtonClicked(navController = navController))
                },
                signUpViewModel = signUpViewModel
            )
        }
        composable(route = PawssengerScreen.RequestBrowse.name){
            RequestBrowser(
                pets = BrowsePetData().getPetData(),
                onClickFloatingActionButton = {
                    navController.navigate(PawssengerScreen.RequestSubmit.name)
                },
                drawerState = drawerState,
                scope = scope,
                drawerContent = drawerContent,
                onProfileClick = {
                    selectedItemIndex = 0
                    navController.navigate(route = PawssengerScreen.Profile.name)
                                 },
                onDashboardClick = {selectedItemIndex = 1},
                onLocateClick = {selectedItemIndex = 2},
                onLogOutClick = {
                    selectedItemIndex = 4
                    navController.popBackStack(route = PawssengerScreen.Entry.name, inclusive = false)
                },
                onFilterClick = {
                    selectedItemIndex = 3
                },
                selectedItemIndex = selectedItemIndex,
                loginViewModel = logInViewModel,
                signUpViewModel = signUpViewModel,
                actionButtonOnClick = {
                    navController.navigate(route = PawssengerScreen.Profile.name)
                },
                fromLogIn = fromLogIn
                //profileViewModel = profileViewModel
            )
        }

        composable(route = PawssengerScreen.RequestSubmit.name){
            RequestSubmissionPage(
                drawerContent = drawerContent,
                drawerState = drawerState,
                scope = scope,
                onProfileClick = {
                    selectedItemIndex = 0

                },
                onDashboardClick = {
                    selectedItemIndex = 1
                },
                onLocateClick = {
                    selectedItemIndex = 2
                },
                onLogOutClick = {
                    selectedItemIndex = 4
                    logInViewModel.onEvent(LoginUIEvent.LogoutButtonClicked)
                    navController.popBackStack(route = PawssengerScreen.Entry.name, inclusive = false)
                },
                onFilterClick = {
                    selectedItemIndex = 3
                },
                selectedItemIndex = selectedItemIndex,
                actionButtonOnClick = {
                    navController.navigate(route = PawssengerScreen.RequestBrowse.name)
                }
            )
        }
        
        composable(route = PawssengerScreen.Profile.name) {
            ProfilePage(
                drawerContent = drawerContent,
                drawerState = drawerState,
                scope = scope,
                onProfileClick = {
                    selectedItemIndex = 0
                    navController.navigate(route = PawssengerScreen.Profile.name)
                },
                onDashboardClick = {
                    selectedItemIndex = 1
                },
                onLocateClick = {
                    selectedItemIndex = 2
                },
                onLogOutClick = {
                    selectedItemIndex = 4
                    logInViewModel.onEvent(LoginUIEvent.LogoutButtonClicked)
                    navController.popBackStack(route = PawssengerScreen.Entry.name, inclusive = false)
                },
                onFilterClick = {
                    selectedItemIndex = 3
                },
                selectedItemIndex = selectedItemIndex,
                actionButtonOnClick = {
                    navController.navigate(route = PawssengerScreen.RequestBrowse.name)
                },
                loginViewModel = logInViewModel,
                signUpViewModel = signUpViewModel,
                fromLogIn = fromLogIn
                //profileViewModel = profileViewModel
            )
            //Profile()
        }
    }
}