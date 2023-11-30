package residencesql.residencesql.event;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import residencesql.residencesql.ResidenceSQL;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import static org.bukkit.Material.STICK;

public class setblock implements Listener {
    private static ResidenceSQL main;
    static final String Residence = "SELECT id, residencename, owner, members, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    static final String firstpoint = "SELECT id, X1, Y1, Z1 FROM firstpoint";
    static final String secondpoint = "SELECT id, X2, Y2, Z2 FROM secondpoint";
    public setblock() {
        this.main = main;
    }
    private ResidenceSQL plugin = ResidenceSQL.getMain();

    // Receive a shared list in constructor
    public int x1;
    public int y1;
    public int z1;
    public int x2;
    public int y2;
    public int z2;
    int existid = 0;
    int existed = 0;
    private Connection connection;
    public String[] array ={"","","","","",""};

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        int exist = 0;
        int leftexist = 0;
        int rightexist = 0;
        String ip = ResidenceSQL.getMain().getConfig().getString("SQL.ip");
        String table = ResidenceSQL.getMain().getConfig().getString("SQL.table");
        String user = ResidenceSQL.getMain().getConfig().getString("SQL.user");
        String password = ResidenceSQL.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + ip + "/" + table;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        Player p = event.getPlayer();
        String ID = String.valueOf(p);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        try (Connection connn1 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt11 = connn1.createStatement();
             ResultSet rs1 = stmt11.executeQuery(Residence);
        ) {
            while (rs1.next()) {
                int X1 = rs1.getInt("X1");
                int X2 = rs1.getInt("X2");
                int Y1 = rs1.getInt("Y1");
                int Y2 = rs1.getInt("Y2");
                int Z1 = rs1.getInt("Z1");
                int Z2 = rs1.getInt("Z2");
                if (!(p.getInventory().getItemInMainHand().getType() == null) && p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                    if (((X1 >= x && X2 <= x) || (X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y) || (Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z) || (Z2 >= z && Z1 <= z))) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        exist = 1;

                        String fdrop = "DELETE FROM firstpoint WHERE name in ('"+q3+"')";
                        stmt11.executeUpdate(fdrop);
                    }
                    }
                    System.out.println(array[0] + array[1] + array[2] + array[3] + array[4] + array[5]);
                }
                else {
                    System.out.println("pass");
                }
        }   if(exist == 0){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                String x2 = String.valueOf(block.getX());
                String y2 = String.valueOf(block.getY());
                String z2 = String.valueOf(block.getZ());
                array[3] = x2;
                array[4] = y2;
                array[5] = z2;
                rightexist = 1;
                if(existid == 0){
                existid+= 1;
                }
                else{
                existed= 1;
                }
            }}
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                String x1 = String.valueOf(block.getX());
                String y1 = String.valueOf(block.getY());
                String z1 = String.valueOf(block.getZ());
                array[0] = x1;
                array[1] = y1;
                array[2] = z1;
                leftexist = 1;
                if(existid == 0){
                   existid+= 1;
                   }
                else{
                    existed= 1;
                   }
                existid+= 1;
            }}
            }
            if(exist == 1){
                p.sendMessage(ChatColor.RED+"此處已經是領地");
                event.setCancelled(true);
            }
            if(existid == 2 && existed ==0 ){
                try (Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     Statement stmt = connn.createStatement();
                     ResultSet rs = stmt.executeQuery(firstpoint);
                     ResultSet idMax = stmt.executeQuery("SELECT MAX(id) FROM firstpoint");
                ) {
                    String fdrop = "DELETE FROM firstpoint WHERE name in ('"+q3+"')";
                    stmt.executeUpdate(fdrop);
                    try (Connection connn2 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                         Statement stmt2 = connn2.createStatement();
                         ResultSet rs2 = stmt2.executeQuery(firstpoint);
                         ResultSet idMax2 = stmt2.executeQuery("SELECT MAX(id) FROM firstpoint");
                    ) {
                        idMax2.next();
                        int intmaxid = idMax2.getInt(1);
                        intmaxid += 1;
                        String sqldata = "INSERT INTO firstpoint (id, name, X1, Y1, Z1, X2, Y2, Z2) VALUES ('"+intmaxid+"', '"+q3+"', '"+array[0]+"', '"+array[1]+"', '"+array[2]+"', '"+array[3]+"', '"+array[4]+"', '"+array[5]+"')";
                        stmt2.executeUpdate(sqldata);
                        existid = 0;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
            if(leftexist == 1){
                p.sendMessage(ChatColor.GREEN + "已選擇第一個點的坐標為 " + array[0] + " " + array[1] + " " + array[2]);
                event.setCancelled(true);
            }
            if(existed == 1){
                existid= 0;
            }
            if(rightexist == 1){
                p.sendMessage(ChatColor.GREEN + "已選擇第二個點的坐標為 " + array[3] + " " + array[4] + " " + array[5]);
                event.setCancelled(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }}





