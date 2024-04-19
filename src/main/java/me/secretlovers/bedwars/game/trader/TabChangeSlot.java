package me.secretlovers.bedwars.game.trader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.secretlovers.bedwars.utils.NBTUtil;
import org.bukkit.inventory.ItemStack;

@Getter
public class TabChangeSlot extends TraderSlot{

    private final String tabName;

    public TabChangeSlot(ItemStack item, String name, int slot) {
        super(NBTUtil.set(item, "traderTab", name), slot);
        tabName = name;
    }

}
