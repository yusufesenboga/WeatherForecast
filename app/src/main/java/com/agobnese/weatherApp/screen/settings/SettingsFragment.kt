package com.agobnese.weatherApp.screen.settings

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.agobnese.weatherApp.R
import com.agobnese.weatherApp.utils.Prefs
import com.agobnese.weatherApp.views.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    lateinit var forecastViewModel: ForecastViewModel
    lateinit var currentUnit: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Settings"
        changeCurrentUnitSystemText()

        notification_checkBox.isChecked = Prefs.notificationSetting

        notification_checkBox.setOnClickListener {
            Prefs.notificationSetting = notification_checkBox.isChecked
            notification_checkBox.isChecked = Prefs.notificationSetting
        }

        fahrenheit_unit.setOnClickListener {
            activity?.let {
                Prefs.unitLetter = "I"
                Prefs.degreeInText = "Fahrenheit"
                changeCurrentUnitSystemText()
                makeBold(fahrenheit_unit, celcius_unit)

            }
        }

        celcius_unit.setOnClickListener {
            activity?.let {
                Prefs.unitLetter = "M"
                Prefs.degreeInText = "Celcius"
                changeCurrentUnitSystemText()
                makeBold(celcius_unit, fahrenheit_unit)
            }
        }

        dropdown_menu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Prefs.dayCount = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Prefs.dayCount = 0
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dropdown_menu.setSelection(Prefs.dayCount)
    }

    fun makeBold(textView: TextView, secondTextView: TextView) {
        textView.setTypeface(null, Typeface.BOLD)
        secondTextView.setTypeface(null, Typeface.NORMAL)
    }

    fun changeCurrentUnitSystemText() {
        if (Prefs.unitLetter == "I") {
            currentUnitInSettings.text = "Fahrenheit"
            makeBold(fahrenheit_unit, celcius_unit)

        } else {
            currentUnitInSettings.text = "Celcius"
            makeBold(celcius_unit, fahrenheit_unit)
        }
    }
}