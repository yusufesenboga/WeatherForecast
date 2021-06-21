package com.agobnese.weatherApp.screen.forecastlist

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agobnese.weatherApp.R
import com.agobnese.weatherApp.WeatherListAdapter
import com.agobnese.weatherApp.chooseTheIconOfWeather
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.model.ForecastContainerResult
import com.agobnese.weatherApp.utils.NotificationUtil
import com.agobnese.weatherApp.utils.PermissionUtil
import com.agobnese.weatherApp.utils.Prefs
import com.agobnese.weatherApp.views.ForecastViewModel
import com.agobnese.weatherApp.views.ForecastViewModelFactory
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : Fragment() {
    lateinit var forecastViewModel: ForecastViewModel

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                //Perform Action
                getForecastDetails()
            } else {
                //Show error screen
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
//        forecastViewModel.getSavedForecastContainer()
        forecastViewModel.fetchForecastContainer()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastViewModel.fetchForecastContainer()

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, {
            it?.let { forecastContainerResult ->

                when (forecastContainerResult) {
                    is ForecastContainerResult.Failure -> {
                        //TODO: Show error dialog (couldn't fetch from internet)
                    }
                    ForecastContainerResult.IsLoading -> {
                        //TODO: show loading animation
                    }
                    is ForecastContainerResult.Success -> {

                        createWeatherList(forecastContainerResult.forecastContainer)
                        assignTodaysWeather(forecastContainerResult.forecastContainer)

                        forecastContainerResult.forecastContainer.forecastList.firstOrNull()
                            ?.let { forecast ->
                                NotificationUtil.fireTodayForecastNotification(
                                    requireContext(),
                                    forecast
                                )
                            }
                    }
                }
            }
        })

        //Ask user permission
        askForLocationPermission()
    }

    private fun getForecastDetails() {
        //TODO: "Get Location Details"
        forecastViewModel.fetchForecastContainer()
    }

    override fun onResume() {
        super.onResume()
        forecastViewModel.getSavedForecastContainer()
    }

    fun askForLocationPermission() {
        when {
            PermissionUtil.isLocationPermissionGranted(requireContext()) -> getForecastDetails()
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                //TODO: show educational dialog to user
                // 2 options
                // no thanks - error snackbar
                // yes - call this method again

                //Temporary
                askForLocationPermission()
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
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