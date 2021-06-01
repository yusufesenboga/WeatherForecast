package com.agobnese.weatherApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.utils.Prefs
import kotlinx.android.synthetic.main.single_item_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherListAdapter(
    private val forecastContainer: ForecastContainer,
    val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_view, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.itemView.run {
            val inFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date? = inFormat.parse(forecastContainer.forecastList[position].datetime)
            val outFormat = SimpleDateFormat("EEEE")
            val goal: String = outFormat.format(date)

            if (position == 0)
                dayName.text = "Today"
            else if (position == 1)
                dayName.text = "Tomorrow"
            else
                dayName.text = goal

            weatherDescription.text = forecastContainer.forecastList[position].weather.description
            morningDegree.text =
                forecastContainer.forecastList[position].maxTemp.toInt()
                    .toString() + "°" + Prefs.degreeInText?.substring(0, 1)
            nightDegree.text =
                forecastContainer.forecastList[position].minTemp.toInt()
                    .toString() + "°" + Prefs.degreeInText?.substring(0, 1)

            imageOfWeather.setImageResource(chooseTheIconOfWeather(forecastContainer.forecastList[position].weather.code))

        }
    }

    override fun getItemCount(): Int {
//        return weatherResponse.forecastList.size
        if (Prefs.dayCount == 0) {
            return 7
        } else
            return 14
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }
    }
}

fun chooseTheIconOfWeather(code: Int): Int {
    when (code) {
        200, 201, 202 -> return R.drawable.t01d
        230, 231, 232, 233 -> return R.drawable.t04d
        300, 301, 302 -> R.drawable.d02n
        500, 501 -> return R.drawable.r02n
        502 -> return R.drawable.r03n
        511 -> return R.drawable.f01n
        520 -> return R.drawable.r04n
        521 -> return R.drawable.r05n
        522 -> return R.drawable.r06d
        600 -> return R.drawable.s01d
        601 -> return R.drawable.s02d
        610 -> return R.drawable.s04d
        611 -> return R.drawable.s05d
        612 -> return R.drawable.s05n
        621 -> return R.drawable.s01d
        622 -> return R.drawable.s02d
        623 -> return R.drawable.s06d
        700 -> return R.drawable.a01d
        711 -> return R.drawable.a02n
        721 -> return R.drawable.a03d
        731 -> return R.drawable.a04n
        741 -> return R.drawable.a05d
        751 -> return R.drawable.a06n
        800 -> return R.drawable.c01d
        801, 802 -> return R.drawable.c02d
        803 -> return R.drawable.c03d
        804 -> return R.drawable.c04d
        900 -> return R.drawable.u00d
    }
    return R.drawable.errorloadingimage

}
