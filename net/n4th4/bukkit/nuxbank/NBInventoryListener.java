package net.n4th4.bukkit.nuxbank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.n4th4.bukkit.nuxbank.gui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.inventory.InventoryClickEvent;
import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.player.SpoutPlayer;

public class NBInventoryListener extends InventoryListener
{
  private final Map<Integer, ItemStack[]> containers = new HashMap();
  private NuxBank plugin;

  public NBInventoryListener(NuxBank plugin)
  {
    this.plugin = plugin;
  }

  public void onInventoryClick(InventoryClickEvent event) {
    boolean emptyCursor = false;

    if (event.getInventory().getName().equals("Bank")) {
      if ((event.getCursor() != null) && (((event.getItem() == null) && (event.getSlot() != -999)) || (event.getItem().getTypeId() != event.getCursor().getTypeId()) || (event.getItem().getData().getData() != event.getCursor().getData().getData()))) {
        event.setResult(Event.Result.ALLOW);

        int id = event.getCursor().getTypeId();
        byte data = event.getCursor().getData().getData();
        Item.addAmount(id, data, event.getCursor().getAmount());
        MaterialData material = new MaterialData(id);
        SpoutManager.getPlayer(event.getPlayer()).sendNotification("Amount", "New amount : " + Item.getAmount(id, data), material.getItemType(), (short)data, 5000);

        emptyCursor = true;
      } else if (event.getItem() != null) {
        event.setResult(Event.Result.ALLOW);

        int id = event.getItem().getTypeId();
        byte data = event.getItem().getData().getData();
        int amount = Item.getAmount(id, data);
        if (amount > 0) {
          int amountToAdd = 1;
          if (event.isShiftClick()) {
            amountToAdd = event.getItem().getMaxStackSize();
          }

          if (amountToAdd > amount) {
            amountToAdd = amount;
          }

          HashMap rest = event.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(event.getItem().getTypeId(), amountToAdd, 0, Byte.valueOf(event.getItem().getData().getData())) });
          if (rest.get(Integer.valueOf(0)) != null) {
            amountToAdd -= ((ItemStack)rest.get(Integer.valueOf(0))).getAmount();
          }

          Item.addAmount(id, data, -1 * amountToAdd);

          MaterialData material = new MaterialData(id);
          SpoutManager.getPlayer(event.getPlayer()).sendNotification("Amount", "New amount : " + Item.getAmount(id, data), material.getItemType(), (short)data, 5000);

          if (Item.getAmount(id, data) <= 0)
            event.setItem(null);
        }
        else {
          event.setItem(null);
        }
      }

      event.setCancelled(true);
    } else {
      boolean found = false;

      if ((event.getItem() == null) || (!this.containers.containsKey(Integer.valueOf(event.getPlayer().getName().hashCode())))) {
        return;
      }
      Iterator it = NuxBank.allList.iterator();
      while (it.hasNext()) {
        ItemStack item = (ItemStack)it.next();
        if (item.getTypeId() == event.getItem().getTypeId()) {
          found = true;
          break;
        }
      }

      if (!found) {
        event.setCancelled(true);
        MaterialData material = new MaterialData(event.getItem().getTypeId());
        SpoutManager.getPlayer(event.getPlayer()).sendNotification("Unsupported", "This item is not supported", material.getItemType(), (short)event.getItem().getData().getData(), 5000);
      } else if (event.isShiftClick()) {
        event.setCancelled(true);
        MaterialData material = new MaterialData(event.getItem().getTypeId());
        SpoutManager.getPlayer(event.getPlayer()).sendNotification("Unsupported", "Shift click is unsupported", material.getItemType(), (short)event.getItem().getData().getData(), 5000);
      }
    }
    if (emptyCursor) {
      event.setResult(Event.Result.ALLOW);

      ItemStack item = event.getCursor();
      item.setAmount(0);
      event.setCursor(event.getItem());
      event.setItem(item);
    }
  }

  private boolean itemsIdentic(ItemStack first, ItemStack second) {
    return (first.getTypeId() == second.getTypeId()) && (first.getData().getData() == second.getData().getData());
  }

  public void onInventoryOpen(InventoryOpenEvent event)
  {
    if ((!event.isCancelled()) && (event.getInventory().getName() == "Bank"))
      this.containers.put(Integer.valueOf(event.getPlayer().getName().hashCode()), event.getInventory().getContents());
  }

  public void onInventoryClose(InventoryCloseEvent event) {
    if ((!event.isCancelled()) && (this.containers.containsKey(Integer.valueOf(event.getPlayer().getName().hashCode()))) && (event.getInventory().getName() == "Bank")) {
      this.containers.remove(Integer.valueOf(event.getPlayer().getName().hashCode()));

      Main popup = new Main(this.plugin, SpoutManager.getPlayer(event.getPlayer()));
      SpoutManager.getPlayer(event.getPlayer()).getMainScreen().closePopup();
      SpoutManager.getPlayer(event.getPlayer()).getMainScreen().attachPopupScreen(popup);
    }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.NBInventoryListener
 * JD-Core Version:    0.6.0
 */