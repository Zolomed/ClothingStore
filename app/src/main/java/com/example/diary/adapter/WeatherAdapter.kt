package com.example.diary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R
import com.example.diary.retrofit.Condition
import com.example.diary.retrofit.Current
import com.example.diary.retrofit.Location
import com.example.diary.retrofit.Weather

class WeatherAdapter(private val data: List<Weather?>?):RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view){
        val weatherName: TextView = view.findViewById(R.id.weather_name)
        val weatherImage: ImageView = view.findViewById(R.id.weather_image)
        val weatherOb: TextView = view.findViewById(R.id.weather_ob)
        val weatherTemp: TextView = view.findViewById(R.id.weather_temp)
        val weatherLocaltime: TextView = view.findViewById(R.id.weather_localtime)
        val weatherVis: TextView = view.findViewById(R.id.weather_vis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = data?.get(position)

        holder.weatherName.text = item?.location?.name
        holder.weatherLocaltime.text = item?.location?.localtime
        holder.weatherOb.text = item?.current?.condition?.text
        holder.weatherTemp.text = item?.current?.temp_c.toString()
        holder.weatherVis.text = item?.current?.vis_km.toString()

    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setListLocation(list: List<Location>){
//        listWeatherLocation = list
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setListCurrent(list: List<Current>){
//        listWeatherCurrent = list
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setListCondition(list: List<Condition>){
//        listWeatherCondition = list
//        notifyDataSetChanged()
//    }

}