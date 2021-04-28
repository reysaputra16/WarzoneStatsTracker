package warzone;

public class Application {

    public static void main(String[] args) throws Exception{
        WarzoneData.printWithPrettyFormat(WarzoneData.obtainWZData());
        //System.out.println(WarzoneData.obtainWZMatchDetails("14446166942826623825"));
    }
}
