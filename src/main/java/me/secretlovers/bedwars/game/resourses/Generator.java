package me.secretlovers.bedwars.game.resourses;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Generator {

    @Setter
    private Long delay; //in ticks;
    private final ResourseType resourseType;
    private final BukkitRunnable dropper;
    private final Location location;

    public Generator(Long delay, ResourseType resourseType, Location location) {
        this.delay          = delay;
        this.resourseType   = resourseType;
        this.location       = location;
        dropper = new BukkitRunnable() {
            Long timer = delay;
            @Override
            public void run() {
                timer--;
                if(timer<=0) {
                    timer=delay;
                    location.getWorld().dropItem(location, resourseType.getItemStack());
                }
            }

        };

    }

}
