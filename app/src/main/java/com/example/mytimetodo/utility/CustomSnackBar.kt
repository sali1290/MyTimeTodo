package com.example.mytimetodo.utility

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity.showTopSnackBar(view: View, message: String) {
    val snackBar = Snackbar.make(
        this@showTopSnackBar,
        view,
        message,
        Snackbar.LENGTH_SHORT
    )
    (snackBar.view.layoutParams as (FrameLayout.LayoutParams)).gravity =
        Gravity.TOP
    snackBar.setAction("Ok") {
        snackBar.dismiss()
    }
    snackBar.show()
}