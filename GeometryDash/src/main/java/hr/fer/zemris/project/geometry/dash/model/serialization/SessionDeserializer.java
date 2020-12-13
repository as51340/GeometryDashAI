package hr.fer.zemris.project.geometry.dash.model.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Session;
import hr.fer.zemris.project.geometry.dash.model.settings.Account;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import hr.fer.zemris.project.geometry.dash.model.stats.Stats;

/**
 * Custom {@linkplain Session} deserializer
 * @author Andi Škrgat
 *
 */
public class SessionDeserializer  implements JsonDeserializer<Session>{

	
	@Override
	public Session deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonObject account = jsonObject.getAsJsonObject("account");
		String firstName = account.get("firstName").getAsString();
		String lastName = account.get("lastName").getAsString();
		String username = account.get("username").getAsString();
		String password = account.get("password").getAsString();
		Account accountCls = new Account(firstName, lastName, username, password);
		JsonObject stats = jsonObject.getAsJsonObject("stats");
		long totalJumps = stats.get("totalJumps").getAsLong();
		long totalAttempts = stats.get("totalAttempts").getAsLong();
		short completedLevels = stats.get("completedLevels").getAsShort();
		short likedLevels = stats.get("likedLevels").getAsShort();
		short dislikedLevels = stats.get("dislikedLevels").getAsShort();
		short ratedLevels = stats.get("ratedLevels").getAsShort();
		Stats statsCls = new Stats(totalJumps, totalAttempts, completedLevels, likedLevels, dislikedLevels, ratedLevels);
		//TODO deserialize selector
		CharactersSelector selectorCls = new CharactersSelector();
		Session session = new Session(accountCls, statsCls, selectorCls);
		return session;
	}
}
