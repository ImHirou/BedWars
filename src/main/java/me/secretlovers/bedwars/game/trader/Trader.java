package me.secretlovers.bedwars.game.trader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.secretlovers.bedwars.BedWars;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Trader implements InventoryHolder {

    public static Trader defaultTrader;
    public static Trader diamondTrader;

    @Setter
    private List<TraderTab> tabs;

    public Trader() {

    }

    public static List<TraderTab> getDefaultTraderTabs() {
        ConfigurationSection trader = BedWars.plugin.getConfig().getConfigurationSection("trader");
        ConfigurationSection defTrader = trader.getConfigurationSection("default").getConfigurationSection("tabs");

        List<TraderTab> tabs = new ArrayList<>();

        for(String key : defTrader.getKeys(false)) {
            tabs.add(new TraderTab(defTrader.getConfigurationSection(key), true));
        }

        return tabs;
    }

    public static List<TraderTab> getDiamondTraderTabs() {

        ConfigurationSection trader = BedWars.plugin.getConfig().getConfigurationSection("trader");
        ConfigurationSection diamTrader = trader.getConfigurationSection("diamond").getConfigurationSection("tabs");

        List<TraderTab> tabs = new ArrayList<>();

        for(String key : diamTrader.getKeys(false)) {
            tabs.add(new TraderTab(diamTrader.getConfigurationSection(key), false));
        }
        return tabs;
    }

    public static void initTraders() {
        defaultTrader = new Trader();
        defaultTrader.setTabs(getDefaultTraderTabs());
        //diamondTrader = new Trader(getDiamondTraderTabs());
    }

    @Override
    public Inventory getInventory() {
        return tabs.get(0).getInventory();
    }
}
