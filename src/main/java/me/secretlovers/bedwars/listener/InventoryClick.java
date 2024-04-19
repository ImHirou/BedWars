package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.trader.BuyableSlot;
import me.secretlovers.bedwars.game.trader.Trader;
import me.secretlovers.bedwars.game.trader.TraderSlot;
import me.secretlovers.bedwars.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Trader trader = BedWars.plugin.getTrader();

        if(event.getClickedInventory().getHolder() == player) return;
        if(event.getClickedInventory().getHolder() != trader) return;



        if(NBTUtil.get(event.getCurrentItem(), "buyable") != null) {
            int slot = event.getSlot();
            trader.getTabs().forEach( traderTab -> {
                if(event.getClickedInventory().getName().equals(traderTab.getName())) {
                    BuyableSlot item = null;
                    for(TraderSlot traderItem : traderTab.getItems()) {
                        if(traderItem.getSlot() == slot)
                            item = (BuyableSlot) traderItem;
                    }
                    Inventory playerInventory = player.getInventory();
                    if(item != null && playerInventory.containsAtLeast(item.getResourseType().getItemStack(), item.getPrice())) {
                        playerInventory.addItem(item.getItem());
                        playerInventory.remove(new ItemStack(item.getResourseType().getItemStack().getType(), item.getPrice()));
                    }
                }
            });
        }
        String tabKey = NBTUtil.get(event.getCurrentItem(), "traderTab");
        if(tabKey != null) {
            trader.getTabs().forEach(traderTab -> {
                if(traderTab.getName().equals(tabKey))
                    player.openInventory(traderTab.getInventory());
            });
        }

        event.setCancelled(true);

    }

}

