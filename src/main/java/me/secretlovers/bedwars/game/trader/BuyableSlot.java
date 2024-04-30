package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import me.secretlovers.bedwars.utils.NBTUtil;

@Getter
public class BuyableSlot extends TraderSlot{

    private final ResourseType resourseType;
    private final int price;

    public BuyableSlot(BedWarsItem item, int slot, ResourseType resourseType, int price) {
        super(NBTUtil.setToBWItem(item, "buyable", "true"), slot);
        this.resourseType = resourseType;
        this.price = price;
    }

}
