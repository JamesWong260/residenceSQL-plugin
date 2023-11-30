package residencesql.residencesql.event;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import residencesql.residencesql.ResidenceSQL;

import java.sql.*;

public class removeresidence implements CommandExecutor {
    static final String Residence = "SELECT id, residencename, owner, members, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    static final String firstpoint = "SELECT MAX(id), X1, Y1, Z1, X2, Y2, Z2  FROM firstpoint";
    static final String secondpoint = "SELECT MAX(id), X2, Y2, Z2 FROM secondpoint";
    private static ResidenceSQL createRES;

    private ResidenceSQL plugin;
    public removeresidence() {
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
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt4 = conn.createStatement();
            Statement stmt5 = conn.createStatement();
            ResultSet firstpointrs = stmt4.executeQuery("SELECT * FROM residence WHERE residencename in ('"+ args[0]+ "')");
        ) {
            firstpointrs.next();
            int x1 = firstpointrs.getInt("X1");
            int y1 = firstpointrs.getInt("Y1");
            int z1 = firstpointrs.getInt("Z1");
            int x2 = firstpointrs.getInt("X2");
            int y2 = firstpointrs.getInt("Y2");
            int z2 = firstpointrs.getInt("Z2");
            String owner = firstpointrs.getString("owner");
            System.out.println(x1 + y1 + z1 + x2 + y2 + z2);
            if(q3.equals(owner)){
            String sqldata = "DELETE FROM residence " +
                    "WHERE residencename in ('"+args[0]+"')";
            stmt4.executeUpdate(sqldata);
            p.sendMessage(ChatColor.GREEN + "成功移除該領地");
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            else{
                p.sendMessage(ChatColor.RED + "這不是你的領地");
            }
          }
            catch (SQLException ed) {
            ed.printStackTrace();
            p.sendMessage(ChatColor.RED + "該領地或玩家不存在");
            }



            return false;}
    }
