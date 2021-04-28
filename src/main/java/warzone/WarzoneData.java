package warzone;

import java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class WarzoneData {

    //Host url
    private static String host = "https://call-of-duty-modern-warfare.p.rapidapi.com/";
    private static String mode = "warzone/";

    //Header request
    private static String x_rapidapi_host = "";    //Host taken from RapidAPI
    private static String x_rapidapi_key = "";     //KEY taken from RapidAPI

    //Param
    private static String gamertag = "rey%2322347";   //Gamertag ('#' is represented as '%23')
    private static String platform = "battle";     //psn, steam, xbl, battle, uno (ActivisionID), acti (activisionTag)

    public static String obtainWZData() throws Exception {

        //Send request
        HttpResponse<JsonNode> response = Unirest.get(host + mode + gamertag + "/" + platform)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        System.out.println(response.getStatus());
        System.out.println(response.getHeaders().get("Content-Type"));

        return response.getBody().toString();

    }

    public static String obtainWZMatchDetails(String matchId) throws Exception {

        //Send request
        HttpResponse<String> response = Unirest.get("https://www.callofduty.com/api/papi-client/crm/cod/v2/title/" +
                "mw/platform/battle/fullMatch/wz/" + matchId + "/it").asString();
        System.out.println(response.getStatus());
        System.out.println(response.getHeaders().get("Content-Type"));

        return response.getBody().toString();
    }

    public static void printWithPrettyFormat(String response) {

        //Prettifying (information is printed with a better format)
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);

    }

    public static void parseWZStats(String response) {

    }



}
