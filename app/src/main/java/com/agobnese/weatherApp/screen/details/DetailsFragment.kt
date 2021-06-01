package com.agobnese.weatherApp.screen.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.agobnese.weatherApp.DAILY_KEY
import com.agobnese.weatherApp.R
import com.agobnese.weatherApp.chooseTheIconOfWeather
import com.agobnese.weatherApp.model.Forecast
import com.agobnese.weatherApp.utils.Prefs
import com.agobnese.weatherApp.views.ForecastViewModel
import com.agobnese.weatherApp.views.ForecastViewModelFactory
import kotlinx.android.synthetic.main.fragment_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private var forecast: Forecast? = null
    private var position = 0
    lateinit var forecastViewModel: ForecastViewModel
    lateinit var factory: ForecastViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            forecast = it.getParcelable(DAILY_KEY)
            factory = ForecastViewModelFactory(requireActivity().application)
            forecastViewModel =
                ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Details"
//        details_fragment_textview.text = forecast.toString()
        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = inFormat.parse(forecast?.datetime)
        val outFormat = SimpleDateFormat("EEEE, MMMM d")
        val goal: String = outFormat.format(date)


        forecast?.let {

            dayNameInDetails.text = goal
            dayDegreeInDetails.text = it.highTemp.toInt()
                .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
            nightDegreeInDetails.text = it.minTemp.toInt()
                .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
            weathericonInDetails.setImageResource(chooseTheIconOfWeather(it.weather.code))
            weatherDescriptionInDetails.text = it.weather.description

            humidityAnswer.text = it.rh.toString() + " %"
            pressureAnswer.text = it.pres.toInt().toString() + " hPa"
            windAnswer.text = it.windSpd.toInt().toString() + " km/h SE"
        }
    }
}