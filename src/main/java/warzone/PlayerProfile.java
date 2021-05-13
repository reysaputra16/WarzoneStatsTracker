package warzone;

public class PlayerProfile {

    /**
     * allStats[0] = Battle Royale
     * allStats[1] = Plunder
     * allStats[2] = Battle Royale + Plunder
     */
    public PlayerStats[] allStats = new PlayerStats[3];

    public PlayerProfile() {
        for(int i = 0;i < allStats.length;i++) {
            allStats[i] = new PlayerStats();
        }
    }

    public String toString() {
        return "BR" + "\n" + "---------------------"
                + "\n" + allStats[0].toString() + "\n"
                + "Plunder" + "\n" + "---------------------"
                + "\n" + allStats[1].toString() + "\n"
                + "BR + Plunder" + "\n" + "---------------------"
                + "\n" + allStats[2].toString();
    }

}
