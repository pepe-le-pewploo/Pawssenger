package com.example.pawssenger.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawssenger.EntryPageButtons
import com.example.pawssenger.R
import com.example.pawssenger.data.NavigationDrawerContent
import com.example.pawssenger.data.NavigationDrawerData
import com.example.pawssenger.ui.components.OutlinedTextFiledGenerator
import com.example.pawssenger.ui.components.PawssengerTopAppBar
import com.example.pawssenger.ui.components.PresentDrawerContent
import com.example.pawssenger.ui.theme.PawssengerTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RequestSubmissionPage(
    drawerContent:List<NavigationDrawerData> = NavigationDrawerContent().drawerContent(),
    drawerState: DrawerState,
    scope: CoroutineScope,
    onLogOutClick: () ->Unit,
    onProfileClick:() ->Unit,
    onDashboardClick:() ->Unit,
    onLocateClick:()->Unit,
    onFilterClick:()->Unit,
    selectedItemIndex:Int,
    actionButtonOnClick:()->Unit
) {
    var uri by remember{
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current
    var petName by rememberSaveable {
        mutableStateOf("")
    }
    var pickUpLoacation by rememberSaveable {
        mutableStateOf("")
    }
    var dropOffLoacation by rememberSaveable {
        mutableStateOf("")
    }

    var contactInfo by rememberSaveable {
        mutableStateOf("")
    }

    var addImgae by rememberSaveable {
        mutableStateOf("")
    }

    var ownerName by rememberSaveable {
        mutableStateOf("")
    }

    var payment by rememberSaveable {
        mutableStateOf("")
    }

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
                            4 -> onLogOutClick
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
                PawssengerTopAppBar(drawerState = drawerState, scope = scope, text = R.string.app_name, actionButtonOnClick = actionButtonOnClick)
            }
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
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { fullHeight -> fullHeight }
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(72.dp))

                    //SubmitHeading()

                    //Spacer(modifier = Modifier.height(32.dp))

                    Instuctions()

                    Card(
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextFiledGenerator(
                                onValueChange = {petName = it},
                                labelText = R.string.pet_name,
                                leadingImageVector = null,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {pickUpLoacation = it},
                                labelText = R.string.pick_up_location,
                                leadingImageVector = Icons.Outlined.LocationOn,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {dropOffLoacation = it},
                                labelText = R.string.drop_off_location,
                                leadingImageVector = Icons.Outlined.LocationOn,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {ownerName = it},
                                labelText = R.string.owner_name,
                                leadingImageVector = Icons.Outlined.Person,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {contactInfo = it},
                                labelText = R.string.contact_info,
                                leadingImageVector = Icons.Outlined.Person,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {addImgae = it},
                                labelText = R.string.add_image,
                                leadingImageVector = Icons.Outlined.Add,
                                trailingImageVector = null,
                                iconButtonOnClick = {
                                    singlePhotoPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                visibility = null,
                                error = true
                            )

                            OutlinedTextFiledGenerator(
                                onValueChange = {payment = it},
                                labelText = R.string.payment,
                                leadingImageVector = Icons.Outlined.Money,
                                trailingImageVector = null,
                                iconButtonOnClick = { /*TODO*/ },
                                visibility = null,
                                error = true
                            )

                            EntryPageButtons(
                                enabled=true,
                                text = R.string.submit,
                                onClick = { uri?.let{
                                    Upload(uri=it, context=context,petName,contactInfo,dropOffLoacation,pickUpLoacation, ownerName, payment)
                                }}
                            )
                        }
                    }
                }
            }
        }
    }
}
fun Upload(uri: Uri, context: Context,petName: String,contactInfo:String, dropOffLoacation:String, pickUpLoacation:String,
           ownerName:String, payment:String
){
//    val description=houseDataUiState.value.description
//    val rent=houseDataUiState.value.rent
//    val address=houseDataUiState.value.address
    val unique_image_name = UUID.randomUUID()
    val newPet = hashMapOf(
        "petName" to "$petName",
        "pickUpLocation" to "$pickUpLoacation",
        "dropOffLocation" to "$dropOffLoacation",
        "contactInfo" to "$contactInfo",
        "image" to "https://firebasestorage.googleapis.com/v0/b/pawssenger-d45ea.appspot.com/o/pet%2F${unique_image_name}.jpg?alt=media&token=646d0472-44c2-4916-a5e7-84217d71ad56",
        "ownerName" to "$ownerName",
        "payment" to "$payment"
    )
    val storage= Firebase.storage
    val db = Firebase.firestore

    db.collection("petInfo")
        .add(newPet)
        .addOnSuccessListener { documentReference ->
            Log.d("newPetAdded", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("failedHouseAdded", "Error adding document", e)
        }


    var storageRef = storage.reference
    var spaceRef: StorageReference


    spaceRef = storageRef.child("pet/${unique_image_name}.jpg")

    val byteArray: ByteArray? = context.contentResolver
        .openInputStream(uri)
        ?.use { it.readBytes() }

    byteArray?.let{

        var uploadTask = spaceRef.putBytes(byteArray)
        uploadTask.addOnFailureListener {
            Toast.makeText(
                context,
                "upload failed",
                Toast.LENGTH_SHORT
            ).show()
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            Toast.makeText(
                context,
                "upload successed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
@Composable
fun SubmitHeading() {
    Text(
        text = stringResource(R.string.pet_details),
        style = MaterialTheme.typography.displayMedium,
        fontSize = 32.sp
    )
}

@Composable
fun Instuctions() {
    Text(
        text = "*Fill up this form to submit your transport request"
    )
}

//@Preview
//@Composable
//fun RequestSubmitPagePreview(){
//    PawssengerTheme {
//        RequestSubmissionPage()
//    }
//}