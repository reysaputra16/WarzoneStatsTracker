package warzone;

public class Application {

    public static void main(String[] args) throws Exception{

        PlayerStats br = new PlayerStats();
        PlayerStats br_dmz = new PlayerStats();
        PlayerStats br_all = new PlayerStats();

        //System.out.println(WarzoneData.obtainWZMatchDetails("14446166942826623825"));
        String WZData = WarzoneData.obtainWZData();
        System.out.println(WZData);
        System.out.println("------------------------------------------");
        WarzoneData.printWithPrettyFormat(WZData);
    }
}
