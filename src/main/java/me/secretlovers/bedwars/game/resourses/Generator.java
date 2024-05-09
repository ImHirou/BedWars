package me.secretlovers.bedwars.game.resourses;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Generator {

    @Setter
    private Long delay; //in ticks;
    private final ResourseType resourseType;
    private final BukkitRunnable dropper;
    private final Location location;
    private final GeneratorAST armorStand;

    public Generator(Long delay, ResourseType resourseType, Location location) {
        this.delay          = delay;
        this.resourseType   = resourseType;
        this.location       = location;
        this.armorStand     = new GeneratorAST(((CraftWorld) location.getWorld()).getHandle(), location.add(0, 2+ (double) resourseType.ordinal() /2, 0), resourseType);
        dropper = new BukkitRunnable() {
            Long timer = delay;

            @Override
            public void run() {
                timer--;
                armorStand.rotateHead();
                if (timer <= 0) {
                    timer=delay;
                    location.getWorld().dropItem(location, resourseType.getItemStack());
                }
                if (timer%20 == 0) {
                    armorStand.setNewTime((int) (timer/20), resourseType);
                }
            }

        };

    }

}
