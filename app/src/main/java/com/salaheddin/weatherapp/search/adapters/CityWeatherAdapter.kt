package com.salaheddin.weatherapp.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salaheddin.weatherapp.R
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import kotlin.collections.ArrayList

class CityWeatherAdapter(
    private val mContext: Context,
    private val data: ArrayList<WeatherData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.context)
        }
        val view = mInflater!!.inflate(R.layout.item_city_weather, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ServiceViewHolder) {
            holder.setData(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal inner class ServiceViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val weatherDegreeTextView = itemView.findViewById<TextView>(R.id.tvWeatherDegree)
        private val minMaxDegreesTextView = itemView.findViewById<TextView>(R.id.tvMinMaxDegrees)
        private val weatherDescriptionTextView = itemView.findViewById<TextView>(R.id.tvWeatherDescription)
        private val weatherIconImageView = itemView.findViewById<ImageView>(R.id.ivWeatherIcon)

        fun setData(weatherData: WeatherData) {
            Glide.with(mContext).load(weatherData.weather[0].iconUrl).into(weatherIconImageView)
            weatherDegreeTextView.text = weatherData.main.temp.toInt().toString() + " \u2103"
            weatherDescriptionTextView.text = weatherData.weather[0].description
            minMaxDegreesTextView.text =
                weatherData.main.temp_min.toInt().toString() + "/" + weatherData.main.temp_max.toInt().toString() + " \u2103"
        }
    }
}
