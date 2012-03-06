package net.n4th4.bukkit.nuxbank.gui;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;

public class NavButton extends GenericButton
{
  private Main GUI;

  public NavButton(Main instance, String text)
  {
    this.GUI = instance;

    setText(text);
    setWidth(48);
    setHeight(this.GUI.height);
  }

  public void onButtonClick(ButtonClickEvent event) {
    if (event.getButton().getText().equals(">>"))
      this.GUI.index += 1;
    else if (event.getButton().getText().equals("<<")) {
      this.GUI.index -= 1;
    }
    this.GUI.setItemsContainer();
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.gui.NavButton
 * JD-Core Version:    0.6.0
 */