package com.example.pawssenger.data

import com.example.pawssenger.R

class BrowsePetData {
    fun getPetData(): List<PetData> {
        return listOf<PetData>(
            PetData(
                image = R.drawable.minty,
                petOwner = R.string.mamun,
                petName = R.string.minty,
                pickUpPoint = R.string.topobon,
                Destination = R.string.kalamiabazar,
                contactInfo = R.string.contact1
            ),

            PetData(
                image = R.drawable.kaju,
                petOwner = R.string.huda,
                petName = R.string.kaju,
                pickUpPoint = R.string.lakecity,
                Destination = R.string.gec,
                contactInfo = R.string.contact2
            ),

            PetData(
                image = R.drawable.pet1,
                petOwner = R.string.piplu,
                petName = R.string.mini,
                pickUpPoint = R.string.topobon,
                Destination = R.string.muradpur,
                contactInfo = R.string.contact3
            ),

            PetData(
                image = R.drawable.pet2,
                petOwner = R.string.nazmul,
                petName = R.string.mili,
                pickUpPoint = R.string.topobon,
                Destination = R.string.oxygen,
                contactInfo = R.string.contact4
            ),

            PetData(
                image = R.drawable.pet3,
                petOwner = R.string.rahul,
                petName = R.string.bahadur,
                pickUpPoint = R.string.oxygen,
                Destination = R.string.topobon,
                contactInfo = R.string.contact5
            ),

            PetData(
                image = R.drawable.pet4,
                petOwner = R.string.saif,
                petName = R.string.kiba,
                pickUpPoint = R.string.subidbazar,
                Destination = R.string.sustgate,
                contactInfo = R.string.contact6
            ),

            PetData(
                image = R.drawable.pet5,
                petOwner = R.string.seiam,
                petName = R.string.akamaru,
                pickUpPoint = R.string.sustgate,
                Destination = R.string.amberkhana,
                contactInfo = R.string.contact7
            ),

            PetData(
                image = R.drawable.pet6,
                petOwner = R.string.imran,
                petName = R.string.shinji,
                pickUpPoint = R.string.sustgate,
                Destination = R.string.chawkbazar,
                contactInfo = R.string.contact8
            ),

            PetData(
                image = R.drawable.pet7,
                petOwner = R.string.mamun,
                petName = R.string.tiger,
                pickUpPoint = R.string.muradpur,
                Destination = R.string.amberkhana,
                contactInfo = R.string.contact1
            ),

            PetData(
                image = R.drawable.pet8,
                petOwner = R.string.rahul,
                petName = R.string.mili,
                pickUpPoint = R.string.oxygen,
                Destination = R.string.lakecity,
                contactInfo = R.string.contact4
            )
        )
    }
}