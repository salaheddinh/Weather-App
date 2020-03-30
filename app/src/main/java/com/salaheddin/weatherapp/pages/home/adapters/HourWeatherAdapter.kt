package com.salaheddin.weatherapp.pages.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.salaheddin.weatherapp.R
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.utils.Utils
import java.util.*

class HourWeatherAdapter(private val mContext: Context, private val data: ArrayList<WeatherData>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.context)
        }
        val view = mInflater!!.inflate(R.layout.item_hour_weather, parent, false)
        return HourWeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is HourWeatherViewHolder) {
            holder.setData(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal inner class HourWeatherViewHolder(
        itemView: View
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private val hourTextView = itemView.findViewById<TextView>(R.id.tvHour)
        private val weatherDescriptionTextView =
            itemView.findViewById<TextView>(R.id.tvWeatherDescription)
        private val weatherDegreeTextView = itemView.findViewById<TextView>(R.id.tvWeatherDegree)
        private val minMaxDegreesTextView = itemView.findViewById<TextView>(R.id.tvMinMaxDegrees)
        private val humidityLevelTextView = itemView.findViewById<TextView>(R.id.tvHumidityLevel)
        private val windLevelTextView = itemView.findViewById<TextView>(R.id.tvWindLevel)
        private val weatherIconImageView = itemView.findViewById<ImageView>(R.id.ivWeatherIcon)

        fun setData(weatherData: WeatherData) {
            Glide.with(mContext).load(weatherData.weather[0].iconUrl).into(weatherIconImageView)
            hourTextView.text = Utils.dateLongToHour(weatherData.time * 1000)
            weatherDescriptionTextView.text = weatherData.weather[0].description
            weatherDegreeTextView.text = weatherData.main.temp.toInt().toString() + " \u2103"
            minMaxDegreesTextView.text =
                weatherData.main.temp_min.toInt().toString() + "/" + weatherData.main.temp_max.toInt().toString() + " \u2103"
            humidityLevelTextView.text = weatherData.main.humidity.toInt().toString() + "%"
            windLevelTextView.text = weatherData.wind.speed.toInt().toString()
        }
    }
}
