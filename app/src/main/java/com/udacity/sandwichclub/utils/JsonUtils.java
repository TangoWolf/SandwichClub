package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAINNAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    private static final String MESSAGE_CODE = "cod";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sammich = new JSONObject(json);

        if (sammich.has(MESSAGE_CODE)) {
            int errorCode = sammich.getInt(MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONObject name = sammich.getJSONObject(NAME);
        String mainName = name.getString(MAINNAME);
        JSONArray aka = name.getJSONArray(AKA);
        List<String> alsoKnownAs = new ArrayList<String>();
        for (int i = 0; i < aka.length(); i++) {
            String entry = aka.getString(i);
            alsoKnownAs.add(entry);
        }

        String origin = sammich.getString(ORIGIN);

        String description = sammich.getString(DESCRIPTION);

        String imageResource = sammich.getString(IMAGE);

        JSONArray stuff = sammich.getJSONArray(INGREDIENTS);
        List<String> ingredients = new ArrayList<String>();
        for (int i = 0; i < stuff.length(); i++) {
            String ingredient = stuff.getString(i);
            ingredients.add(ingredient);
        }
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, origin, description, imageResource, ingredients);

        return sandwich;
    }
}