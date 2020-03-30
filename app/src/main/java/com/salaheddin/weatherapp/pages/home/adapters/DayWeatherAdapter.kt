package com.salaheddin.weatherapp.pages.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salaheddin.weatherapp.R
import com.salaheddin.weatherapp.models.DailyWeather
import kotlin.collections.ArrayList

class DayWeatherAdapter(
    private val mContext: Context,
    private val data: ArrayList<DailyWeather>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.context)
        }
        val view = mInflater!!.inflate(R.layout.item_day_weather, parent, false)
        return DayWeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DayWeatherViewHolder) {
            holder.setData(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal inner class DayWeatherViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val dayWeatherRecyclerView =
            itemView.findViewById<RecyclerView>(R.id.rvHourlyWeather)

        init {
            dayWeatherRecyclerView.layoutManager = LinearLayoutManager(mContext)
        }

        fun setData(weather: DailyWeather) {
            val adapter = HourWeatherAdapter(
                mContext,
                weather.weatherList
            )
            dayWeatherRecyclerView.adapter = adapter
        }
    }
}
