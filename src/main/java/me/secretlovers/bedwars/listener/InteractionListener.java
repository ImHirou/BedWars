package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.game.trader.Trader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractionListener implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEntityEvent event) {

        System.out.println(event.getRightClicked().getType().toString());

        if(!(event.getRightClicked().getType() == EntityType.VILLAGER)) return;

        if(event.getRightClicked().getName().equals("Default Trader"))
            event.getPlayer().openInventory(Trader.defaultTrader.getTabs().get(0).getInventory());
        if(event.getRightClicked().getName().equals("Diamond Trader"))
            event.getPlayer().openInventory(Trader.diamondTrader.getTabs().get(0).getInventory());

        event.setCancelled(true);

    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event) {
        if(event.getEntityType() == EntityType.VILLAGER) event.setCancelled(true);
    }

}
