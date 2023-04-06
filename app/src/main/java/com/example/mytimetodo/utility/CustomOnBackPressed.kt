package com.example.mytimetodo.utility

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.mytimetodo.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FragmentActivity.customOnBackPressed(viewLifecycleOwner: LifecycleOwner) {
    this@customOnBackPressed.onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do custom work here
                this@customOnBackPressed.apply {
                    findViewById<FloatingActionButton>(R.id.fab).visibility =
                        View.VISIBLE
                    findViewById<BottomAppBar>(R.id.bottom_app_bar).visibility =
                        View.VISIBLE
                }
                // if you want onBackPressed() to be called as normal afterwards
                if (isEnabled) {
                    isEnabled = false
                    this@customOnBackPressed.onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        )
}