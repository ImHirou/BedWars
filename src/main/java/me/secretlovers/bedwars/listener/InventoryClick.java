package me.secretlovers.bedwars.listener;

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

import java.util.Arrays;

public class InventoryClick implements Listener {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Trader trader = Trader.defaultTrader;

        if (event.getClickedInventory().getHolder() == trader) {

            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;


            if (NBTUtil.getFromItemStack(event.getCurrentItem(), "buyable").equals("true")) {
                int slot = event.getSlot();
                trader.getTabs().forEach(traderTab -> {
                    if (event.getClickedInventory().getName().equals(traderTab.getName())) {
                        BuyableSlot bSlot = null;
                        for (TraderSlot traderItem : traderTab.getItems()) {
                            if (traderItem.getSlot() == slot)
                                bSlot = (BuyableSlot) traderItem;
                        }
                        Inventory playerInventory = player.getInventory();
                        if (bSlot != null && playerInventory.containsAtLeast(bSlot.getResourseType().getItemStack(), bSlot.getPrice())) {
                            playerInventory.addItem(bSlot.getItem().getItemStack());
                            for (ItemStack itemStack : playerInventory.getContents()) {
                                if (itemStack.getType() == bSlot.getResourseType().getItemStack().getType()) {
                                    itemStack.setAmount(itemStack.getAmount() - bSlot.getPrice());
                                    break;
                                }
                            }
                        }
                    }
                });
            }
            String tabKey = NBTUtil.getFromItemStack(event.getCurrentItem(), "traderTab");
            if (tabKey != null) {
                trader.getTabs().forEach(traderTab -> {
                    if (traderTab.getName().equals(tabKey.toLowerCase()))
                        player.openInventory(traderTab.getInventory());
                });
            }

        }
    }

}

