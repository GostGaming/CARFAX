package com.brandonbryant.carfax.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class CarfaxDb(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val sqlCreateVehicleEntries: String = "CREATE TABLE IF NOT EXISTS ${VehicleEntry.TABLE_NAME} (" +
                "${VehicleEntry.ID} TEXT PRIMARY KEY," +
                "${VehicleEntry.PHOTO_COL} TEXT," +
                "${VehicleEntry.YEAR_COL} TEXT," +
                "${VehicleEntry.MAKE_COL} TEXT,"+
                "${VehicleEntry.MODEL_COL} TEXT,"+
                "${VehicleEntry.TRIM_COL} TEXT,"+
                "${VehicleEntry.PRICE_COL} TEXT,"+
                "${VehicleEntry.MILEAGE_COL} TEXT,"+
                "${VehicleEntry.DEALER_NUMBER_COL} TEXT,"+
                "${VehicleEntry.LOCATION_COL} TEXT,"+
                "${VehicleEntry.INTERIOR_COLOR_COL} TEXT,"+
                "${VehicleEntry.EXTERIOR_COLOR_COL} TEXT,"+
                "${VehicleEntry.DRIVE_TYPE_COL} TEXT,"+
                "${VehicleEntry.TRANSMISSION_COL} TEXT,"+
                "${VehicleEntry.ENGINE_COL} TEXT,"+
                "${VehicleEntry.BODY_STYLE_COL} TEXT,"+
                "${VehicleEntry.FUEL_TYPE_COL} TEXT" +
            ")"
    private val sqlDeleteVehicleEntries = "DROP TABLE IF EXISTS ${VehicleEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlCreateVehicleEntries)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(sqlDeleteVehicleEntries)
        onCreate(db)
    }

}

fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                           selectionArgs: Array<String>? = null, groupBy: String? = null,
                           having: String? = null, orderBy: String? = null): Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}


fun Cursor.getString (columnName: String): String = getString (getColumnIndexOrThrow(columnName))

fun Cursor.getBitmap (columnName: String): Bitmap {
    val byteArray = getBlob (this.getColumnIndexOrThrow(columnName))
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
fun Cursor.getInt (columnName: String) = getInt (getColumnIndexOrThrow(columnName))


inline fun <T> SQLiteDatabase.transaction (function: SQLiteDatabase.() -> T): T {
    beginTransaction ()
    val result = try {
        val returnValue = function ()
        setTransactionSuccessful()
        returnValue
    } finally {
        endTransaction()
    }
    close()
    return result
}
fun toByteArray (bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream ()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}