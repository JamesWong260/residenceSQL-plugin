package residencesql.residencesql.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import residencesql.residencesql.ResidenceSQL;

import java.sql.*;
import java.util.HashMap;

public class cometoresidencefact implements Listener {
    private static ResidenceSQL main;
    static final String Residence = "SELECT ALL id, residencename, owner, members, X1, X2, Y1, Y2, Z1, Z2 FROM Residence";
    public cometoresidencefact() {
        this.main = main;
    }
    private ResidenceSQL plugin = ResidenceSQL.getMain();

    private Connection connection;
    int already = 0;
    int leave = 0;
    int keep = 0;
    String owner ="";
    String resname ="";
    int maxX = 0;
    int minX = 0;
    int maxY = 0;
    int minY = 0;
    int maxZ = 0;
    int minZ = 0;
    int maxx = 0;
    int minx = 0;
    int maxy = 0;
    int miny = 0;
    int maxz = 0;
    int minz = 0;
    HashMap<Player, Boolean> enteredArea = new HashMap<Player, Boolean>();

    @EventHandler
    public void OnPlayerMoveEvent(PlayerMoveEvent e) {
        int xx = 1;
        Player p = e.getPlayer();
        String ID = String.valueOf(p);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        Location loc = p.getLocation();
        String sx = String.valueOf(e.getTo().getBlockX());
        String sy = String.valueOf(e.getTo().getBlockY());
        String sz = String.valueOf(e.getTo().getBlockZ());
        System.out.println(sx+sy+sz);
        String ip = ResidenceSQL.getMain().getConfig().getString("SQL.ip");
        String table = ResidenceSQL.getMain().getConfig().getString("SQL.table");
        String user = ResidenceSQL.getMain().getConfig().getString("SQL.user");
        String password = ResidenceSQL.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + ip + "/" + table;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        int X1 = 0;
        int X2 = 0;
        int Y1 = 0;
        int Y2 = 0;
        int Z1 = 0;
        int Z2 = 0;

        try (Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt1 = connn.createStatement();
             Statement stmt11 = connn.createStatement();
             ResultSet idMax = stmt1.executeQuery("SELECT MAX(id) FROM Residence");
             ResultSet rs = stmt11.executeQuery("SELECT ALL id, residencename, owner, X1, X2, Y1, Y2, Z1, Z2 FROM Residence WHERE id in ("+xx +")");
        ) {
            idMax.next();
            rs.next();
            int intmaxid = idMax.getInt(1);
            while (xx <= intmaxid) {
                xx += 1;
                    X1 = rs.getInt("X1");
                    X2 = rs.getInt("X2");
                    Y1 = rs.getInt("Y1");
                    Y2 = rs.getInt("Y2");
                    Z1 = rs.getInt("Z1");
                    Z2 = rs.getInt("Z2");
                    owner = rs.getString("owner");
                    resname = rs.getString("residencename");
                    if (X1 > X2) {
                        maxX = X1;
                        minX = X2;
                    }
                    if (X2 > X1) {
                        maxX = X2;
                        minX = X1;
                    }
                    if (Y1 > Y2) {
                        maxY = Y1;
                        minY = Y2;
                    }
                    if (Y2 > Y1) {
                        maxY = Y2;
                        minY = Y1;
                    }
                    if (Z1 > Z2) {
                        maxZ = Z1;
                        minZ = Z2;
                    }
                    if (Z2 > Z1) {
                        maxZ = Z2;
                        minZ = Z1;
                    }
                }
            World w = Bukkit.getWorld("world");
            Location cord1 = new Location(w, maxX, maxY, maxZ);
            Location cord2 = new Location(w, minX, minY, minZ);
            p = e.getPlayer();
            if (p.getWorld().equals(w)) {
                if ((p.getLocation().getBlockX() > cord1.getBlockX()) && (p.getLocation().getBlockX() > cord2.getBlockX())) {
                    p.sendMessage(ChatColor.RED + "message 1 Z");
                    if ((p.getLocation().getBlockY() >= cord1.getBlockY()) && (p.getLocation().getBlockY() < cord2.getBlockY())) {
                        p.sendMessage(ChatColor.RED + "message 2 Z");
                        if ((p.getLocation().getBlockZ() > cord1.getBlockZ()) && (p.getLocation().getBlockZ() > cord2.getBlockZ())) {
                            p.sendMessage(ChatColor.RED + "message 3 Z");

                        }
                    }

                }


            }
        }catch(SQLException t){
            throw new RuntimeException(t);
        }

    }




    }

