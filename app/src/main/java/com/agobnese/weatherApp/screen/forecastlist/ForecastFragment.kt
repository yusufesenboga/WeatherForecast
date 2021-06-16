package com.agobnese.weatherApp.screen.forecastlist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agobnese.weatherApp.DAILY_KEY
import com.agobnese.weatherApp.R
import com.agobnese.weatherApp.WeatherListAdapter
import com.agobnese.weatherApp.chooseTheIconOfWeather
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.screen.details.DetailsFragmentDirections
import com.agobnese.weatherApp.utils.Prefs
import com.agobnese.weatherApp.views.ForecastViewModel
import com.agobnese.weatherApp.views.ForecastViewModelFactory
import kotlinx.android.synthetic.main.fragment_forecast.*
import java.time.LocalTime

class ForecastFragment : Fragment() {
    lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsButton -> {
                val direction =
                    ForecastFragmentDirections.actionForecastFragmentToSettingsFragment()
                findNavController().navigate(direction)
            }
            R.id.mapLocationButton -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastViewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                //TODO
                if (LocalTime.now().hour > it.forecastList[0].sunsetTs) {
                    todaysWeather.setBackgroundResource(R.drawable.screen_shot_2021_03_03_at_11_15_39_pm)
                } else {
                    todaysWeather.setBackgroundResource(R.drawable.darksky)
                }

                createWeatherList(it)
                assignTodaysWeather(it)
            }
        })
    }

    fun createWeatherList(forecastContainer: ForecastContainer) {
        val adapter = WeatherListAdapter(forecastContainer) { position ->
            val direction = ForecastFragmentDirections.actionForecastFragmentToDetailsFragment(position)
            findNavController().navigate(direction)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    fun assignTodaysWeather(forecastContainer: ForecastContainer) {
        dayNameInToday.text = "Today"
        currentDegreeInToday.text = forecastContainer.forecastList[0].temp.toInt()
            .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
        dayDegreeInDetails.text = forecastContainer.forecastList[0].highTemp.toInt()
            .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
        nightDegreeInToday.text = forecastContainer.forecastList[0].minTemp.toInt()
            .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
        weathericonInToday.setImageResource(chooseTheIconOfWeather(forecastContainer.forecastList[0].weather.code))
        weatherDescriptionInToday.text = forecastContainer.forecastList[0].weather.description
    }
}