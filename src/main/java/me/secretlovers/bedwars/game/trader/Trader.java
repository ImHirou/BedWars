package me.secretlovers.bedwars.game.trader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

@Getter
@AllArgsConstructor
public class Trader implements InventoryHolder {

    private List<TraderTab> tabs;
    private final Location location;

    @Override
    public Inventory getInventory() {
        return tabs.get(0).getInventory();
    }
}
