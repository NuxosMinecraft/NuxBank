package net.n4th4.bukkit.nuxbank;

import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c2bCommand
  implements CommandExecutor
{
  private NuxBank plugin;

  public c2bCommand(NuxBank plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
  {
    if ((sender instanceof Player)) {
      Player player = (Player)sender;

      if (player.hasPermission("nuxbank.c2b")) {
        this.plugin.c2bPlayers.put(Integer.valueOf(player.getName().hashCode()), null);
        sender.sendMessage(ChatColor.GREEN + "[NuxBank] Now, left-click a chest.");
      } else {
        sender.sendMessage(ChatColor.RED + "[NuxBank] Permissions denied.");
      }
      return true;
    }
    sender.sendMessage(ChatColor.RED + "[NuxBank] Only in-game commands are supported.");
    return true;
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.c2bCommand
 * JD-Core Version:    0.6.0
 */