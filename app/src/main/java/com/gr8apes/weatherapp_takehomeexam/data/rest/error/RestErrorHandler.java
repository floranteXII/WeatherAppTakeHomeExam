package com.gr8apes.weatherapp_takehomeexam.data.rest.error;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;

public class RestErrorHandler {

    public static ErrorResponse parseErrorDetails(int httpCode,String exception) {
        JsonObject jsonObject = new JsonParser().parse(exception).getAsJsonObject();

        String message;

        message = getError(jsonObject.toString());


        return new ErrorResponse(httpCode, message);
    }

    public static String getError(String err) {
        String errorDescription = "";
        try {
            JsonElement error = new JsonParser().parse(err);
            Log.i("getError", "getError: " + error);
            if (error.isJsonObject()) {
                JsonObject messageJsonObject = (JsonObject) error;
                Set<Map.Entry<String, JsonElement>> entries = messageJsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entries) {
                    String message = "";
                    if (messageJsonObject.get(entry.getKey()).isJsonPrimitive()) {
                        message = messageJsonObject.get(entry.getKey()).getAsString();
                    } else if (messageJsonObject.get(entry.getKey()).isJsonArray()) {
                        JsonArray jsonArray = messageJsonObject.get(entry.getKey()).getAsJsonArray();
                        if (jsonArray.size() > 0)
                            message += jsonArray.get(0).getAsString();
                    }
                    errorDescription += message + "\n";
                    break;
                }
            } else {
                errorDescription = error.getAsString();
            }
            return errorDescription;
        } catch (Exception ex) {
            return err;
        }
    }
}
