package net.n4th4.bukkit.nuxbank.gui;

import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.inventory.InventoryBuilder;
import org.getspout.spoutapi.player.SpoutPlayer;

public class OpenButton extends GenericButton
{
  private Main GUI;
  private HashSet<ItemStack> list;

  public OpenButton(Main instance, HashSet<ItemStack> list)
  {
    this.GUI = instance;
    this.list = list;

    setText("Open");
    setWidth(100);
    setHeight(this.GUI.height);
  }

  public void onButtonClick(ButtonClickEvent event) {
    Inventory bankInv = SpoutManager.getInventoryBuilder().construct(36, "Bank");
    Iterator it = this.list.iterator();
    while (it.hasNext()) {
      ItemStack item = (ItemStack)it.next();

      bankInv.addItem(new ItemStack[] { item });
    }

    event.getPlayer().openInventoryWindow(bankInv);
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.gui.OpenButton
 * JD-Core Version:    0.6.0
 */