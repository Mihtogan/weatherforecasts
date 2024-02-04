package com.example.weatherforecasts.yandexWeather

data class AnswerYandex(
    val now: Float,
    val now_dt: String,
    val info: Info,
    val fact: Fact,
    val forecast: Forecast
)

data class Info(
    val lat: Float,
    val lon: Float,
    val url: String
)

data class Fact(
    val temp: Float,
    val feels_like: Float,
    val temp_water: Float,
    val icon: String,
    val condition: String,
    val wind_speed: Float,
    val wind_gust: Float,
    val wind_dir: String,
    val pressure_mm: Float,
    val pressure_pa: Float,
    val humidity: Float,
    val daytime: String,
    val polar: Boolean,
    val season: String,
    val obs_time: Float
)

data class Forecast(
    val date: String,
    val date_ts: Float,
    val week: Float,
    val sunset: String,
    val moon_code: Float,
    val moon_text: String,
    val parts: List<Parts>
)

data class Parts(
    val part_name: String,
    val temp_min: Float,
    val temp_max: Float,
    val temp_avg: Float,
    val feels_like: Float,
    val icon: String,
    val condition: String,
    val daytime: String,
    val polar: Boolean,
    val wind_speed: Float,
    val wind_gust: Float,
    val wind_dir: String,
    val pressure_mm: Float,
    val pressure_pa: Float,
    val humidity: Float,
    val prec_mm: Float,
    val prec_period: Float,
    val prec_prob: Float
)
