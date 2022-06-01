package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.bot.MeteoObject;
import fr.ensim.interop.introrest.model.openWeather.*;
import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.joke.Joke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

class JokeCall {
//	@Value("${blagues.api.url}")
	private static String blaguesApiUrl = "https://www.blagues-api.fr/";
//	@Value("${blagues.api.token}")
	private static String blaguesApiToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMzM3NTMyNDg1NTM5ODU2Mzg1IiwibGltaXQiOjEwMCwia2V5IjoiU1E4V0F5UkdNUjV2WHFoOGxQY2tOeE1MYkNLS3JmMFlydXByS0ZCelNMdUN0cUZuc0wiLCJjcmVhdGVkX2F0IjoiMjAyMi0wNS0zMFQxNTozNzo1MiswMDowMCIsImlhdCI6MTY1MzkyNTA3Mn0.MMlUWx5kgfr0jQGYfg7yRhfKgZMKQDJk5H4qf2FhuWY";

	public static Joke getJoke () throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(blaguesApiToken);
		ResponseEntity<Joke> responseJoke = restTemplate.exchange(RequestEntity.get(
				new URI(blaguesApiUrl + "api/random")).headers(headers).build(), Joke.class);
		if (responseJoke.getStatusCode() != HttpStatus.OK) throw new Exception("Code "+responseJoke.getStatusCode().value());
		Joke aJoke =  responseJoke.getBody();
//		aJoke.save();
		return aJoke;
	}

	public static void main(String[] args) throws Exception {
		Joke joke = JokeCall.getJoke();
		System.out.println(joke.joke);
		System.out.println(joke.answer);
	}
}
