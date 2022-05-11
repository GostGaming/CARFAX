package com.brandonbryant.carfax.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.brandonbryant.carfax.R
import com.brandonbryant.carfax.fragments.DetailsFragment

class DetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DetailsActivity", "Creating Details activity..")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.details_container, DetailsFragment.newInstance())
                .commitNow()
        }
    }
}