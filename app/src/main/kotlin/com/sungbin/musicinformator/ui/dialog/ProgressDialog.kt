package com.sungbin.musicinformator.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.sungbin.musicinformator.R

class ProgressDialog constructor(val activity: Activity) {

    private lateinit var alert: AlertDialog

    @SuppressLint("InflateParams")
    fun show() {
        val layout = LayoutInflater.from(activity).inflate(R.layout.layout_loading_dialog, null)
        val dialog = AlertDialog.Builder(activity)
        dialog.setView(layout)

        alert = dialog.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
        alert.show()
    }

    fun close() {
        alert.cancel()
    }
}