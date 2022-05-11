package com.brandonbryant.carfax.viewmodels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.brandonbryant.carfax.R
import java.text.NumberFormat
import java.util.*

class CarfaxUtils(private val context: Context) {
    fun makePhoneCall(number: String) {
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            } else {
                val dial = "tel:$number"
                ActivityCompat.startActivity(
                    context,
                    Intent(Intent.ACTION_CALL, Uri.parse(dial)),
                    null
                )
            }
        } else {
            Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
        }
    }

    fun formatPriceMileage (price: String, mileage: String):String {
        val priceInDollars: String
        if (Build.VERSION.SDK_INT >= 30) {
            priceInDollars = NumberFormatter.with()
                .notation(Notation.compactShort())
                .precision(Precision.maxSignificantDigits(3))
                .locale(Locale.ENGLISH)
                .format(mileage.toLong())
                .toString()
            Log.d("DetailsFragment","Price formatted to: $priceInDollars")
        } else {
            priceInDollars = NumberFormat.getCurrencyInstance().format(price.toInt())
        }
        var milesFormatted = mileage
        try {
            var doubleMiles = mileage.toDouble()

            if (doubleMiles > 1_000) {
                doubleMiles %= 1_000
                milesFormatted = "${doubleMiles}k"

            }
        } catch(exception: Exception) {}
        return String.format(context.getString(R.string.price_mileage, priceInDollars, milesFormatted))
    }
}