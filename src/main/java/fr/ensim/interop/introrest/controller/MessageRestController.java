package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.bot.MeteoObject;
import fr.ensim.interop.introrest.model.openWeather.*;
import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
public class MessageRestController {

	@Value("${open.weather.api.url}")
	private String openWeatherUrl;
	@Value("${open.weather.api.token}")
	private String openWeatherToken;
	
	@Value("${telegram.api.url}")
	private String telegramApiUrl;

	@Value("${telegram.api.token}")
	private String telegramApiToken;
	
	//Opérations sur la ressource Message
	@PostMapping("/message")
	public ResponseEntity<ApiResponseTelegram<Message>> sendMessage(@RequestBody MessageObject messageObject) throws URISyntaxException {
		System.out.println(messageObject);
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(telegramApiUrl + telegramApiToken + "/sendMessage");
		System.out.println(uri);
		ResponseEntity<ApiResponseTelegram> responseTelegram = restTemplate.postForEntity(uri, messageObject, ApiResponseTelegram.class);

		return ResponseEntity.ok().body(responseTelegram.getBody());
	}

	@PostMapping("/meteo")
	public OpenWeather postMeteo(@RequestBody MeteoObject meteoObject) throws Exception {
		return OpenWeatherCall.getWeather(
				meteoObject.getCityName(),
				TimeOfWeek.valueOf(meteoObject.getDayOfWeek()),
				openWeatherUrl,
				openWeatherToken
		);
	}

}

final class OpenWeatherCall {

	/**
	 * Recherche de la météo sur une ville
	 * @param cityName : nom de la ville
	 * @return Objet OpenWeather qui contient la meteo
	 * @throws Exception
	 */
	public static OpenWeather getWeather (String cityName, TimeOfWeek moment, String openWeatherUrl, String openWeatherToken) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<City[]> responseEntity = restTemplate.getForEntity(
				openWeatherUrl + "geo/1.0/direct?q={cityName}&limit=3" + "&appid=" + openWeatherToken,
				City[].class,
				cityName
		);

		City[] cities = responseEntity.getBody();
		for(City city : cities) {
			System.out.println(city.getName() + " " + city.getLat() + " " + city.getLon());
		}
		if (cities.length == 0) throw new Exception("Aucune ville trouvé");
		City city = cities[0];

		OpenWeather openWeather;

		if(moment == TimeOfWeek.TODAY) {
			String urlWeather = openWeatherUrl + "data/2.5/weather?lat="+city.getLat()
					+ "&lon="+city.getLon()+"&appid=" + openWeatherToken;
			System.out.println(urlWeather);
			URI uriWeather = new URI(urlWeather);
			openWeather = restTemplate.getForObject(uriWeather, OpenWeatherCurrent.class);
		}
		else if (moment == TimeOfWeek.WEEK) {
			String urlWeek = openWeatherUrl + "data/2.5/onecall?lat=" + city.getLat()
					+ "&lon=" + city.getLon() + "&&exclude=current,minutely,hourly&appid=" + openWeatherToken;
			URI uriWeek = new URI(urlWeek);
			System.out.println(uriWeek);
			openWeather = restTemplate.getForObject(uriWeek, OpenWeatherForcast.class);
		}
		else throw new Exception("TimeOfWeek non reconu");

		return openWeather;
	}
}