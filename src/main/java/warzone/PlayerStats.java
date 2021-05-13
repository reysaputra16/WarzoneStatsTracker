package warzone;

public class PlayerStats {

    public int wins;
    public int kills;
    public double kd_ratio;
    public int downs;
    public int top_twenty_five;
    public int top_ten;
    public int contracts;
    public String title;
    public int revives;
    public int top_five;
    public long score;
    public long time_played;
    public int games_played;
    public int tokens;
    public double score_per_minute;
    public int cash;
    public int deaths;

    public String toString() {
        String actualTitle;
        if(title.equals("\"br\"")) {
            actualTitle = "Battle Royale";
        } else if(title.equals("\"br_dmz\"")) {
            actualTitle = "Plunder";
        } else {
            actualTitle = "Battle Royale + Plunder";
        }

        return "Wins: " + wins + "\n"
                + "Kills: " + kills + "\n"
                + "Deaths: " + deaths + "\n"
                + "K/D Ratio: " + kd_ratio + "\n"
                + "Downs: " + downs + "\n"
                + "Top 25: " + top_twenty_five + "\n"
                + "Top 10: " + top_ten + "\n"
                + "Top 5: " + top_five + "\n"
                + "Contracts: " + contracts + "\n"
                + "Title: " +  actualTitle + "\n"
                + "Revives: " + revives + "\n"
                + "Score: " + score + "\n"
                + "Time played: " + time_played + "\n"
                + "Games played: " + games_played + "\n"
                + "Tokens: " + tokens + "\n"
                + "Score per Minute: " + score_per_minute + "\n"
                + "Cash: " + cash + "\n";
    }
}
