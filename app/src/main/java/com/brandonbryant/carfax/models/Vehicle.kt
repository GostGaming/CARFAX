package com.brandonbryant.carfax.models


data class Vehicle (
    val id: String,
    val photo: String,
    val year: String,
    val make: String,
    val model: String,
    val trim: String,
    val price: String,
    val mileage: String,
    val dealerNumber: String,
    val location: String,
    val interiorColor: String,
    val exteriorColor: String,
    val driveType: String,
    val transmission: String,
    val engine: String,
    val bodyStyle: String,
    val fuelType: String
    ){
    override fun toString(): String {
        return "$year $make $model ${if (trim != "Unspecified") trim else ""}"
    }
}