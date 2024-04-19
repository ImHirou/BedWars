package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Getter
public class TraderTab {

    private final Trader trader;
    private final String name;
    private List<TraderSlot> items;
    private Inventory inventory;

    public TraderTab(String name) {
        this.name = name;
        trader = BedWars.plugin.getTrader();

        inventory = Bukkit.createInventory(trader, 36, name);
    }
}
