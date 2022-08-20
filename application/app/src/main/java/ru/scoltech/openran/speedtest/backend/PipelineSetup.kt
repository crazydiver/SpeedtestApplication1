package ru.scoltech.openran.speedtest.backend

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText
import ru.scoltech.openran.speedtest.R

class PipelineSetup(act: FragmentActivity, vg: ViewGroup, cnt : Context) {
    
    val activity = act
    val parent = vg
    val context = cnt

    val childCountEditor = activity.getSharedPreferences("pipeline_count",
        AppCompatActivity.MODE_PRIVATE)





}