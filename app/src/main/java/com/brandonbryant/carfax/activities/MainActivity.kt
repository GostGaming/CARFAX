package com.brandonbryant.carfax.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandonbryant.carfax.R
import com.brandonbryant.carfax.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commitNow()
        }

    }
}