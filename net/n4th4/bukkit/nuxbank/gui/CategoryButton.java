package net.n4th4.bukkit.nuxbank.gui;

import java.util.HashSet;
import java.util.Iterator;
import net.n4th4.bukkit.nuxbank.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.ItemWidget;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class CategoryButton extends GenericButton
{
  private Main GUI;
  private HashSet<ItemStack> list;

  public CategoryButton(Main instance, String text, HashSet<ItemStack> list)
  {
    this.GUI = instance;
    this.list = list;

    setText(text);
    setWidth(100);
    setHeight(this.GUI.height);
    setMargin(2);
    setFixed(true);
  }

  public void onButtonClick(ButtonClickEvent event) {
    this.GUI.removeWidget(this.GUI.mainContainer);
    this.GUI.mainContainer = new GenericContainer();

    Iterator it = this.list.iterator();
    int i = 0;
    GenericContainer itemsContainer = new GenericContainer();
    while (it.hasNext()) {
      ItemStack itemS = (ItemStack)it.next();
      GenericContainer itemC = new GenericContainer();
      GenericItemWidget item = new GenericItemWidget();
      item.setTypeId(itemS.getTypeId()).setData((short)itemS.getData().getData()).setHeight(7).setWidth(7).setDepth(7).setMargin(4).setFixed(true);
      GenericLabel amountLabel = new GenericLabel(Integer.toString(Item.getAmount(itemS.getTypeId(), itemS.getData().getData())));
      amountLabel.setHeight(7).setWidth(14).setMarginTop(10).setFixed(true);

      itemC.setLayout(ContainerType.VERTICAL).setAlign(WidgetAnchor.CENTER_RIGHT);
      itemC.addChildren(new Widget[] { item, amountLabel });

      itemsContainer.setLayout(ContainerType.HORIZONTAL).setAlign(WidgetAnchor.CENTER_CENTER);
      itemsContainer.setHeight(50).setWidth(i * 28);
      itemsContainer.addChild(itemC);

      if ((i + 1) % 15 == 0) {
        this.GUI.mainContainer.setLayout(ContainerType.VERTICAL).setAlign(WidgetAnchor.TOP_CENTER);
        this.GUI.mainContainer.setHeight(this.GUI.getScreen().getHeight()).setWidth(this.GUI.getScreen().getWidth());
        this.GUI.mainContainer.addChild(itemsContainer);
        itemsContainer = new GenericContainer();
      }
      i++;
    }
    this.GUI.mainContainer.setLayout(ContainerType.VERTICAL).setAlign(WidgetAnchor.TOP_CENTER);
    this.GUI.mainContainer.setHeight(this.GUI.getScreen().getHeight()).setWidth(this.GUI.getScreen().getWidth());
    this.GUI.mainContainer.addChild(itemsContainer);
    itemsContainer = new GenericContainer();

    BackButton backButton = new BackButton(this.GUI);
    OpenButton openButton = new OpenButton(this.GUI, this.list);

    itemsContainer.addChildren(new Widget[] { backButton, openButton });
    this.GUI.mainContainer.addChild(itemsContainer);

    this.GUI.attachWidget(this.GUI.plugin, this.GUI.mainContainer);
    this.GUI.setDirty(true);
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.gui.CategoryButton
 * JD-Core Version:    0.6.0
 */