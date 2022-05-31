package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.openWeather.*;
import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class MessageRestController {
	
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
