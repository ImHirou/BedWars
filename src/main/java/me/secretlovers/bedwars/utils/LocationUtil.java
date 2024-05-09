package me.secretlovers.bedwars.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

@UtilityClass
public class LocationUtil {

    public Location fromConfigurationSection(ConfigurationSection section, World world) {
        return new Location(world,
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"));
    }

    public boolean isEqual(Location first, Location second) {
        return first.getX() == second.getX() && first.getY() == second.getY() && first.getZ() == second.getZ();
    }

}
