package net.n4th4.bukkit.nuxbank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class NBBlockListener extends BlockListener
{
  public void onSignChange(SignChangeEvent event)
  {
    if ((event.getLine(0).equals("[bank]")) && (!event.getPlayer().hasPermission("nuxbank.build"))) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(ChatColor.RED + "[NuxBank] You can't build a bank sign");
    }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.NBBlockListener
 * JD-Core Version:    0.6.0
 */