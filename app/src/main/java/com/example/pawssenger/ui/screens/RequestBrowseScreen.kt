package com.example.pawssenger.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pawssenger.R
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.data.PetData
import com.example.pawssenger.data.ProfileUiState
import com.example.pawssenger.data.ProfileViewModel
import com.example.pawssenger.data.login.LogInViewModel
import com.example.pawssenger.data.petData.petUiState
import com.example.pawssenger.data.signup.SignUpViewModel
import com.example.pawssenger.data.status.SubscriptionStatus
import com.example.pawssenger.retrofit.callFunctions.subscribeRequestParameters
import com.example.pawssenger.retrofit.callFunctions.subscriptionOff
import com.example.pawssenger.retrofit.callFunctions.subscriptionOn
import com.example.pawssenger.retrofit.callFunctions.unsubscribeRequestParameters
import com.example.pawssenger.retrofit.callFunctions.verifyStatus
import com.example.pawssenger.ui.components.PawssengerTopAppBar
import com.example.pawssenger.ui.components.PresentDrawerContent
import com.google.android.play.integrity.internal.s
import kotlinx.coroutines.CoroutineScope

val subscriptionStatus = SubscriptionStatus(isRegistered = false)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestBrowser(
    petData:com.example.pawssenger.data.petData.PetData= viewModel(),
    pets: List<PetData>,
    onClickFloatingActionButton:() -> Unit,
    drawerContent:List<NavigationDrawerData>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onProfileClick:() -> Unit,
    onDashboardClick:() -> Unit,
    onLocateClick:() -> Unit,
    onLogOutClick:() -> Unit,
    onFilterClick:() -> Unit,
    selectedItemIndex:Int,
    signUpViewModel: SignUpViewModel = viewModel(),
    actionButtonOnClick:()->Unit,
    loginViewModel: LogInViewModel= viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    fromLogIn:Boolean,
    navController: NavController = rememberNavController()
) {
    val email= if(!fromLogIn) signUpViewModel.loginUIState.value.email else loginViewModel.loginUIState.value.email
    val profiles=profileViewModel.stateList
    var profile= mutableStateOf(ProfileUiState())
    for(p in profiles.value){
        if(p.email==email){
            profile.value=p
            Log.d("AgainMySelfTag", p.toString())
        }
    }

    var Pets=petData.stateList.value
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
    ) {
        Scaffold(
            topBar = {
                PawssengerTopAppBar(drawerState = drawerState, scope = scope, text = R.string.app_name, actionButtonOnClick = actionButtonOnClick)
            },
            floatingActionButton = {
                if(profile.value.role == "Pet Owner" && subscriptionStatus.isRegistered){
                    FloatingActionButton(
                        onClick = onClickFloatingActionButton,
                        containerColor = MaterialTheme.colorScheme.background
                    ) {
                        Icon(
                            Icons.Outlined.Add, contentDescription = null
                        )
                    }
                }
            }
        ) { it ->
            LazyColumn(contentPadding = it) {
                items(Pets) {
                    PetItem(
                        pet = it,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                        profile = profile
                    )
                }
            }
        }

    }
}

@Composable
fun PetItem(
    pet: petUiState,
    modifier: Modifier = Modifier,
    profile: MutableState<ProfileUiState>
) {

    var expanded by remember { mutableStateOf(false) }
    var acceptState by rememberSaveable {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (!acceptState) {
            if (expanded) MaterialTheme.colorScheme.tertiaryContainer
            else MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.secondaryContainer
        },
        label = ""
    )
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PetImage(pet.image)
                TransportInfo(pet.pickUpLocation, pet.dropOffLocation)
                Spacer(modifier = modifier.weight(1f))
                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
//                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ExtraInfo(
                        petName = pet.petName,
                        ownerName = pet.ownerName,
                        contactInfo = pet.contactInfo,
                        payment = pet.payment,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                    )

                    Spacer(modifier = Modifier.width(56.dp))

                    if(profile.value.role == "Transporter" && subscriptionStatus.isRegistered){
                        Button(
                            onClick = { acceptState = true },
                            modifier = Modifier.width(128.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 12.dp,
                                pressedElevation = 12.dp,
                                disabledElevation = 0.dp,
                                hoveredElevation = 12.dp,
                                focusedElevation = 12.dp
                            )
                        ) {
                            if (acceptState) {
                                Text(text = "Accepted")
                            } else {
                                Text(text = "Accept")
                            }
                        }
                    }

//                    Button(
//                        onClick = { acceptState = true },
//                        modifier = Modifier.width(128.dp),
//                        elevation = ButtonDefaults.buttonElevation(
//                            defaultElevation = 12.dp,
//                            pressedElevation = 12.dp,
//                            disabledElevation = 0.dp,
//                            hoveredElevation = 12.dp,
//                            focusedElevation = 12.dp
//                        )
//                    ) {
//                        if (acceptState) {
//                            Text(text = "Accepted")
//                        } else {
//                            Text(text = "Accept")
//                        }
//                    }
                }
            }
        }
    }
}


@Composable
fun PetImage(
     petImage: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = petImage,
        contentDescription = null,
        modifier = modifier
            .size(96.dp)
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TransportInfo(
   pickUpPoint: String,
   destination:String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LocationInfo(location = pickUpPoint, description = R.string.pickuplocation)
        LocationInfo(location = destination, description = R.string.destination)
    }
}

@Composable
fun LocationInfo(location: String, description: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = description),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = location,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
    }

}

@Composable
private fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ExtraInfo(
     petName: String,
    ownerName: String,
    contactInfo: String,
     payment:String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ExtraInfoDetails(heading = R.string.name, details = petName)
        ExtraInfoDetails(heading = R.string.owner, details = ownerName)
        ExtraInfoDetails(heading = R.string.contact, details = contactInfo)
        ExtraInfoDetails(heading = R.string.payment, details = payment)
    }
}

@Composable
fun ExtraInfoDetails(heading: Int, details: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = heading),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = details,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )

    }

}

//@Preview
//@Composable
//fun RequestBrowserPreview() {
//    PawssengerTheme {
//        RequestBrowser(
//            pets = BrowsePetData().getPetData(),
//            onClickFloatingActionButton = {},
//            profileOnClick = {},
//            dashBoardOnClick = {},
//            logOutOnClick = {},
//            locateOnClick = {}
//        )
//    }
//}