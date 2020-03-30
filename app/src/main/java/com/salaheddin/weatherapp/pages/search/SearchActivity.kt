package com.salaheddin.weatherapp.pages.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salaheddin.weatherapp.R
import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.Status
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.pages.search.adapters.CityWeatherAdapter
import com.salaheddin.weatherapp.utils.*
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val keywordsString: String by lazy { intent.getStringExtra("keywords") }

    private val searchViewModel: SearchViewModel by viewModel()
    private val citiesWeather = ArrayList<WeatherData>()
    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    private val cityWeatherObserver = Observer<Response<WeatherData>> {
        when (it.status) {
            Status.SUCCESS -> {
                showData(data, loading, error)
                bindCityWeather(it.data!!)
            }

            Status.ERROR -> {

            }

            Status.LOADING -> {

            }
        }
    }

    private fun bindCityWeather(data: WeatherData) {
        if (citiesWeather.isEmpty()) {
            //set first item data
            setCityData(data)
            citiesWeather.add(data)
            cityWeatherAdapter = CityWeatherAdapter(this, citiesWeather)
            rvCitiesWeather.adapter = cityWeatherAdapter

            rvCitiesWeather.layoutManager =
                CenterZoomLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvCitiesWeather)

            rvCitiesWeather.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val currentPosition = snapHelper.getSnapPosition(rvCitiesWeather)
                        setCityData(citiesWeather[currentPosition])
                    }
                }
            })
        } else {
            citiesWeather.add(data)
            cityWeatherAdapter.notifyItemInserted(citiesWeather.size - 1)
        }
    }

    private fun setCityData(cityWeather: WeatherData) {
        tvCityName.text = cityWeather.name
        tvHumidityLevel.text =
            cityWeather.main.humidity.toInt().toString() + "%"
        tvWindLevel.text = cityWeather.wind.speed.toInt().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
        val keywords = keywordsString.split(",")
        for (keyword in keywords) {
            searchViewModel.getCityWeather(keyword).observe(this, cityWeatherObserver)
        }
    }

    private fun init() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
