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
import org.bukkit.inventory.meta.ItemMeta;
import residencesql.residencesql.ResidenceSQL;

import java.util.*;
import java.sql.*;

public class setblockevent implements Listener {
    private static ResidenceSQL main;
    static final String Residence = "SELECT id, residencename, owner, UUID, members, membersUUID, world, X1, Y1, Z1, X2, Y2, Z2 FROM residence";
    static final String firstpoint = "SELECT id, X1, Y1, Z1 FROM firstpoint";
    static final String secondpoint = "SELECT id, X2, Y2, Z2 FROM secondpoint";
    public setblockevent() {
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
    //public String[][] array;

    Map<String, Integer> arrayX1 = new HashMap<String, Integer>();
    Map<String, Integer> arrayY1 = new HashMap<String, Integer>();
    Map<String, Integer> arrayZ1 = new HashMap<String, Integer>();
    Map<String, Integer> arrayX2 = new HashMap<String, Integer>();
    Map<String, Integer> arrayY2 = new HashMap<String, Integer>();
    Map<String, Integer> arrayZ2 = new HashMap<String, Integer>();

    Map<String, Integer> left = new HashMap<String, Integer>();

    Map<String, Integer> right = new HashMap<String, Integer>();

    Map<String, Integer> xx = new HashMap<String, Integer>();
    Map<String, Integer> yy = new HashMap<String, Integer>();
    Map<String, Integer> maxvolume = new HashMap<String, Integer>();
    Map<String, Integer> zz = new HashMap<String, Integer>();
    Map<String, Integer> conflict = new HashMap<String, Integer>();
    int leftblockX;
    int leftblockY;
    int leftblockZ;
    int rightblockX;
    int rightblockY;
    int rightblockZ;
    String rightclickblock = null;

    String leftclickblock = null;
   // int left = 0;
   // int right = 0;
    //int xx = 0;
   // int yy = 0;
   // int maxvolume = 0;
   // int zz = 0;
   // int conflict = 0;
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        int exist = 0;
        int leftexist = 0;
        int rightexist = 0;
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
        Block leftblock = event.getClickedBlock();
        Block rightblock = event.getClickedBlock();
        Player p = event.getPlayer();
        String world = p.getWorld().getName();
        Location loc = p.getLocation();
        World locworld = loc.getWorld();
        String uuid = String.valueOf(p.getUniqueId());
        String ID = String.valueOf(p);
        String q1 = "CraftPlayer{name=";
        String q2 = "}";
        String q3 = ID.replace(q1, "").replace(q2, "");
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        EquipmentSlot e = event.getHand();
        ItemMeta meta = item.getItemMeta();
        String name = item.getItemMeta().getDisplayName();
        List<String> lore = meta.getLore();
        conflict.put(q3, 0);
        if (e.equals(EquipmentSlot.HAND)) {
            if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)) {
                if(name.equals("領地發生器")) {
                    p.sendMessage(ChatColor.RED + "需要用鐵砧重新命名使用");
                    event.setCancelled(true);
                }
                else{
                if (meta.hasLore()) {
                if (lore.contains("領地發生器")) {
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
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)) {
                        if (((X1 >= x && X2 <= x) || (X2 >= x && X1 <= x)) && ((Y1 >= y && Y2 <= y) || (Y2 >= y && Y1 <= y)) && ((Z1 >= z && Z2 <= z) || (Z2 >= z && Z1 <= z))) {
                            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                                exist = 1;
                            }
                        }
                    } else {
                        System.out.println("pass");
                    }
                }
                //if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                 //   block.setType(Material.BEDROCK);
               // }
                if (exist == 0) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!(rightclickblock==null)){
                            locworld.getBlockAt(rightblockX,rightblockY,rightblockZ).setType(Material.valueOf(rightclickblock));
                            rightclickblock = event.getClickedBlock().getType().name();
                            int XX2 = leftblock.getX();
                            int YY2 = leftblock.getY();
                            int ZZ2 = leftblock.getZ();
                            rightblockX = XX2;
                            rightblockY = YY2;
                            rightblockZ = ZZ2;
                            arrayX2.put(q3, XX2);
                            arrayY2.put(q3, YY2);
                            arrayZ2.put(q3, ZZ2);
                            rightexist = 1;
                            right.put(q3, 1);
                            System.out.println("rightclick");
                            //block.setType(Material.BEDROCK);
                            }

                            if (rightclickblock==null) {
                            rightclickblock = event.getClickedBlock().getType().name();
                            int XX2 = leftblock.getX();
                            int YY2 = leftblock.getY();
                            int ZZ2 = leftblock.getZ();
                            rightblockX = XX2;
                            rightblockY = YY2;
                            rightblockZ = ZZ2;
                            arrayX2.put(q3, XX2);
                            arrayY2.put(q3, YY2);
                            arrayZ2.put(q3, ZZ2);
                            rightexist = 1;
                            right.put(q3, 1);
                            System.out.println("rightclick");
                            //block.setType(Material.BEDROCK);
                            }
                    }
                    if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        if (!(leftclickblock==null)){
                            locworld.getBlockAt(leftblockX,leftblockY,leftblockZ).setType(Material.valueOf(leftclickblock));
                            leftclickblock = event.getClickedBlock().getType().name();
                            int XX1 = rightblock.getX();
                            int YY1 = rightblock.getY();
                            int ZZ1 = rightblock.getZ();
                            leftblockX = XX1;
                            leftblockY = YY1;
                            leftblockZ = ZZ1;
                            arrayX1.put(q3, XX1);
                            arrayY1.put(q3, YY1);
                            arrayZ1.put(q3, ZZ1);
                            leftexist = 1;
                            left.put(q3, 1);
                            System.out.println("leftclick");
                            //block.setType(Material.BEDROCK);
                        }
                        if (leftclickblock==null){
                            leftclickblock = event.getClickedBlock().getType().name();
                            int XX1 = rightblock.getX();
                            int YY1 = rightblock.getY();
                            int ZZ1 = rightblock.getZ();
                            leftblockX = XX1;
                            leftblockY = YY1;
                            leftblockZ = ZZ1;
                            arrayX1.put(q3, XX1);
                            arrayY1.put(q3, YY1);
                            arrayZ1.put(q3, ZZ1);
                            leftexist = 1;
                            left.put(q3, 1);
                            System.out.println("leftclick");
                            //block.setType(Material.BEDROCK);
                            }
                    }
                }
                if (exist == 1) {
                    p.sendMessage(ChatColor.RED + "此處已經是領地");
                    locworld.getBlockAt(leftblockX,leftblockY,leftblockZ).setType(Material.valueOf(leftclickblock));
                    locworld.getBlockAt(rightblockX,rightblockY,rightblockZ).setType(Material.valueOf(rightclickblock));
                }
                //if ((leftexist == 1) && !(left.get(q3) + right.get(q3) == 2)) {

                if ((leftexist == 1) && (left.get(q3)== 1)) {
                    p.sendMessage(ChatColor.GREEN + "已選擇右鍵的坐標為 " + arrayX1.get(q3) + " " + arrayY1.get(q3) + " " + arrayZ1.get(q3));
                }
                //if ((rightexist == 1) && !(left.get(q3) + right.get(q3) == 2)) {
                    if ((rightexist == 1) && (right.get(q3) == 1)) {
                     p.sendMessage(ChatColor.GREEN + "已選擇左鍵的坐標為 " + arrayX2.get(q3) + " " + arrayY2.get(q3) + " " + arrayZ2.get(q3));
                }
                if (left.get(q3) + right.get(q3) == 2) {
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     Statement stmt1 = con.createStatement();
                     Statement stmt2 = con.createStatement();
                     ResultSet frs = stmt2.executeQuery(Residence);
                ) {
                    while (frs.next()) {
                        int point = 0;
                        int X1 = frs.getInt("X1");
                        int Y1 = frs.getInt("Y1");
                        int Z1 = frs.getInt("Z1");
                        int X2 = frs.getInt("X2");
                        int Y2 = frs.getInt("Y2");
                        int Z2 = frs.getInt("Z2");
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
                        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                             Statement stmt111 = conn.createStatement();
                             ResultSet idMax = stmt111.executeQuery("SELECT MAX(id) FROM Residence");
                        ) {
                            if (arrayX1.get(q3) > arrayX2.get(q3)) {
                                maxx = arrayX1.get(q3);
                                minx = arrayX2.get(q3);
                            }
                            if (arrayX2.get(q3) > arrayX1.get(q3)) {
                                maxx = arrayX2.get(q3);
                                minx = arrayX1.get(q3);
                            }
                            if (arrayY1.get(q3) > arrayY2.get(q3)) {
                                maxy = arrayY1.get(q3);
                                miny = arrayY2.get(q3);
                            }
                            if (arrayY2.get(q3) > arrayY1.get(q3)) {
                                maxy = arrayY2.get(q3);
                                miny = arrayY1.get(q3);
                            }
                            if (arrayZ1.get(q3) > arrayZ2.get(q3)) {
                                maxz = arrayZ1.get(q3);
                                minz = arrayZ2.get(q3);
                            }
                            if (arrayZ2.get(q3) > arrayZ1.get(q3)) {
                                maxz = arrayZ2.get(q3);
                                minz = arrayZ1.get(q3);
                            }
                            if ((maxy - miny) < 3) {
                                xx.put(q3, 1);
                                conflict.put(q3, 2);
                            }
                            if (((maxx - minx) * (maxz - minz)) < 60) {
                                zz.put(q3, 1);
                                conflict.put(q3, 2);
                            }
                            if (((maxx - minx) * (maxz - minz)) > 10000) {
                                maxvolume.put(q3, 1);
                                conflict.put(q3, 2);
                            }
                            else {
                                if (maxX > minx) {
                                    if (minX < maxx) {
                                        if (maxY > miny) {
                                            if (minY < maxy) {
                                                if (maxZ > minz) {
                                                    if (minZ < maxz) {
                                                        System.out.println("clict");
                                                        conflict.put(q3, 1);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException er) {
                            throw new RuntimeException(er);
                        }
                    }
                } catch (SQLException er) {
                    throw new RuntimeException(er);
                }}
            if (left.get(q3) + right.get(q3) == 2) {
                locworld.getBlockAt(leftblockX,leftblockY,leftblockZ).setType(Material.valueOf(leftclickblock));
                locworld.getBlockAt(rightblockX,rightblockY,rightblockZ).setType(Material.valueOf(rightclickblock));
            if (conflict.get(q3) == 0) {
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     Statement stmt1 = con.createStatement();
                     Statement stmt2 = con.createStatement();
                     ResultSet idMax = stmt1.executeQuery("SELECT MAX(id) FROM residence");
                     ResultSet nameCOUNT = stmt2.executeQuery("SELECT COUNT(owner) AS total FROM residence WHERE owner in ('"+q3+"')");
                ) {
                    nameCOUNT.next();
                    int COUNT = nameCOUNT.getInt("total");
                    if(COUNT < 3){
                    idMax.next();
                    int intmaxid = idMax.getInt(1);
                    intmaxid += 1;
                    String Residence1 = "INSERT INTO residence (id, residencename, owner, UUID, members, membersUUID, world, X1, Y1, Z1, X2, Y2, Z2) VALUES (" + intmaxid + ", '" + name + "', '" + q3 + "', '" + uuid + "', '', '','" + world + "' ,'" + arrayX1.get(q3) + "', '" + arrayY1.get(q3) + "', '" + arrayZ1.get(q3) + "', '" + arrayX2.get(q3) + "', '" + arrayY2.get(q3) + "', '" + arrayZ2.get(q3) + "')";
                    stmt1.executeUpdate(Residence1);
                    existid = 0;
                    left.put(q3, 0);
                    right.put(q3, 0);
                    xx.put(q3, 0);
                    zz.put(q3, 0);
                    p.sendMessage(ChatColor.GREEN + "領地成功建設");
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    }else{
                    existid = 0;
                    left.put(q3, 0);
                    right.put(q3, 0);
                    xx.put(q3, 0);
                    zz.put(q3, 0);
                    p.sendMessage(ChatColor.RED + "你擁有的領地已經超出上限，無法建造");
                    }

                } catch (SQLException ed) {
                    existid = 0;
                    left.put(q3, 0);
                    right.put(q3, 0);
                    xx.put(q3, 0);
                    zz.put(q3, 0);
                    p.sendMessage(ChatColor.RED + "該領地名稱可能已被使用無法建造");
                    ed.printStackTrace();
                    return;
                }
            }
            if (conflict.get(q3) == 1) {
                left.put(q3, 0);
                right.put(q3, 0);
                xx.put(q3, 0);
                zz.put(q3, 0);
                conflict.put(q3, 0);
                maxvolume.put(q3, 0);
                p.sendMessage(ChatColor.RED + "領地與其他重叠，無法設置");

            }
            if (xx.get(q3) == 1) {
                p.sendMessage(ChatColor.RED + "領地高度不能小於3");
                left.put(q3, 0);
                right.put(q3, 0);
                xx.put(q3, 0);
                zz.put(q3, 0);
                conflict.put(q3, 0);
                maxvolume.put(q3, 0);

            }
            if (zz.get(q3) == 1) {
                p.sendMessage(ChatColor.RED + "領地面積不能小於60");
                left.put(q3, 0);
                right.put(q3, 0);
                xx.put(q3, 0);
                zz.put(q3, 0);
                conflict.put(q3, 0);
                maxvolume.put(q3, 0);

            }
            if (maxvolume.get(q3) == 1) {
                p.sendMessage(ChatColor.RED + "領地面積不能大於10000");
                left.put(q3, 0);
                right.put(q3, 0);
                xx.put(q3, 0);
                zz.put(q3, 0);
                conflict.put(q3, 0);
                maxvolume.put(q3, 0);
            }
                }

        }catch(SQLException ee){
                throw new RuntimeException(ee);


    }
                }
                }
            }}
    }}
}





