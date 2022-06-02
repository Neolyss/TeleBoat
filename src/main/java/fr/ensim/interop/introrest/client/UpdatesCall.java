package fr.ensim.interop.introrest.client;

import fr.ensim.interop.introrest.model.bot.MessageObject;
import fr.ensim.interop.introrest.model.bot.MeteoObject;
import fr.ensim.interop.introrest.model.openWeather.OpenWeather;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class UpdatesCall {

    private static String telegramApiUrl;

    private static String telegramApiToken;

    private UpdatesCall() {}

    @Autowired
    public void GetPropertiesBean(@Value("${telegram.api.url}") String telegramApiUrl, @Value("${telegram.api.token}") String telegramApiToken) {
        UpdatesCall.telegramApiUrl = telegramApiUrl;
        UpdatesCall.telegramApiToken =telegramApiToken;
    }

    public static ApiResponseUpdateTelegram getUpdates(int offset) {
        System.out.println(telegramApiUrl + " " + telegramApiToken);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(telegramApiUrl + telegramApiToken + "/getUpdates?offset={offset}", ApiResponseUpdateTelegram.class, offset);
    }

    public static void sendMessage(String chatId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        MessageObject messageObject = new MessageObject(chatId, message);
        restTemplate.postForEntity("http://localhost:9090/message", messageObject, Message.class);
    }

    public static String getMeteo(MeteoObject meteoObject) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:9090/meteo", meteoObject, String.class);
    }

    public static String getJoke() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9090/joke", String.class);
    }

    public static String getQuestion() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9090/question", String.class);
    }

    public static String getResponse(int response) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9090/question/" + response, String.class);
    }
}
