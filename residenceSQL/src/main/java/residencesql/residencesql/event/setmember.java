package residencesql.residencesql.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import residencesql.residencesql.ResidenceSQL;
import java.util.UUID;

import java.sql.*;

public class setmember implements CommandExecutor {
    static final String Residence = "SELECT id, residencename, owner, members, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    static final String firstpoint = "SELECT MAX(id), X1, Y1, Z1, X2, Y2, Z2  FROM firstpoint";
    static final String secondpoint = "SELECT MAX(id), X2, Y2, Z2 FROM secondpoint";
    private static ResidenceSQL createRES;

    private ResidenceSQL plugin;
    public setmember() {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        setblock sb = new setblock();
        final Player p = (Player) sender;
        String ID = String.valueOf(p);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        String ip = ResidenceSQL.getMain().getConfig().getString("SQL.ip");
        String table = ResidenceSQL.getMain().getConfig().getString("SQL.table");
        String user = ResidenceSQL.getMain().getConfig().getString("SQL.user");
        String password = ResidenceSQL.getMain().getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + ip + "/" + table;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement stmt4 = conn.createStatement();
                 Statement stmt5 = conn.createStatement();
                 ResultSet firstpointrs = stmt4.executeQuery("SELECT * FROM residence WHERE residencename in ('" + args[1] + "')");
            ) {
                String name = args[0];
                OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(name);
                UUID id = player.getUniqueId();
                firstpointrs.next();
                String members = firstpointrs.getString("membersUUID");
                int x1 = firstpointrs.getInt("X1");
                int y1 = firstpointrs.getInt("Y1");
                int z1 = firstpointrs.getInt("Z1");
                int x2 = firstpointrs.getInt("X2");
                int y2 = firstpointrs.getInt("Y2");
                int z2 = firstpointrs.getInt("Z2");
                String owner = firstpointrs.getString("owner");
                System.out.println(x1 + y1 + z1 + x2 + y2 + z2);
                if(!members.contains(String.valueOf(id))){
                if (q3.equals(owner)) {
                    String sqldata = "UPDATE residence " +
                            "SET members = CONCAT(members, '" + args[0] + "\n') WHERE residencename in ('" + args[1] + "')";
                    String sqldata2 = "UPDATE residence " +
                            "SET membersUUID = CONCAT(membersUUID, '" + id + "\n') WHERE residencename in ('" + args[1] + "')";
                    stmt4.executeUpdate(sqldata);
                    stmt5.executeUpdate(sqldata2);
                    p.sendMessage(ChatColor.GREEN + "成功添加該成員");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                } else {
                    p.sendMessage(ChatColor.RED + "這不是你的領地");
                }
                }else{
                    p.sendMessage(ChatColor.RED + "該玩家已存在");
                }
            } catch (SQLException ed) {
                ed.printStackTrace();
                p.sendMessage(ChatColor.RED + "該領地或玩家不存在");
            }


          return false;
        }
}
