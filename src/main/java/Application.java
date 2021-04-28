import java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * API Documentation taken from RapidAPI
 */

public class Application {

    public static void main(String[] args) throws Exception {

        //Host url
        String host = "https://call-of-duty-modern-warfare.p.rapidapi.com/";
        String mode = "warzone/";

        //Header request
        String x_rapidapi_host = "";    //Host taken from RapidAPI
        String x_rapidapi_key = "";     //KEY taken from RapidAPI

        //Param
        String gamertag = "";   //Gamertag ('#' is represented as '%23')
        String platform = "";     //psn, steam, xbl, battle, uno (ActivisionID), acti (activisionTag)


        //Send request
        HttpResponse <JsonNode> response = Unirest.get(host + mode + gamertag + "/" + platform)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();
        System.out.println(response.getStatus());
        System.out.println(response.getHeaders().get("Content-Type"));

        //Prettifying (information is printed with a better format)
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString =  gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}
