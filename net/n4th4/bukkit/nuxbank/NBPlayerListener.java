package net.n4th4.bukkit.nuxbank;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.n4th4.bukkit.nuxbank.gui.Main;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutChest;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

public class NBPlayerListener extends PlayerListener
{
  private NuxBank plugin;

  public NBPlayerListener(NuxBank plugin)
  {
    this.plugin = plugin;
  }

  public void onPlayerInteract(PlayerInteractEvent event) {
    if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && ((event.getClickedBlock().getState() instanceof Sign))) {
      if (((Sign)event.getClickedBlock().getState()).getLine(0).equals("[bank]")) {
        SpoutPlayer player = (SpoutPlayer)event.getPlayer();
        if (player.getCurrentScreen() == null) {
          return;
        }
        if (player.getCurrentScreen().getScreenType() == ScreenType.GAME_SCREEN) {
          Main popup = new Main(this.plugin, SpoutManager.getPlayer(event.getPlayer()));
          player.getMainScreen().attachPopupScreen(popup);
        }
        event.setCancelled(true);
      }
    } else if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && ((event.getClickedBlock().getState() instanceof Chest)) && (this.plugin.c2bPlayers.containsKey(Integer.valueOf(event.getPlayer().getName().hashCode())))) {
      Inventory inv = ((SpoutChest)event.getClickedBlock().getState()).getLargestInventory();
      try {
        for (int i = 0; i < 54; i++) {
          boolean found = false;
          ItemStack item = inv.getItem(i);

          Iterator it = NuxBank.allList.iterator();
          while (it.hasNext()) {
            ItemStack item2 = (ItemStack)it.next();
            if (item2.getTypeId() == item.getTypeId()) {
              found = true;
              break;
            }
          }

          if (found) {
            Item.addAmount(item.getTypeId(), item.getData().getData(), item.getAmount());
            inv.clear(i);
          }
        }
      } catch (ArrayIndexOutOfBoundsException ex) {
      } finally {
        event.getPlayer().sendMessage(ChatColor.GREEN + "[NuxBank] Content transfered.");
        this.plugin.c2bPlayers.remove(Integer.valueOf(event.getPlayer().getName().hashCode()));
      }
    }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.NBPlayerListener
 * JD-Core Version:    0.6.0
 */