package me.secretlovers.bedwars.game.trader;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;

@Getter
public class TraderEntity extends EntityVillager {

    private final Location location;
    private final boolean isDefault;

    public TraderEntity(World world, Location location, boolean isDefault) {
        super(world);
        this.setNoAI(true);
        this.isDefault = isDefault;
        if(isDefault) {
            this.setCustomName("Default Trader");
        } else {
            this.setCustomName("Diamond Trader");
        }
        this.setCustomNameVisible(true);
        this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.location = location;
    }

    public final void spawn() {
        this.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        if(!location.getChunk().isLoaded()) location.getChunk().load();
        this.getWorld().addEntity(this);
    }

}
