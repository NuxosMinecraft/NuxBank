package net.n4th4.bukkit.nuxbank.gui;

import net.n4th4.bukkit.nuxbank.NuxBank;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Main extends GenericPopup
{
  public GenericContainer mainContainer = new GenericContainer();
  public int height = 20;
  public int index = 0;
  public NuxBank plugin;
  public SpoutPlayer spoutplayer;

  public Main(NuxBank plugin, SpoutPlayer spoutplayer)
  {
    this.plugin = plugin;
    this.spoutplayer = spoutplayer;

    setItemsContainer();
  }

  public void setItemsContainer() {
    int baseY = 6;

    removeWidget(this.mainContainer);
    this.mainContainer = new GenericContainer();

    NavButton nextButton = new NavButton(this, ">>");
    NavButton prevButton = new NavButton(this, "<<");

    prevButton.setY(baseY).setX(321);
    nextButton.setY(baseY).setX(373);

    if (this.index == 0) {
      prevButton.setEnabled(false);
    }

    baseY += 25;

    CategoryButton baseButton = new CategoryButton(this, "Base", NuxBank.baseList);
    CategoryButton mineralsButton = new CategoryButton(this, "Minerals", NuxBank.mineralsList);
    CategoryButton woodButton = new CategoryButton(this, "Wood", NuxBank.woodList);
    CategoryButton redstoneButton = new CategoryButton(this, "Redstone", NuxBank.redstoneList);
    CategoryButton transportButton = new CategoryButton(this, "Transport", NuxBank.transportList);
    CategoryButton woolsAndDyesButton = new CategoryButton(this, "Wools & Dyes", NuxBank.woolsAndDyesList);
    CategoryButton netherButton = new CategoryButton(this, "Nether & End", NuxBank.netherAndEndList);
    CategoryButton farmingButton = new CategoryButton(this, "Farming", NuxBank.farmingList);
    CategoryButton foodButton = new CategoryButton(this, "Food", NuxBank.foodList);
    CategoryButton lootsButton = new CategoryButton(this, "Loots", NuxBank.lootsList);

    this.mainContainer.setLayout(ContainerType.VERTICAL).setAlign(WidgetAnchor.CENTER_CENTER);
    this.mainContainer.setHeight(this.spoutplayer.getMainScreen().getHeight()).setWidth(this.spoutplayer.getMainScreen().getWidth());
    this.mainContainer.addChildren(new Widget[] { baseButton, mineralsButton, woodButton, redstoneButton, transportButton, woolsAndDyesButton, netherButton, farmingButton, foodButton, lootsButton });

    attachWidget(this.plugin, this.mainContainer);
    setDirty(true);
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.gui.Main
 * JD-Core Version:    0.6.0
 */