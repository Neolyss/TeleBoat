package fr.ensim.interop.introrest.model.bot;

import fr.ensim.interop.introrest.client.UpdatesCall;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;

public class UpdateTask extends TimerTask {
    private static int offset = 0;

    @Override
    public void run() {
        ApiResponseUpdateTelegram responseUpdateTelegram = UpdatesCall.getUpdates(offset);
        List<Update> updateList = responseUpdateTelegram.getResult();
        if(!updateList.isEmpty()) {
            for(Update update : updateList) {
                int updateId = update.getUpdateId();
                if(offset == 0) {
                    offset = updateId;
                }
                String message = update.getMessage().getText();
                System.out.println("message : " + message);
                if(message.contains("meteo")) {
                    handleWeather(update, message);
                } else if (message.contains("joke")) {
                    UpdatesCall.sendMessage(update.getMessage().getChatId().toString(), UpdatesCall.getJoke());
                } else if (message.contains("question")) {
                    UpdatesCall.sendMessage(update.getMessage().getChatId().toString(),UpdatesCall.getQuestion());
                } else if (message.contains("1") || message.contains("2") || message.contains("3") || message.contains("4") ) {
                    UpdatesCall.sendMessage(update.getMessage().getChatId().toString(),UpdatesCall.getResponse(Integer.parseInt(message)));
                } else {
                    UpdatesCall.sendMessage(update.getMessage().getChatId().toString(), "This command is not existing");
                }
                offset++;
            }
        }
        System.out.println(offset);
    }

    private void handleWeather(Update update, String message) {
        String messageProcessed = "";
        if(message.equals("meteo")) { // if there is only meteo
            messageProcessed = message.replace("meteo", "");
        } else {
            messageProcessed = message.replace("meteo ", "");
        }
        System.out.println("messageProcessed=" + messageProcessed);
        String[] messageSplit = messageProcessed.split(" ");
        //Arrays.stream(messageSplit).forEach(System.out::println);
        String city = "";
        String param = "";
        System.out.println("Lenght = " + messageSplit.length);
        if(messageSplit.length == 1) {
            city = messageSplit[0];
            param = "today";
        } else if(messageSplit.length >= 2) {
            for(int i=0; i<=messageSplit.length-2; i++) {
                System.out.println("i=" +  messageSplit[i]);
                city = city.concat(messageSplit[i] + " ");
            }
            String maybeParam = messageSplit[messageSplit.length-1];
            System.out.println("mb=" + maybeParam);
            if(!maybeParam.equalsIgnoreCase("today") && !maybeParam.equalsIgnoreCase("week")) {
                param = "";
                city = city.concat(maybeParam + " ");
            } else {
                param = maybeParam;
            }
        }
        if(param.equals("")) {
            param = "TODAY";
        } else {
            param = param.toUpperCase(Locale.ROOT);
        }
        if(city.equals("")) {
            UpdatesCall.sendMessage(update.getMessage().getChatId().toString(), "Not enough params");
        } else {
            MeteoObject meteoObject = new MeteoObject(city, param);
            System.out.println(meteoObject);
            String meteo = UpdatesCall.getMeteo(meteoObject);
            UpdatesCall.sendMessage(update.getMessage().getChatId().toString(), meteo);
        }
    }

}
