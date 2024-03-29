package com.agodgrab.carrental.weatherforecast.controller;

import com.agodgrab.carrental.weatherforecast.domain.CurrentWeatherDto;
import com.agodgrab.carrental.weatherforecast.domain.WeatherForecastDto;
import com.agodgrab.carrental.weatherforecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/current")
    public CurrentWeatherDto getWeather(@RequestParam(value = "city name") String cityName, @RequestParam(value = "country code") String countryCode) {
        return weatherService.getCurrentWeather(cityName, countryCode);
    }

    @GetMapping(value = "/forecast")
    public WeatherForecastDto getForecast(@RequestParam(value = "city name") String cityName, @RequestParam(value = "country code") String countryCode) {
        return weatherService.getWeatherForecast(cityName, countryCode);
    }

}
