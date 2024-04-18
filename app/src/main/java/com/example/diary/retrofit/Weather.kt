package com.example.diary.retrofit

class Weather(val current: Current) {
    data class Current(
        val last_updated_epoch: Int,
        val last_updated: String,
        val temp_c: Float,
        val temp_f: Float,
        val is_day: Boolean
    )
}