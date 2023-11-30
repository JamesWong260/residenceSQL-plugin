package residencesql.residencesql.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import residencesql.residencesql.ResidenceSQL;
import java.util.ArrayList;


import java.sql.*;
import java.util.Random;

public class TNTExplodeEvent implements Listener {
    private static ResidenceSQL main;
    static final String Residence = "SELECT id, residencename, owner, members, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    public TNTExplodeEvent() {
        this.main = main;
    }
    private ResidenceSQL plugin = ResidenceSQL.getMain();

    private Connection connection;
    int x= 0;

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event) {
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
        ResultSet rs = stmt1.executeQuery(Residence);
   ) {
       while (rs.next()) {
           ArrayList<Block> block = new ArrayList<>();
           for (Block b : event.blockList()) {
               //if(event.getEntity() instanceof TNTPrimed ){
               Location B = b.getLocation();
               double x = B.getX();
               double y = B.getY();
               double z = B.getZ();
               String sx = String.valueOf(B.getX());
               String sy = String.valueOf(B.getY());
               String sz = String.valueOf(B.getZ());
               int X1 = rs.getInt("X1");
               int X2 = rs.getInt("X2");
               int Y1 = rs.getInt("Y1");
               int Y2 = rs.getInt("Y2");
               int Z1 = rs.getInt("Z1");
               int Z2 = rs.getInt("Z2");
               String owner = rs.getString("owner");
               String world = rs.getString("world");
               if(world.equals(b.getWorld().getName())){
               if (((X1 >= x && X2 <= x)||(X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y)||(Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z)||(Z2 >= z && Z1 <= z))) {
                   block.add(b);
               }}}//}
               for (int i = 0; i < block.size(); i++) {
                   Bukkit.broadcastMessage("Removing block " + i + " from explosion!");
                   event.blockList().remove(block.get(i));
               }
               block.clear();
               for (Player all:Bukkit.getOnlinePlayers()){
                   all.sendMessage("debug");
               }
       }
   } catch (SQLException e) {
       throw new RuntimeException(e);
   }


}
}

