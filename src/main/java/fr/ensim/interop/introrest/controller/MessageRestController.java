package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.joke.Joke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

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

class JokeCall {
	@Value("${blagues.api.url}")
	private static String blaguesApiUrl;
	@Value("${blagues.api.token}")
	private static String blaguesApiToken;

	public static Joke getJoke () throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(blaguesApiToken);
		ResponseEntity<Joke> responseJoke = restTemplate.exchange(RequestEntity.get(
				new URI(blaguesApiUrl + "api/random")).headers(headers).build(), Joke.class);
		if (responseJoke.getStatusCode() != HttpStatus.OK) throw new Exception("Code "+responseJoke.getStatusCode().value());
		return responseJoke.getBody();
	}
}
