package me.secretlovers.bedwars.game.trader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class TraderSlot {

    private final ItemStack item;
    private final int slot;

}
