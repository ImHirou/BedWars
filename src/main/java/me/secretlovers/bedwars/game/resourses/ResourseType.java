package me.secretlovers.bedwars.game.resourses;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum ResourseType {

    IRON("IRON", new ItemStack(Material.IRON_INGOT)),
    GOLD("GOLD", new ItemStack(Material.GOLD_INGOT)),
    DIAMOND("DIAMOND", new ItemStack(Material.DIAMOND)),
    EMERALD("EMERALD", new ItemStack(Material.EMERALD));

    final String name;
    final ItemStack itemStack;

    ResourseType(String name, ItemStack itemStack) {
        this.name       = name;
        this.itemStack  = itemStack;
    }

}
