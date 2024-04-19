package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import me.secretlovers.bedwars.utils.NBTUtil;
import org.bukkit.inventory.ItemStack;

@Getter
public class BuyableSlot extends TraderSlot{

    private final ResourseType resourseType;
    private final int price;

    public BuyableSlot(ItemStack item, int slot, ResourseType resourseType, int price) {
        super(NBTUtil.set(item, "buyable", "true"), slot);
        this.resourseType = resourseType;
        this.price = price;
    }

}
