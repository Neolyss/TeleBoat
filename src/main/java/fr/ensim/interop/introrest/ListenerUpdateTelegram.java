package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.client.UpdatesCall;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {

	/*
	* An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
	* The negative offset can be specified to retrieve updates starting from -offset update from the end of the *
	* updates queue. All previous updates will be forgotten.
	* */


	@Override
	public void run(String... args) throws Exception {
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");
		
		// Operation de pooling pour capter les evenements Telegram
		// Get offset
		ApiResponseUpdateTelegram responseUpdateTelegram = UpdatesCall.getUpdates();
		System.out.println(responseUpdateTelegram);

		new Timer().schedule(new TimerTask() {

			private int offset;
			@Override
			public void run() {

				System.out.println(offset);
				offset++;
			}
		}, 0, 1000L);
	}
}
