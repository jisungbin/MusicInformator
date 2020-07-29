package com.sungbin.musicinformator.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sungbin.musicinformator.R
import org.jetbrains.anko.textColor

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