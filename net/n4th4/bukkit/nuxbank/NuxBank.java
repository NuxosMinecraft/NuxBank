package net.n4th4.bukkit.nuxbank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NuxBank extends JavaPlugin
{
  public Logger log;
  public FileConfiguration config;
  public final Map<Integer, ItemStack[]> c2bPlayers = new HashMap();
  public static HashSet<ItemStack> baseList = new HashSet();
  public static HashSet<ItemStack> mineralsList = new HashSet();
  public static HashSet<ItemStack> woodList = new HashSet();
  public static HashSet<ItemStack> redstoneList = new HashSet();
  public static HashSet<ItemStack> transportList = new HashSet();
  public static HashSet<ItemStack> woolsAndDyesList = new HashSet();
  public static HashSet<ItemStack> netherAndEndList = new HashSet();
  public static HashSet<ItemStack> farmingList = new HashSet();
  public static HashSet<ItemStack> foodList = new HashSet();
  public static HashSet<ItemStack> lootsList = new HashSet();
  public static HashSet<ItemStack> allList = new HashSet();

  public void onEnable() {
    this.log = getServer().getLogger();
    this.config = getConfig();

    fillLists();

    ConnectionManager.init(this);
    Connection conn = ConnectionManager.getConnection();
    try
    {
      Statement state = conn.createStatement();
      state.executeUpdate("CREATE TABLE IF NOT EXISTS bank ( id INT, data INT, quantity INT );");
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    createRows(allList, conn);

    c2bCommand c2bCommand = new c2bCommand(this);
    getCommand("c2b").setExecutor(c2bCommand);

    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.PLAYER_INTERACT, new NBPlayerListener(this), Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.SIGN_CHANGE, new NBBlockListener(), Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.CUSTOM_EVENT, new NBInventoryListener(this), Event.Priority.Normal, this);

    PluginDescriptionFile pdfFile = getDescription();
    this.log.info("[NuxBank] " + pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
  }

  public void onDisable() {
    ConnectionManager.closeConnection();
  }

  private void fillLists() {
    baseList = new HashSet();
    baseList.add(new ItemStack(1, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(2, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(3, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(4, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(12, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(13, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(17, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(17, 1, 0, Byte.valueOf(1)));
    baseList.add(new ItemStack(17, 1, 0, Byte.valueOf(2)));
    baseList.add(new ItemStack(19, 1, 0, Byte.valueOf(0)));

    baseList.add(new ItemStack(24, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(44, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(44, 1, 0, Byte.valueOf(1)));
    baseList.add(new ItemStack(44, 1, 0, Byte.valueOf(3)));
    baseList.add(new ItemStack(44, 1, 0, Byte.valueOf(4)));
    baseList.add(new ItemStack(44, 1, 0, Byte.valueOf(5)));
    baseList.add(new ItemStack(45, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(48, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(49, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(67, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(82, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(98, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(98, 1, 0, Byte.valueOf(1)));
    baseList.add(new ItemStack(98, 1, 0, Byte.valueOf(2)));
    baseList.add(new ItemStack(108, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(109, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(336, 1, 0, Byte.valueOf(0)));
    baseList.add(new ItemStack(337, 1, 0, Byte.valueOf(0)));

    mineralsList = new HashSet();
    mineralsList.add(new ItemStack(14, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(15, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(16, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(56, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(21, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(73, 1, 0, Byte.valueOf(0)));

    mineralsList.add(new ItemStack(41, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(42, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(57, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(22, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(49, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(318, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(263, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(263, 1, 0, Byte.valueOf(1)));
    mineralsList.add(new ItemStack(265, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(266, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(264, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(20, 1, 0, Byte.valueOf(0)));
    mineralsList.add(new ItemStack(102, 1, 0, Byte.valueOf(0)));

    woodList = new HashSet();
    woodList.add(new ItemStack(5, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(18, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(18, 1, 0, Byte.valueOf(1)));
    woodList.add(new ItemStack(18, 1, 0, Byte.valueOf(2)));
    woodList.add(new ItemStack(44, 1, 0, Byte.valueOf(2)));
    woodList.add(new ItemStack(47, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(53, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(54, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(58, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(65, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(85, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(96, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(107, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(262, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(280, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(321, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(323, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(324, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(333, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(339, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(340, 1, 0, Byte.valueOf(0)));
    woodList.add(new ItemStack(355, 1, 0, Byte.valueOf(0)));

    redstoneList = new HashSet();
    redstoneList.add(new ItemStack(23, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(25, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(29, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(33, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(69, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(70, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(72, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(76, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(77, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(84, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(331, 1, 0, Byte.valueOf(0)));
    redstoneList.add(new ItemStack(356, 1, 0, Byte.valueOf(0)));

    transportList = new HashSet();
    transportList.add(new ItemStack(27, 1, 0, Byte.valueOf(0)));
    transportList.add(new ItemStack(28, 1, 0, Byte.valueOf(0)));
    transportList.add(new ItemStack(66, 1, 0, Byte.valueOf(0)));
    transportList.add(new ItemStack(328, 1, 0, Byte.valueOf(0)));
    transportList.add(new ItemStack(342, 1, 0, Byte.valueOf(0)));
    transportList.add(new ItemStack(343, 1, 0, Byte.valueOf(0)));

    woolsAndDyesList = new HashSet();
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(0)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(1)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(2)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(3)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(4)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(5)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(6)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(7)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(8)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(9)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(10)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(11)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(12)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(13)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(14)));
    woolsAndDyesList.add(new ItemStack(35, 1, 0, Byte.valueOf(15)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(0)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(1)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(2)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(3)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(4)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(5)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(6)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(7)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(8)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(9)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(10)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(11)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(12)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(13)));
    woolsAndDyesList.add(new ItemStack(351, 1, 0, Byte.valueOf(14)));

    netherAndEndList = new HashSet();
    netherAndEndList.add(new ItemStack(87, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(88, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(89, 1, 0, Byte.valueOf(0)));

    netherAndEndList.add(new ItemStack(112, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(113, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(114, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(121, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(122, 1, 0, Byte.valueOf(0)));
    netherAndEndList.add(new ItemStack(348, 1, 0, Byte.valueOf(0)));

    farmingList = new HashSet();
    farmingList.add(new ItemStack(6, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(6, 1, 0, Byte.valueOf(1)));
    farmingList.add(new ItemStack(6, 1, 0, Byte.valueOf(2)));
    farmingList.add(new ItemStack(18, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(18, 1, 0, Byte.valueOf(1)));
    farmingList.add(new ItemStack(18, 1, 0, Byte.valueOf(2)));
    farmingList.add(new ItemStack(31, 1, 0, Byte.valueOf(1)));
    farmingList.add(new ItemStack(31, 1, 0, Byte.valueOf(2)));
    farmingList.add(new ItemStack(32, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(37, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(38, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(39, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(40, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(81, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(86, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(91, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(106, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(111, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(295, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(296, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(103, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(338, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(361, 1, 0, Byte.valueOf(0)));
    farmingList.add(new ItemStack(362, 1, 0, Byte.valueOf(0)));

    foodList = new HashSet();
    foodList.add(new ItemStack(260, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(281, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(282, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(297, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(319, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(320, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(322, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(335, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(344, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(349, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(350, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(353, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(354, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(357, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(360, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(363, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(364, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(365, 1, 0, Byte.valueOf(0)));
    foodList.add(new ItemStack(366, 1, 0, Byte.valueOf(0)));

    lootsList = new HashSet();
    lootsList.add(new ItemStack(287, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(288, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(289, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(334, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(341, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(351, 1, 0, Byte.valueOf(15)));
    lootsList.add(new ItemStack(352, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(367, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(368, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(370, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(371, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(372, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(375, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(376, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(377, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(378, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(381, 1, 0, Byte.valueOf(0)));
    lootsList.add(new ItemStack(382, 1, 0, Byte.valueOf(0)));

    allList = new HashSet();
    allList.addAll(baseList);
    allList.addAll(mineralsList);
    allList.addAll(woodList);
    allList.addAll(redstoneList);
    allList.addAll(transportList);
    allList.addAll(woolsAndDyesList);
    allList.addAll(netherAndEndList);
    allList.addAll(farmingList);
    allList.addAll(foodList);
    allList.addAll(lootsList);
  }

  private void createRows(HashSet<ItemStack> list, Connection conn) {
    try {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ItemStack item = (ItemStack)it.next();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM bank WHERE id=" + item.getTypeId() + " AND  data=" + item.getData().getData() + ";");
        result.last();
        if (result.getRow() == 0) {
          state.executeUpdate("INSERT INTO bank VALUES ( " + item.getTypeId() + ", " + item.getData().getData() + ", 0 );");
        }
        conn.commit();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.NuxBank
 * JD-Core Version:    0.6.0
 */