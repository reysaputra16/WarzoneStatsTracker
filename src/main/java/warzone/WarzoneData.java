package warzone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class WarzoneData {

    //Host url
    private static String host = "https://call-of-duty-modern-warfare.p.rapidapi.com/";
    private static String mode = "warzone/";

    //Header request
    private static String x_rapidapi_host = "call-of-duty-modern-warfare.p.rapidapi.com";    //Host taken from RapidAPI
    private static String x_rapidapi_key = "fd5ac84f5amsh06f452ea4a4b4fdp179dd7jsn59a6f1274d94";     //KEY taken from RapidAPI

    //Param
    private static String gamertag = "rey%2322347";   //Gamertag ('#' is represented as '%23')
    private static String platform = "battle";     //psn, steam, xbl, battle, uno (ActivisionID), acti (activisionTag)

    //BR Stats representation
    PlayerStats br = new PlayerStats();
    PlayerStats br_dmz = new PlayerStats();
    PlayerStats br_all = new PlayerStats();

    public static void startProgram() throws Exception {

        //System.out.println(WarzoneData.obtainWZMatchDetails("14446166942826623825"));
        String WZData = obtainWZData();
        System.out.println(WZData);
        System.out.println("------------------------------------------");
        //WarzoneData.printWithPrettyFormat(WZData);
        parseWZStats(WZData);
    }

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

    public static String[][] parseWZStats(String response) {
        int currentBracket = 0; //Current bracket being processed

        String[] StringWZStats = new String[3];     //All 3 categories separated in 3 Strings
        String[][] splittedStrings = new String[3][];//Stats are splitted in order to be stored;
        /******INFO ABOUT DIFFERENT STATS PER PLAYER
         * splittedStrings[0]: br (Normal BR)
         * splittedStrings[1]: br_dmz (Plunder)
         * splittedStrings[2]: br_all (Normal BR + Plunder)
         */

        int categoryBracket = 0;    //Position that indicates when a bracket enters the stats of a certain category
        int currentCategory = 0;    //Determines which category is being processed (br, br_dmz, br_all)

        //Separate the data into the 3 given categories
        for(int i = 0;i < response.length();i++) {

            if(response.charAt(i) == '{' && currentBracket == 0) {
                currentBracket++;
            } else if(response.charAt(i) == '{' && currentBracket == 1) {
                categoryBracket = i + 1;
                currentBracket++;
            } else if(response.charAt(i) == '}' && currentBracket == 2) {
                StringWZStats[currentCategory] = response.substring(categoryBracket, i - 1);
                currentCategory++;
                currentBracket--;
            } else if(response.charAt(i) == '{' && currentBracket == 1) {
                currentBracket--;
            }
        }

        //Separate the warzone stats into 3 categories
        for(int i = 0;i < splittedStrings.length;i++) {
            splittedStrings[i] = StringWZStats[i].split(",");
        }

        return splittedStrings;
    }
}
