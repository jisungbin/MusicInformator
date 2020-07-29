package com.sungbin.musicinformator.ui.fragment

import android.widget.TextView
import androidx.fragment.app.Fragment


/**
 * Created by SungBin on 2020-07-30.
 */

abstract class BaseFragment : Fragment() {

    operator fun TextView.plusAssign(text:String){
        this.text = text
    }

    fun TextView.clear() {
        this.text = ""
    }
}