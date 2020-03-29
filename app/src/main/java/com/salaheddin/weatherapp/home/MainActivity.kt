package com.salaheddin.weatherapp.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salaheddin.weatherapp.*
import com.salaheddin.weatherapp.home.adapters.DayWeatherAdapter
import com.salaheddin.weatherapp.home.utils.ValidationUtils
import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.Status
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.search.SearchActivity
import com.salaheddin.weatherapp.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val LOCATION_REQUEST_CODE = 100
    private val TAG = javaClass.name
    private var isSearchOpened = false

    private val currentCityViewModel: CurrentCityViewModel by viewModel()

    private val forecastObserver = Observer<Response<ArrayList<WeatherData>>> {
        when (it.status) {
            Status.SUCCESS -> {
                showData(data, loading, error)
                bindForecast(it.data!!)
            }

            Status.ERROR -> {
                showError(data, loading, error)
            }

            Status.LOADING -> {
                showLoading(data, loading, error)
            }
        }
    }

    private val currentObserver = Observer<Response<WeatherData>> {
        when (it.status) {
            Status.SUCCESS -> {
                showData(data, loading, error)
                bindCurrentWaether(it.data!!)
            }

            Status.ERROR -> {
                showError(data, loading, error)
            }

            Status.LOADING -> {
                showLoading(data, loading, error)
            }
        }
    }

    private val mLocationListener = object : LocationListener {
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }

        override fun onLocationChanged(location: Location?) {
            val lon = location?.longitude!!.toInt()
            val lan = location?.latitude!!.toInt()
            currentCityViewModel.getWeather(lan, lon).observe(this@MainActivity, currentObserver)
            currentCityViewModel.getWeather(lan, lon).observe(this@MainActivity, currentObserver)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        requestLocation()
        setListeners()

        if (Utils.checkIfAlreadyHavePermission(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            )
        ) {
            getLastLocationData()
        } else {
            Utils.requestForSpecificPermission(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocationData() {
        val mLocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val lon = location.longitude.toInt()
            val lan = location.latitude.toInt()
            currentCityViewModel.getForecast5(lan, lon).observe(this, forecastObserver)
            currentCityViewModel.getWeather(lan, lon).observe(this@MainActivity, currentObserver)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        try {
            val mLocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val provider: String?

            when {
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    provider = LocationManager.GPS_PROVIDER
                    mLocationManager.requestLocationUpdates(
                        provider,
                        1000000,
                        100f,
                        mLocationListener
                    )
                }
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                    provider = LocationManager.NETWORK_PROVIDER
                    mLocationManager.requestLocationUpdates(
                        provider,
                        1000000,
                        100f,
                        mLocationListener
                    )
                }
                else -> {

                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
    }

    private fun init() {
        setSupportActionBar(toolBar)
    }

    private fun setListeners() {
        ivBack.setOnClickListener {
            closeSearch()
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val validationMessage =
                    ValidationUtils.validateSearchKeywords(etSearch.text.toString())
                if (validationMessage.isEmpty()) {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    intent.putExtra("keywords", etSearch.text.toString())
                    startActivity(intent)
                    closeSearch()
                } else {
                    Utils.showToast(this@MainActivity, validationMessage)
                }
                true
            } else {
                false
            }
        }
    }

    private fun bindCurrentWaether(data: WeatherData) {
        supportActionBar?.title = data.name
        tvTodayDate.text = Utils.dateLongToShowDate(data.time * 1000)
        Glide.with(this).load(data.weather[0].iconUrl).into(ivWeatherIcon)
        tvWeatherDegree.text = Utils.dateLongToHour(data.time * 1000)
        tvWeatherDegree.text = data.main.temp.toInt().toString() + " \u2103"
        tvMinMaxDegrees.text =
            data.main.temp_min.toInt().toString() + "/" + data.main.temp_max.toInt().toString() + " \u2103"
        tvHumidityLevel.text = data.main.humidity.toInt().toString() + "%"
        tvWindLevel.text = data.wind.speed.toInt().toString()
        tvWeatherDescription.text = data.weather[0].description
    }

    private fun bindForecast(data: ArrayList<WeatherData>) {
        val groupedData = currentCityViewModel.getDailyDataGrouped(data)

        //set first day date
        tvCurrentDate.text =
            Utils.dateLongToShowDate(groupedData[0].weatherList[0].time * 1000)

        rvDailyWeather.layoutManager =
            CenterZoomLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDailyWeather)

        val adapter =
            DayWeatherAdapter(
                this,
                ArrayList(groupedData)
            )
        rvDailyWeather.adapter = adapter

        rvDailyWeather.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val currentPosition = snapHelper.getSnapPosition(rvDailyWeather)
                    tvCurrentDate.text =
                        Utils.dateLongToShowDate(groupedData[currentPosition].weatherList[0].time * 1000)
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocationData()
                } else {
                    Utils.showToast(
                        this@MainActivity,
                        "permission is needed to show current location weather"
                    )
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_search) {
            openSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (isSearchOpened) {
            closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeSearch(){
        vBlackBackground.visibility = View.GONE
        vSearch.visibility = View.GONE
        isSearchOpened = false
        etSearch.setText("")
        hideKeyboard(etSearch)
    }

    private fun openSearch(){
        vBlackBackground.visibility = View.VISIBLE
        vSearch.visibility = View.VISIBLE
        isSearchOpened = true
        showKeyboard(etSearch)
    }
}
