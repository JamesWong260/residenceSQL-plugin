package residencesql.residencesql.event;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import residencesql.residencesql.ResidenceSQL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.sql.*;

public class createRES implements CommandExecutor {
    static final String Residence = "SELECT id, residencename, owner, X1, X2, Y1, Y2, Z1, Z2 FROM Residence";
    static final String firstpoint = "SELECT MAX(id), X1, Y1, Z1, X2, Y2, Z2  FROM firstpoint";
    static final String secondpoint = "SELECT MAX(id), X2, Y2, Z2 FROM secondpoint";
    private static ResidenceSQL createRES;

    private ResidenceSQL plugin;
    public createRES(ResidenceSQL plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int x = 0;
        int y = 0;
        int z = 0;
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
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt1= con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet frs = stmt2.executeQuery(Residence);
        ) {
            while (frs.next()){
            int point = 0;
            int X1 = frs.getInt("X1");
            int Y1 = frs.getInt("Y1");
            int Z1 = frs.getInt("Z1");
            int X2 = frs.getInt("X2");
            int Y2 = frs.getInt("Y2");
            int Z2 = frs.getInt("Z2");
            if(X1>X2) {
            maxX = X1;
            minX = X2;
            }
            if(X2>X1) {
            maxX = X2;
            minX = X1;
            }
            if(Y1>Y2) {
            maxY = Y1;
            minY = Y2;
            }
            if(Y2>Y1) {
            maxY = Y2;
            minY = Y1;
            }
            if(Z1>Z2) {
            maxZ = Z1;
            minZ = Z2;
            }
            if(Z2>Z1) {
            maxZ = Z2;
            minZ = Z1;
            }
            try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement stmt4 = conn.createStatement();
                Statement stmt5 = conn.createStatement();
                ResultSet firstpointrs = stmt4.executeQuery("SELECT * FROM firstpoint WHERE name in ('"+q3+"')");
            ) {
                while (firstpointrs.next()){
                int x1 = firstpointrs.getInt("X1");
                int y1 = firstpointrs.getInt("Y1");
                int z1 = firstpointrs.getInt("Z1");
                int x2 = firstpointrs.getInt("X2");
                int y2 = firstpointrs.getInt("Y2");
                int z2 = firstpointrs.getInt("Z2");
                    if(x1>x2) {
                    maxx = x1;
                    minx = x2;
                    }
                    if(x2>x1) {
                    maxx = x2;
                    minx = x1;
                    }
                    if(y1>y2) {
                    maxy = y1;
                    miny = y2;
                    }
                    if(y2>y1) {
                    maxy = y2;
                    miny = y1;
                    }
                    if(z1>z2) {
                    maxz = z1;
                    minz = z2;
                    }
                    if(z2>z1) {
                    maxz = z2;
                    minz = z1;
                    }
                    if(maxy - miny < 4){
                        x = 2;
                        y = 1;
                    }
                    if((maxx - minx)*(maxz - minz) < 60){
                      z = 1;
                    }else{
                    if (maxX > minx){
                      if (minX < maxx){
                        if (maxY > miny){
                         if (minY < maxy){
                          if (maxZ > minz){
                            if (minZ < maxz){
                                System.out.println("clict");
                                x = 1;

                                }
                    }
                          }
                         }
                        }
                      }
                    }
                //if (((x1 >= X1 && x2 <= X2) && (y1 >= Y1 && y2 <= Y2) && (z1 >= Z1 && z2 <= Z2)) ||
                  //  ((x1 <= X1  && x2 >= X2) && (y1 <= Y1  && y2 >= Y2) && (z1 <= Z1  && z2 >= Z2))||
                  //  ((x1 >= X1  && x2 <= X2) && (y1 <= Y1  && y2 >= Y2) && (z1 <= Z1  && z2 >= Z2))||
                 //   ((x1 >= X1  && x2 <= X2) && (y1 >= Y1  && y2 <= Y2) && (z1 <= Z1  && z2 >= Z2))||
               //     ((x1 <= X1  && x2 >= X2) && (y1 <= Y1  && y2 >= Y2) && (z1 >= Z1  && z2 <= Z2))
               // ){
               //
                }
              //  if (((x1 >= X1 || x2 <= X2) && (y1 >= Y1 || y2 <= Y2) && (z1 >= Z1 || z2 <= Z2))){
               //     point+= 1;
             //   }
             //   if (((x1 <= X1 || x2 >= X2) && (y1 <= Y1 || y2 >= Y2) && (z1 <= Z1 || z2 >= Z2))){
            //        point+= 1;
            //    }
               // if (((x1 <= X1 || x2 >= X2) && (y1 >= Y1 || y2 <= Y2) && (z1 >= Z1 || z2 <= Z2))){
             //       point+= 1;
             //   }
             //   if (((x1 >= X1 || x2 <= X2) && (y1 <= Y1 || y2 >= Y2) && (z1 <= Z1 || z2 >= Z2))){
              //      point+= 1;
                }
              //  if (((x1 >= X1 || x2 <= X2) && (y1 >= Y1 || y2 <= Y2) && (z1 <= Z1 || z2 >= Z2))){
              //      point+= 1;
              //  }
               // if (((x1 <= X1 || x2 >= X2) && (y1 <= Y1 || y2 >= Y2) && (z1 >= Z1 || z2 <= Z2))){
              //      point+= 1;
              //  }
              //      if(point == 5)
               //     {
                //    System.out.println("clict");
               //     x = 1;
              //   }
         //   }}
            catch (SQLException ed) {
                ed.printStackTrace();
            }
        }}
       catch (SQLException ed) {
           ed.printStackTrace();
        }
        if(x == 0){
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt1= con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            ResultSet idMax = stmt1.executeQuery("SELECT MAX(id) FROM Residence");
            ResultSet frs = stmt2.executeQuery(firstpoint);
        ) {
            idMax.next();
            int intmaxid = idMax.getInt(1);
            intmaxid += 1;
            try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement stmt4 = conn.createStatement();
                Statement stmt5 = conn.createStatement();
                ResultSet firstpointrs = stmt4.executeQuery("SELECT * FROM firstpoint WHERE name in ('"+q3+"')");
            ) {
                firstpointrs.next();
                int x1 = firstpointrs.getInt("X1");
                int y1 = firstpointrs.getInt("Y1");
                int z1 = firstpointrs.getInt("Z1");
                int x2 = firstpointrs.getInt("X2");
                int y2 = firstpointrs.getInt("Y2");
                int z2 = firstpointrs.getInt("Z2");
                String fdrop = "DELETE FROM firstpoint WHERE name in ('"+q3+"')";
                System.out.println(x1 + y1 + z1 + x2 + y2 + z2);
                String Residence1 = "INSERT INTO Residence VALUES (" + intmaxid + ", '"+q3+"', '"+q3+"', " + x1 + ", " + x2 + ", " + y1 + ", " + y2 + ", " + z1 + ", " + z2 + ")";
                stmt1.executeUpdate(Residence1);
                stmt2.executeUpdate(fdrop);
                p.sendMessage(ChatColor.GREEN + "領地成功建設");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
              }
                catch (SQLException ed) {
                ed.printStackTrace();

            }




        }
        catch (SQLException ed) {
            ed.printStackTrace();
        }
        }
     if(x == 1){
      p.sendMessage(ChatColor.RED + "領地與其他重叠，無法設置");
        }
        if(y == 1){
            p.sendMessage(ChatColor.RED + "領地高度不能小於3");
        }
        if(z == 1){
            p.sendMessage(ChatColor.RED + "領地面積不能小於60");
        }
        return false;
    }

}
