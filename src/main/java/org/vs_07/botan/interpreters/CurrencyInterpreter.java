package org.vs_07.botan.interpreters;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;

public class CurrencyInterpreter implements Interpreter {


    //todo : refactor all this shit

    public static final String COMMAND = "kurs";
    public static final String URL = "https://query.yahooapis.com/v1/public/yql?q=select+*+from+yahoo.finance.xchange+where+pair+=+\"USDBYR,EURBYR,%20RUBBYR,%20USDRUB\"&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Override
    public String interpret(String message) {
        return null;
    }

    @Override
    public String interpret(String message, String sender) {
        String response = null;

        if (COMMAND.equalsIgnoreCase(message)) {
            response = getCurrencyResponse();
        }

        return response;
    }

    private String getCurrencyResponse() {
        String raw = getRawCurrency();
        return parseRates(raw);
    }

    private String parseRates(String raw) {
        try {


            JSONObject json = (JSONObject) JSONValue.parse(raw);
            JSONObject query = (JSONObject) json.get("query");
            JSONObject results = (JSONObject) query.get("results");
            JSONArray rates = (JSONArray) results.get("rate");
            StringBuilder response = new StringBuilder();
            rates.forEach((Object o) -> {
                JSONObject rate = (JSONObject) o;
                response.append("\n").append(rate.get("Name"))
                        .append(" - ")
                        .append(rate.get("Rate"));
            });
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRawCurrency() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();

        String response = null;
        try {
            response = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
