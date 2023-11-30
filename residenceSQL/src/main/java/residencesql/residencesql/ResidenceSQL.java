package residencesql.residencesql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import residencesql.residencesql.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

public class ResidenceSQL extends JavaPlugin implements Listener {
    private static ResidenceSQL main;

    @Override
    public void onEnable() {
        main = this;
        Random random = new Random();
        main.saveDefaultConfig();
        System.out.println("started");
        String ip = main.getConfig().getString("SQL.ip");
        String table = main.getConfig().getString("SQL.table");
        String individual = main.getConfig().getString("SQL.individual SQL base");
        String user = main.getConfig().getString("SQL.user");
        String password = main.getConfig().getString("SQL.password");
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://"+ ip + "/" +table;
        String DB_IND = "jdbc:mysql://"+ ip + "/" +individual;
        String DB_USERNAME = user;
        String DB_PASSWORD = password;
        Connection conn = null;
        try{
            //Register the JDBC driver
            Class.forName(DB_DRIVER);
            //Open the connection
            conn = DriverManager.
                    getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if(conn != null){
                System.out.println("Successfully connected.");
            }else{
                System.out.println("Failed to connect.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        getCommand("welcomemessage").setExecutor(new welcometoresidence());
        getCommand("addmember").setExecutor(new setmember());
        getCommand("removemember").setExecutor(new removemember());
        getCommand("removeresidence").setExecutor(new removeresidence());
        Bukkit.getServer().getPluginManager().registerEvents(new breakblock(), this);
        //Bukkit.getServer().getPluginManager().registerEvents(new walk(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new TNTExplodeEvent(), this);
        //Bukkit.getServer().getPluginManager().registerEvents(new TNTblowblock(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new setblockevent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBurningEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new opendoor(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new placeblock(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new openchest(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new damageevent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new clickbutton(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new plateevent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new welcomemsg(), this);
        //Bukkit.getServer().getPluginManager().registerEvents(new whetherinres(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new floatevent(), this);
        //Bukkit.getServer().getPluginManager().registerEvents(new cometoresidenceevent(), this);
        try(Connection connn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connn.createStatement();
        ) {
            String firstpoint = "CREATE TABLE firstpoint " +
                        "(id INTEGER not NULL, " +
                        " name VARCHAR(255), " +
                        " X1 INTEGER, " +
                        " Y1 INTEGER, " +
                        " Z1 INTEGER, " +
                        " X2 INTEGER, " +
                        " Y2 INTEGER, " +
                        " Z2 INTEGER, " +
                        " PRIMARY KEY ( id ))";
            String firstpoint1 = "INSERT INTO firstpoint VALUES (0, 0, 0, 0, 0, 0, 0, 0)";
            System.out.println("Inserted records into the table...");
            String secondpoint = "CREATE TABLE secondpoint " +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " X2 INTEGER, " +
                    " Y2 INTEGER, " +
                    " Z2 INTEGER, " +
                    " PRIMARY KEY ( id ))";
            String secondpoint1 = "INSERT INTO secondpoint VALUES (0, 0, 0, 0, 0)";
            System.out.println("Inserted records into the table...");
            String Residence = "CREATE TABLE residence " +
                    "(id INTEGER not NULL, " +
                    " residencename VARCHAR(255) UNIQUE, " +
                    " owner VARCHAR(255), " +
                    " UUID VARCHAR(255), " +
                    " members TEXT, " +
                    " membersUUID TEXT, " +
                    " world VARCHAR(255), " +
                    " X1 INTEGER, " +
                    " X2 INTEGER, " +
                    " Y1 INTEGER, " +
                    " Y2 INTEGER, " +
                    " Z1 INTEGER, " +
                    " Z2 INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(Residence);
            System.out.println("Created table in given database...");
            String residence = "INSERT INTO secondpoint VALUES (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
            stmt.executeUpdate(residence);
            System.out.println("Inserting records into the table...");
            System.out.println("Inserted records into the table...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ItemStack superPotato = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta superPotatoMeta = superPotato.getItemMeta();
        superPotatoMeta.setDisplayName("領地發生器");
        superPotatoMeta.setLore(List.of("領地發生器"));
        superPotato.setItemMeta(superPotatoMeta);
        {
            NamespacedKey key = new NamespacedKey(this, "inventor");
            ShapedRecipe recipe = new ShapedRecipe(key, superPotato);
            recipe.shape(
                    "GGG",
                    "GXG",
                    "GGG");
            recipe.setIngredient('X', Material.STICK);
            recipe.setIngredient('G', Material.GOLD_INGOT);
            Bukkit.addRecipe(recipe);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
        public static ResidenceSQL getPlugin() {
        return new ResidenceSQL();
        }
        public static ResidenceSQL getMain(){
        return main;
        }

        public boolean contains(String cyber) {
        return false;
        }
        }

