package com.brandonbryant.carfax.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.brandonbryant.carfax.models.Vehicle

class VehicleDbTable (context: Context) {
    private val dbHelper = CarfaxDb(context)
    private val vehicles = mutableListOf<Vehicle>()
    private fun saveVehicle (vehicle: Vehicle): Long {
        val values = ContentValues()
        with (values) {
            put (VehicleEntry.ID, vehicle.id)
            put (VehicleEntry.PHOTO_COL, vehicle.photo)
            put (VehicleEntry.YEAR_COL, vehicle.year)
            put (VehicleEntry.MAKE_COL, vehicle.make)
            put (VehicleEntry.MODEL_COL, vehicle.model)
            put (VehicleEntry.TRIM_COL, vehicle.trim)
            put (VehicleEntry.PRICE_COL, vehicle.price)
            put (VehicleEntry.MILEAGE_COL, vehicle.mileage)
            put (VehicleEntry.DEALER_NUMBER_COL, vehicle.dealerNumber)
            put (VehicleEntry.LOCATION_COL, vehicle.location)
            put (VehicleEntry.INTERIOR_COLOR_COL, vehicle.interiorColor)
            put (VehicleEntry.EXTERIOR_COLOR_COL, vehicle.exteriorColor)
            put (VehicleEntry.DRIVE_TYPE_COL, vehicle.driveType)
            put (VehicleEntry.TRANSMISSION_COL, vehicle.transmission)
            put (VehicleEntry.ENGINE_COL, vehicle.engine)
            put (VehicleEntry.BODY_STYLE_COL, vehicle.bodyStyle)
            put (VehicleEntry.FUEL_TYPE_COL, vehicle.fuelType)
        }
        val db = dbHelper.readableDatabase
        val id = db.transaction {
            insert(VehicleEntry.TABLE_NAME, null, values)
        }
        return id
    }
    fun saveVehicles(vehicles: List<Vehicle>) {
        for (vehicle in vehicles) {
            val stat = saveVehicle(vehicle)
            Log.d("VehicleDbTable", "Vehicle $vehicle save status: $stat")
        }
    }

    fun retrieveVehiclesList(): List<Vehicle> {
        if (vehicles.isNotEmpty()) return vehicles
        var cursor: Cursor
        with (VehicleEntry) {
            val columns = arrayOf(
                ID,
                PHOTO_COL,
                YEAR_COL,
                MAKE_COL,
                MODEL_COL,
                TRIM_COL,
                PRICE_COL,
                MILEAGE_COL,
                DEALER_NUMBER_COL,
                LOCATION_COL,
                INTERIOR_COLOR_COL,
                EXTERIOR_COLOR_COL,
                DRIVE_TYPE_COL,
                TRANSMISSION_COL,
                ENGINE_COL,
                BODY_STYLE_COL,
                FUEL_TYPE_COL
            )
            val order = "$ID ASC"
            val db = dbHelper.readableDatabase
            cursor = db.doQuery(TABLE_NAME, columns = columns, orderBy = order)
        }
        return parseVehiclesFromCursor(cursor)
    }

    fun getSingleVehicle(vehicleId: String): Vehicle {
        if (vehicles.isEmpty()) return emptyVehicle()
        return vehicles.first { vehicle -> vehicle.id == vehicleId }
    }

    private fun emptyVehicle():Vehicle = Vehicle (
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
            )

    private fun parseVehiclesFromCursor(cursor: Cursor): List<Vehicle> {
        vehicles.clear()
        while (cursor.moveToNext ()) {
            with (VehicleEntry) {
                val id = cursor.getString(ID)
                val photo = cursor.getString(PHOTO_COL)
                val year = cursor.getString(YEAR_COL)
                val make = cursor.getString(MAKE_COL)
                val model = cursor.getString(MODEL_COL)
                val trim = cursor.getString(TRIM_COL)
                val price = cursor.getString(PRICE_COL)
                val mileage = cursor.getString(MILEAGE_COL)
                val dealerNumber = cursor.getString(DEALER_NUMBER_COL)
                val location = cursor.getString(LOCATION_COL)
                val interiorColor = cursor.getString(INTERIOR_COLOR_COL)
                val exteriorColor = cursor.getString(EXTERIOR_COLOR_COL)
                val driveType = cursor.getString(DRIVE_TYPE_COL)
                val transmission = cursor.getString(TRANSMISSION_COL)
                val engine = cursor.getString(ENGINE_COL)
                val bodyStyle = cursor.getString(BODY_STYLE_COL)
                val fuelType = cursor.getString(FUEL_TYPE_COL)

                vehicles.add(Vehicle(id, photo, year, make, model, trim, price,
                    mileage, dealerNumber, location, interiorColor, exteriorColor,
                    driveType, transmission, engine, bodyStyle, fuelType))
            }
        }
        cursor.close()
        return vehicles
    }
}