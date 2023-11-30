package residencesql.residencesql.event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import residencesql.residencesql.ResidenceSQL;

import java.sql.*;

public class damageevent implements Listener {
    private static ResidenceSQL main;
    static final String Residence = "SELECT id, residencename, owner, UUID, members, membersUUID, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    public damageevent() {
        this.main = main;
    }
    private ResidenceSQL plugin = ResidenceSQL.getMain();

    private Connection connection;
    int x= 0;
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Location block = event.getEntity().getLocation();
        double x = block.getX();
        double y = block.getY();
        double z = block.getZ();
        String sx = String.valueOf(block.getX());
        String sy = String.valueOf(block.getY());
        String sz = String.valueOf(block.getZ());
        System.out.println(sx+sy+sz);
        String ip = ResidenceSQL.getMain().getConfig().getString("SQL.ip");
        String table = ResidenceSQL.getMain().getConfig().getString("SQL.table");
        String user = ResidenceSQL.getMain().getConfig().getString("SQL.user");
        String password = ResidenceSQL.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + ip + "/" + table;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        String ID = String.valueOf(damager);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        String uuid = String.valueOf(damager.getUniqueId());
        String victimuuid = String.valueOf(victim.getUniqueId());
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
        try (Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt1 = connn.createStatement();
             ResultSet rs = stmt1.executeQuery(Residence);
        ) {
                while (rs.next()) {
                    int X1 = rs.getInt("X1");
                    int X2 = rs.getInt("X2");
                    int Y1 = rs.getInt("Y1");
                    int Y2 = rs.getInt("Y2");
                    int Z1 = rs.getInt("Z1");
                    int Z2 = rs.getInt("Z2");
                    String owner = rs.getString("UUID");
                    String members = rs.getString("membersUUID");
                    String world = rs.getString("world");
                    //if(!uuid.equals(owner)&&!members.contains(uuid)){
                    if(world.equals(damager.getWorld().getName())){
                    if (((X1 >= x && X2 <= x)||(X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y)||(Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z)||(Z2 >= z && Z1 <= z))) {
                        event.setCancelled(true);
                        damager.sendMessage(ChatColor.RED + "無權PVP");}
                }}
        //}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
}
