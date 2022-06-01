package fr.ensim.interop.introrest.client;

import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public static ApiResponseUpdateTelegram getUpdates() {
        System.out.println(telegramApiUrl + " " + telegramApiToken);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(telegramApiUrl + telegramApiToken + "/getUpdates", ApiResponseUpdateTelegram.class);
    }
}
