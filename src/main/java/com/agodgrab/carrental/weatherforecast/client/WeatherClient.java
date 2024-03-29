package com.agodgrab.carrental.weatherforecast.client;

import com.agodgrab.carrental.weatherforecast.config.WeatherConfig;
import com.agodgrab.carrental.weatherforecast.domain.CurrentWeatherDto;
import com.agodgrab.carrental.weatherforecast.domain.WeatherForecastDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class WeatherClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherConfig weatherConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherClient.class);


    public CurrentWeatherDto currentWeather(String cityName, String countryCode) {
        try {
            return restTemplate.getForObject(getUrl(cityName, countryCode, weatherConfig.CURR_ENDPOINT), CurrentWeatherDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new CurrentWeatherDto();
        }
    }

    public WeatherForecastDto weatherForecast(String cityName, String countryCode) {
        return restTemplate.getForObject(getUrl(cityName, countryCode, weatherConfig.FORECAST_ENDPOINT), WeatherForecastDto.class);
    }


    private URI getUrl(String cityName, String countryCode, String endpoint) {

        return UriComponentsBuilder.fromHttpUrl(weatherConfig.getWeatherAppEndpoint() + endpoint)
                .queryParam("q", cityName, countryCode)
                .queryParam("units", "metric")
                .queryParam("lang", "pl")
                .queryParam("APPID", weatherConfig.getWeatherAppKey())
                .build()
                .encode()
                .toUri();
    }
}
