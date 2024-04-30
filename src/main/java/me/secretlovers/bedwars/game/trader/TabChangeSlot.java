package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import me.secretlovers.bedwars.utils.NBTUtil;

@Getter
public class TabChangeSlot extends TraderSlot{

    private final String tabName;

    public TabChangeSlot(BedWarsItem item, String name, int slot) {
        super(NBTUtil.setToBWItem(item, "traderTab", name), slot);
        tabName = name;
    }

}
