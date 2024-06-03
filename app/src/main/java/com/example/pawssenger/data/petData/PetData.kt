package com.example.pawssenger.data.petData

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PetData:ViewModel() {
    var stateList= mutableStateOf(mutableListOf(petUiState()))
    init{
        getData()
    }
    private fun getData(){
        viewModelScope.launch {
            stateList.value= getDataFromFirestore()
        }
    }
}
suspend fun getDataFromFirestore(): MutableList<petUiState> {
    val db= FirebaseFirestore.getInstance()

    var aboutList= mutableListOf<petUiState>()
    try{
        db.collection("petInfo")
            .get()
            .await().map{
                Log.d("hello","$it")
                val result= it.toObject(petUiState::class.java)
                aboutList.add(result)
            }
    }catch (e: FirebaseFirestoreException){
        Log.d("error","getdata")
    }
    return aboutList
}
