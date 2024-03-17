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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.pawssenger.ui.theme.PawssengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage() {
    Scaffold(
        topBar = {
            ProfileTopBar()
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

                Spacer(modifier = Modifier.height(64.dp))

                CardStuffs()
            }

        }
    }
}

@Composable
fun giveSpace(){
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun CardStuffs() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MakeCards(text = "Preferences", imageVector = Icons.Default.Add)
//        Spacer(modifier = Modifier.width(20.dp))
        MakeCards(text = "Listings", imageVector = Icons.Default.Home)
    }
}

@Composable
fun MakeCards(text:String, imageVector: ImageVector){
    Card(
        modifier = Modifier
            .size(150.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text)
            Icon(imageVector = imageVector, contentDescription =null )

        }
    }
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
        ProfilePage()
    }
}


