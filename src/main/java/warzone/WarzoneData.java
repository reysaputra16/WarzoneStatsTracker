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
    private static PlayerProfile playerProfile = new PlayerProfile();

    public static void startProgram() throws Exception {
        //System.out.println(WarzoneData.obtainWZMatchDetails("14446166942826623825"));
        String WZData = obtainWZData();
        String[][] parsedWZData = parseWZStats(WZData);
        //System.out.println(WZData);
        //System.out.println("------------------------------------------");
        //WarzoneData.printWithPrettyFormat(WZData);
        dataToPlayerStats(parsedWZData);
        System.out.println("\n" + playerProfile.toString());

        /*
        for(int i = 0;i < parsedWZData.length;i++) {
            for(int j = 0;j < parsedWZData[i].length;j++) {
                System.out.println(parsedWZData[i][j]);
            }
            System.out.println("--------------");
        }
         */

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
                StringWZStats[currentCategory] = response.substring(categoryBracket, i);
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

    public static void dataToPlayerStats(String[][] data) {
        String[] currentStat;
        for(int i = 0;i < data.length;i++) {
            for(int j = 0;j < data[i].length;j++) {
                currentStat = data[i][j].split(":");
                addPlayerStats(i, currentStat);
            }
        }
    }

    public static void addPlayerStats(int statNumber, String[] currentStat) {

        if(currentStat[0].equals("\"wins\"")) {
            playerProfile.allStats[statNumber].wins = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"kills\"")) {
            playerProfile.allStats[statNumber].kills = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"kdRatio\"")) {
            playerProfile.allStats[statNumber].kd_ratio = Double.parseDouble(currentStat[1]);
        } else if(currentStat[0].equals("\"downs\"")) {
            playerProfile.allStats[statNumber].downs = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"topTwentyFive\"")) {
            playerProfile.allStats[statNumber].top_twenty_five = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"topTen\"")) {
            playerProfile.allStats[statNumber].top_ten = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"contracts\"")) {
            playerProfile.allStats[statNumber].contracts = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"title\"")) {
            playerProfile.allStats[statNumber].title = currentStat[1];
        } else if(currentStat[0].equals("\"revives\"")) {
            playerProfile.allStats[statNumber].revives = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"topFive\"")) {
            playerProfile.allStats[statNumber].top_five = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"score\"")) {
            playerProfile.allStats[statNumber].score = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"timePlayed\"")) {
            playerProfile.allStats[statNumber].time_played = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"gamesPlayed\"")) {
            playerProfile.allStats[statNumber].games_played = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"tokens\"")) {
            playerProfile.allStats[statNumber].tokens = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"scorePerMinute\"")) {
            playerProfile.allStats[statNumber].score_per_minute = Double.parseDouble(currentStat[1]);
        } else if(currentStat[0].equals("\"cash\"")) {
            playerProfile.allStats[statNumber].cash = Integer.parseInt(currentStat[1]);
        } else if(currentStat[0].equals("\"deaths\"")) {
            playerProfile.allStats[statNumber].deaths = Integer.parseInt(currentStat[1]);
        }
    }
}
