package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TraderTab {

    private final Trader trader;
    private final String name;
    private final List<TraderSlot> items = new ArrayList<>();
    private final Inventory inventory;

    public TraderTab(ConfigurationSection section, boolean isDefault) {
        this.name = section.getName();
        if(isDefault) trader = Trader.defaultTrader;
        else trader = Trader.diamondTrader;

        for(String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
                String type = itemSection.getString("slotType");
                if(type.equals("tabChange"))
                    items.add(new TabChangeSlot(new BedWarsItem(itemSection), itemSection.getString("name"), Integer.valueOf(key)));
                else if(type.equals("buyable"))
                    items.add(new BuyableSlot(new BedWarsItem(itemSection), Integer.valueOf(key), ResourseType.valueOf(itemSection.getString("resourse")), itemSection.getInt("price")));
                else if(type.equals("default")) items.add(new TraderSlot(new BedWarsItem(itemSection), Integer.valueOf(key)));
            }

        System.out.println(trader.toString());

        inventory = Bukkit.createInventory(trader, 27, name);
        for(TraderSlot slot : items) {
            inventory.setItem(slot.getSlot(), slot.getItem().getItemStack());
        }
    }
}
