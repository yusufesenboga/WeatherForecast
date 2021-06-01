package com.agobnese.weatherApp.model

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "forecastContainerTable")
data class ForecastContainer(
	@Expose(deserialize = false, serialize = false)
	@PrimaryKey(autoGenerate = false)
	val id: Int = 0,
	@SerializedName(value = "country_code")
	val countryCode: String,
	@SerializedName(value = "city_name")
	val cityName: String,
	@SerializedName("data")
	val forecastList: List<Forecast>,
	val timezone: String,
	val lon: Double,
	@SerializedName(value = "state_code")
	val stateCode: String,
	val lat: Double
) : Parcelable

@Parcelize
data class Forecast(

	@field:SerializedName("pres")
	val pres: Double,

	@field:SerializedName("moon_phase")
	val moonPhase: Double,

	@field:SerializedName("wind_cdir")
	val windCdir: String,

	@field:SerializedName("moonrise_ts")
	val moonriseTs: Int,

	@field:SerializedName("clouds")
	val clouds: Int,

	@field:SerializedName("low_temp")
	val lowTemp: Double,

	@field:SerializedName("wind_spd")
	val windSpd: Double,

	@field:SerializedName("ozone")
	val ozone: Double,

	@field:SerializedName("pop")
	val pop: Double,

	@field:SerializedName("valid_date")
	val validDate: String,

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("precip")
	val precip: Float,

	@field:SerializedName("sunrise_ts")
	val sunriseTs: Int,

	@field:SerializedName("min_temp")
	val minTemp: Double,

	@field:SerializedName("weather")
	val weather: Weather,

	@field:SerializedName("app_max_temp")
	val appMaxTemp: Double,

	@field:SerializedName("max_temp")
	val maxTemp: Double,

	@field:SerializedName("snow_depth")
	val snowDepth: Double,

	@field:SerializedName("sunset_ts")
	val sunsetTs: Int,

	@field:SerializedName("max_dhi")
	val maxDhi: String,

	@field:SerializedName("clouds_mid")
	val cloudsMid: Int,

	@field:SerializedName("vis")
	val vis: Double,

	@field:SerializedName("uv")
	val uv: Double,

	@field:SerializedName("high_temp")
	val highTemp: Double,

	@field:SerializedName("temp")
	val temp: Double,

	@field:SerializedName("clouds_hi")
	val cloudsHi: Int,

	@field:SerializedName("app_min_temp")
	val appMinTemp: Double,

	@field:SerializedName("moon_phase_lunation")
	val moonPhaseLunation: Double,

	@field:SerializedName("dewpt")
	val dewpt: Double,

	@field:SerializedName("wind_dir")
	val windDir: Int,

	@field:SerializedName("wind_gust_spd")
	val windGustSpd: Double,

	@field:SerializedName("clouds_low")
	val cloudsLow: Int,

	@field:SerializedName("rh")
	val rh: Int,

	@field:SerializedName("slp")
	val slp: Double,

	@field:SerializedName("snow")
	val snow: Double,

	@field:SerializedName("wind_cdir_full")
	val windCdirFull: String,

	@field:SerializedName("moonset_ts")
	val moonsetTs: Int,

	@field:SerializedName("ts")
	val ts: Int

) : Parcelable

@Parcelize
data class Weather(
	val code: Int,
	val icon: String,
	val description: String
) : Parcelable
