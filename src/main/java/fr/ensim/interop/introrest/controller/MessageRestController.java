package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.openWeather.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageRestController {
	
	@Value("${telegram.api.url}")
	private String telegramApiUrl;
	
	//Opérations sur la ressource Message
}

class OpenWeatherCall {
	@Value("${open.weather.api.url}")
	private static String openWeatherUrl;
	@Value("${open.weather.api.token}")
	private static String openWeatherToken;

	/**
	 * Recherche de la météo sur une ville
	 * @param cityName : nom de la ville
	 * @return Objet OpenWeather qui contient la meteo
	 * @throws Exception
	 */
	public static OpenWeather getWeather (String cityName, TimeOfWeek moment) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<City[]> responseEntity = restTemplate.getForEntity(openWeatherUrl + "geo/1.0/direct?q={cityName}&limit=3"
						+ "&appid=" + openWeatherToken,
				City[].class, cityName);

		City[] cities = responseEntity.getBody();
		if (cities.length == 0) throw new Exception("Aucune ville trouvé");
		City city = cities[0];

		OpenWeather openWeather;

		if(moment == TimeOfWeek.TODAY)
			openWeather = restTemplate.getForObject(openWeatherUrl + "data/2.5/weather?lat="+city.getLat()
							+ "&lon="+city.getLat()+"&appid=" + openWeatherToken,
					OpenWeatherCurrent.class);
		else if (moment == TimeOfWeek.WEEK)
			openWeather = restTemplate.getForObject(openWeatherUrl + "data/2.5/onecall?lat=" + city.getLat()
							+ "&lon=" + city.getLat() + "&&exclude=current,minutely,hourly&appid=" + openWeatherToken,
					OpenWeatherForcast.class);
		else throw new Exception("TimeOfWeek non reconu");

		return openWeather;
	}
}
