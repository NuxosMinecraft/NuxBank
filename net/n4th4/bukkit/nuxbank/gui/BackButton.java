package net.n4th4.bukkit.nuxbank.gui;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class BackButton extends GenericButton
{
  private Main GUI;

  public BackButton(Main instance)
  {
    this.GUI = instance;

    setText("Back");
    setWidth(100);
    setHeight(this.GUI.height);
  }

  public void onButtonClick(ButtonClickEvent event) {
    this.GUI.setItemsContainer();
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.gui.BackButton
 * JD-Core Version:    0.6.0
 */