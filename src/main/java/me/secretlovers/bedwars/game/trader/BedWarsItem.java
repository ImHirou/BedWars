package me.secretlovers.bedwars.game.trader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BedWarsItem {

    private ItemStack itemStack;
    private final String name;
    private final List<String> lore;

    public BedWarsItem(ConfigurationSection section) {
        itemStack   = new ItemStack(Material.getMaterial(section.getString("item")));
        name        = section.getString("name");
        lore        = section.getStringList("lore");
    }

    public ItemStack getItemStack() {
        ItemStack item = itemStack;
        if(item == null) item = new ItemStack(Material.AIR);
        ItemMeta meta = item.getItemMeta();
        if(name != null) meta.setDisplayName(name);
        if(lore != null) meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
