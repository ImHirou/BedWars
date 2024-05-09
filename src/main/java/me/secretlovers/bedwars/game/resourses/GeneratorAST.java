package me.secretlovers.bedwars.game.resourses;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

@Getter
public class GeneratorAST extends EntityArmorStand {

    private final Location location;

    public GeneratorAST(World world, Location location, ResourseType resourseType) {
        super(world);
        this.location = location;
        this.setInvisible(true);
        this.setCustomName(resourseType.getName());
        this.setCustomNameVisible(true);
        this.setEquipment(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(resourseType.getItemStack()));
        this.setInvulnerable(true);
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoGravity(true);
    }

    public void rotateHead() {
        this.setHeadRotation((float) (this.getHeadRotation()+0.1));
    }
    public void setNewTime(int time, ResourseType type) {
        this.setCustomName(type.getName() + " " + time + " seconds left");
    }
    public void spawn() {
        this.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if(!location.getChunk().isLoaded()) location.getChunk().load();
        this.getWorld().addEntity(this);
    }

}
