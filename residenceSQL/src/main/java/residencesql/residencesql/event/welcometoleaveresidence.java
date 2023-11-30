package residencesql.residencesql.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import residencesql.residencesql.ResidenceSQL;

import java.util.HashMap;
import java.util.UUID;

public class welcometoleaveresidence implements CommandExecutor {
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
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        return false;
    }
}
