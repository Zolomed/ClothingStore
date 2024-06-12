package com.example.clothing_store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.clothing_store.R
import com.example.clothing_store.model.weather.Weather

class WeatherAdapter(private val data: List<Weather?>?) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherName: TextView = view.findViewById(R.id.weatherName)
        val weatherImage: ImageView = view.findViewById(R.id.weatherImage)
        val weatherOb: TextView = view.findViewById(R.id.weatherOb)
        val weatherTemp: TextView = view.findViewById(R.id.weatherTemp)
        val weatherLocaltime: TextView = view.findViewById(R.id.weatherLocaltime)
        val weatherVis: TextView = view.findViewById(R.id.weatherVis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = data?.get(position)
        holder.weatherName.text = weather?.location?.name
        holder.weatherLocaltime.text = "Время: ${weather?.location?.localtime}"
        holder.weatherOb.text = "Описание: ${weather?.current?.condition?.text}"
        holder.weatherTemp.text = "Темперетара: ${weather?.current?.temp_c.toString()}С°"
        holder.weatherVis.text = "Скорость ветра: ${weather?.current?.vis_km.toString()}km/h"

        weather?.current?.condition?.icon.let {
            holder.weatherImage.load(it)
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}