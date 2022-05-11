package com.brandonbryant.carfax.db

import android.provider.BaseColumns

const val DATABASE_NAME = "carfax.db"
const val DATABASE_VERSION = 10

object VehicleEntry : BaseColumns {
    const val TABLE_NAME = "vehicles"
    const val ID = "id"
    const val PHOTO_COL = "photo"
    const val YEAR_COL = "year"
    const val MAKE_COL = "make"
    const val MODEL_COL = "model"
    const val TRIM_COL = "trim"
    const val PRICE_COL = "price"
    const val MILEAGE_COL = "mileage"
    const val DEALER_NUMBER_COL = "dealerNumber"
    const val LOCATION_COL = "location"
    const val INTERIOR_COLOR_COL = "interiorColor"
    const val EXTERIOR_COLOR_COL = "exteriorColor"
    const val DRIVE_TYPE_COL = "driveType"
    const val TRANSMISSION_COL = "transmission"
    const val ENGINE_COL = "engine"
    const val BODY_STYLE_COL = "bodyStyle"
    const val FUEL_TYPE_COL = "fuelType"
    
}