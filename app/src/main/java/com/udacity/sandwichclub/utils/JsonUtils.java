package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();
    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwich_details = new JSONObject(json);
            JSONObject name = sandwich_details.optJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME);
            JSONArray alsoKnownAs = name.optJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.optString(i));
            }
            String placeOfOrigin = sandwich_details.optString(PLACE_OF_ORIGIN);
            String description = sandwich_details.optString(DESCRIPTION);
            String image = sandwich_details.optString(IMAGE);
            JSONArray ingredients = sandwich_details.optJSONArray(INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.optString(i));
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}
