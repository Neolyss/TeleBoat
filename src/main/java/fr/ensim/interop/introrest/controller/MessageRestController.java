package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.bot.MeteoObject;
import fr.ensim.interop.introrest.model.joke.JokeDAO;
import fr.ensim.interop.introrest.model.joke.NotesDAO;
import fr.ensim.interop.introrest.model.openWeather.*;
import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.joke.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

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

	@Value("${blagues.api.url}")
	private String blaguesApiUrl;

	@Value("${blagues.api.token}")
	private String blaguesApiToken;

	@Autowired
	private JokeDAO jokeDAO;

	@Autowired
	private NotesDAO notesDAO;

	private static Joke joke = null;

	//Opérations sur la ressource Message
	@PostMapping("/message")
	public Message sendMessage(@RequestBody MessageObject messageObject) throws URISyntaxException {
		System.out.println(messageObject);
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(telegramApiUrl + telegramApiToken + "/sendMessage");
		System.out.println(uri);
		return restTemplate.postForObject(uri, messageObject, Message.class);
	}

	@PostMapping("/meteo")
	public String postMeteo(@RequestBody MeteoObject meteoObject) throws Exception {
		OpenWeather openWeather = OpenWeatherCall.getWeather(
				meteoObject.getCityName(),
				TimeOfWeek.valueOf(meteoObject.getDayOfWeek()),
				openWeatherUrl,
				openWeatherToken
		);
		return openWeather.toString();
	}

	@GetMapping("/joke")
	public String getJoke() throws Exception {
		joke = JokeCall.getJoke(jokeDAO, notesDAO, blaguesApiUrl, blaguesApiToken);
		System.out.println(joke.joke);
		System.out.println(joke.answer);
		return joke.toString();
	}

	@PostMapping("/joke")
	public String postNoteJoke (@RequestParam(name = "note") int noteGET) {
		return JokeCall.addNote(notesDAO, joke, noteGET) ? "Votre note a été ajouté" : "J'ai pas pu ajouté votre note" ;
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

final class JokeCall {

	public static Joke getJoke (JokeDAO jokeDAO, NotesDAO notesDAO, String blaguesApiUrl, String blaguesApiToken) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(blaguesApiToken);
		ResponseEntity<Joke> responseJoke = restTemplate.exchange(RequestEntity.get(
				new URI(blaguesApiUrl + "api/random")).headers(headers).build(), Joke.class);
		if (responseJoke.getStatusCode() != HttpStatus.OK) throw new Exception("Code "+responseJoke.getStatusCode().value());
		Joke aJoke =  responseJoke.getBody();
		notesDAO.save(aJoke.notes);
		jokeDAO.save(aJoke);
		return aJoke;
	}

	public static boolean addNote (NotesDAO notesDAO, Joke joke, int note) {
		boolean sucess = true;
		switch (note) {
			case 10 :
				joke.notes.dix++;
				break;
			case 9 :
				joke.notes.neuf++;
				break;
			case 8 :
				joke.notes.huit++;
				break;
			case 7 :
				joke.notes.sept++;
				break;
			case 6 :
				joke.notes.six++;
				break;
			case 5 :
				joke.notes.cinq++;
				break;
			case 4 :
				joke.notes.quatre++;
				break;
			case 3 :
				joke.notes.trois++;
				break;
			case 2 :
				joke.notes.deux++;
				break;
			case 1 :
				joke.notes.un++;
				break;
			case 0 :
				joke.notes.zero++;
				break;
			default:
				sucess = false;
				break;
		}
		notesDAO.save(joke.notes);
		return sucess;
	}
}
