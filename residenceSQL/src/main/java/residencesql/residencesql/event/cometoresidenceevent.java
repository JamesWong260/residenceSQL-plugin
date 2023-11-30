package residencesql.residencesql.event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import residencesql.residencesql.ResidenceSQL;
import java.util.UUID;
import java.util.HashMap;
import java.sql.*;

public class cometoresidenceevent implements Listener {
    private final HashMap<UUID, Long> cooldown;
    private final HashMap<UUID, Long> leavecooldown;
    private static ResidenceSQL main;
    static final String Residence = "SELECT id, residencename, owner, UUID, members, membersUUID, world, X1, Y1, Z1, X2, Y2, Z2 FROM Residence";
    public cometoresidenceevent(ResidenceSQL residenceSQL, HashMap<UUID, Long> cooldown, HashMap<UUID, Long> leavecooldown) {
        this.cooldown = cooldown;
        this.leavecooldown = leavecooldown;
        this.main = main;
    }
    private ResidenceSQL plugin = ResidenceSQL.getMain();

    private Connection connection;


    public static final int DEFAULT_COOLDOWN = 15;
    int already = 0;

    public cometoresidenceevent() {
        this.cooldown = new HashMap<>();
        this.leavecooldown = new HashMap<>();
    }
    int leave = 0;
    int keep = 0;
    String owner ="";
    String resname ="";

    @EventHandler
    public void OnPlayerMoveEvent(PlayerMoveEvent event) {
        int xx = 1;
        Player player = event.getPlayer();
        String ID = String.valueOf(player);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        Location loc = player.getLocation();
        String sx = String.valueOf(event.getTo().getBlockX());
        String sy = String.valueOf(event.getTo().getBlockY());
        String sz = String.valueOf(event.getTo().getBlockZ());
        int x = event.getTo().getBlockX();
        int y = event.getTo().getBlockY();
        int z = event.getTo().getBlockZ();
        String ip = ResidenceSQL.getMain().getConfig().getString("SQL.ip");
        String table = ResidenceSQL.getMain().getConfig().getString("SQL.table");
        String user = ResidenceSQL.getMain().getConfig().getString("SQL.user");
        String password = ResidenceSQL.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + ip + "/" + table;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        try (Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt1 = connn.createStatement();
             ResultSet rss = stmt1.executeQuery("SELECT id, residencename, owner, X1, X2, Y1, Y2, Z1, Z2 FROM residence where ((X1 >= " + x + " AND X2 <= " + x + ") OR (X2 >= " + x + " AND X1 <= " + x + ")) & ((Y1 >= " + y + " AND Y2 <= " + y + ") OR (Y2 >= " + y + " AND Y1 <= " + y + ")) AND ((Z1 >= " + z + " AND Z2 <= " + z + ") OR (Z2 >= " + z + " AND Z1 <= " + z + "))");
        ) {
            if(rss.next()){
            int X1 = rss.getInt("X1");
            int X2 = rss.getInt("X2");
            int Y1 = rss.getInt("Y1");
            int Y2 = rss.getInt("Y2");
            int Z1 = rss.getInt("Z1");
            int Z2 = rss.getInt("Z2");
            owner = rss.getString("owner");
            resname = rss.getString("residencename");
            if (leave == 0) {
                if (keep == 0) {
                    if (((X1 >= x && X2 <= x) || (X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y) || (Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z) || (Z2 >= z && Z1 <= z))) {
                            player.sendMessage(ChatColor.GREEN + "歡迎來到 " + owner + " 的領地" + resname);
                        already = 1;
                        keep = 1;
                    }
                }
                if (already == 1) {
                    if (!(((X1 >= x && X2 <= x) || (X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y) || (Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z) || (Z2 >= z && Z1 <= z)))) {
                        if (keep == 1) {
                            leave = 1;
                        }

                    }
                }
            }
        if (leave == 1) {
            player.sendMessage(ChatColor.GREEN + "你以離開 " + owner + " 的領地" + resname);
            already = 0;
            leave = 0;
            keep = 0;
            xx = 1;
        }}


        }catch(SQLException t){
                throw new RuntimeException(t);

        }
    }
}
