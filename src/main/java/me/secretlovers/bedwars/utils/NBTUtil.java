package me.secretlovers.bedwars.utils;

import lombok.experimental.UtilityClass;
import me.secretlovers.bedwars.game.trader.BedWarsItem;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NBTUtil {

    public ItemStack setToItemStack(ItemStack itemStack, String key, String value) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsItem.getTag();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            nmsItem.setTag(tagCompound);
        }
        tagCompound.setString(key, value);
        itemStack.setItemMeta(CraftItemStack.getItemMeta(nmsItem));
        return itemStack;
    }

    public String getFromItemStack(ItemStack itemStack, String key) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsItem.getTag();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            nmsItem.setTag(tagCompound);
        }
        return tagCompound.getString(key);
    }

    public BedWarsItem setToBWItem(BedWarsItem bedWarsItem, String key, String value) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(bedWarsItem.getItemStack());
        NBTTagCompound tagCompound = nmsItem.getTag();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            nmsItem.setTag(tagCompound);
        }
        tagCompound.setString(key, value);
        bedWarsItem.getItemStack().setItemMeta(CraftItemStack.getItemMeta(nmsItem));
        return bedWarsItem;
    }

    public String getFromBWItem(BedWarsItem bedWarsItem, String key) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(bedWarsItem.getItemStack());
        NBTTagCompound tagCompound = nmsItem.getTag();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            nmsItem.setTag(tagCompound);
        }
        return tagCompound.getString(key);
    }

}
