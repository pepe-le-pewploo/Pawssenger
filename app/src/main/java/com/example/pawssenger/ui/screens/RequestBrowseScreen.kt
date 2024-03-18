package com.example.pawssenger.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pawssenger.R
import com.example.pawssenger.data.BrowsePetData
import com.example.pawssenger.data.NavigationDrawerContent
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.data.PetData
import com.example.pawssenger.data.petData.petUiState
import com.example.pawssenger.data.signup.SignUpViewModel
import com.example.pawssenger.ui.components.PawssengerTopAppBar
import com.example.pawssenger.ui.components.PresentDrawerContent
import com.example.pawssenger.ui.theme.PawssengerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    selectedItemIndex:Int,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    var Pets=petData.stateList.value
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
    ) {
        Scaffold(
            topBar = {
                PawssengerTopAppBar(drawerState = drawerState, scope = scope, text = R.string.app_name)
            },
            floatingActionButton = {
                if(!signUpViewModel.registrationUIState.value.asTransporter){
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
                        signUpViewModel = signUpViewModel
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
    signUpViewModel: SignUpViewModel = viewModel()
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
                ) {
                    ExtraInfo(
                        petName = pet.petName,
                        ownerName = "",
                        contactInfo = pet.contactInfo,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                    )

                    Spacer(modifier = Modifier.width(56.dp))

                    if(signUpViewModel.registrationUIState.value.asTransporter){
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ExtraInfoDetails(heading = R.string.name, details = petName)
        ExtraInfoDetails(heading = R.string.owner, details = ownerName)
        ExtraInfoDetails(heading = R.string.contact, details = contactInfo)
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