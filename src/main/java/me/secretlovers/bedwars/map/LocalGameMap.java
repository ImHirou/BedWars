package me.secretlovers.bedwars.map;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.resourses.Generator;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import me.secretlovers.bedwars.game.trader.Trader;
import me.secretlovers.bedwars.game.trader.TraderEntity;
import me.secretlovers.bedwars.utils.LocationUtil;
import me.secretlovers.bedwars.utils.WorldUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalGameMap implements GameMap{
    private final File sourceWorldFolder;
    private File activeWorldFolder;
    private World bukkitWorld;
    private final String worldName;
    @Getter
    private List<Generator> diamondGenerators = new ArrayList<>();
    @Getter
    private List<Generator> emeraldGenerators = new ArrayList<>();
    @Getter
    private List<TraderEntity> traders = new ArrayList<>();

    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit) {
        this.sourceWorldFolder = new File(
                worldFolder,
                worldName
        );
        this.worldName = worldName;

        if(loadOnInit) {
            load();

            ConfigurationSection generatorSection = BedWars.plugin.getConfig().getConfigurationSection("maps").getConfigurationSection(worldName).getConfigurationSection("generator");
            ConfigurationSection diamondSection = generatorSection.getConfigurationSection("diamond");
            ConfigurationSection emeraldSection = generatorSection.getConfigurationSection("emerald");

            for (String key : diamondSection.getKeys(false)) {
                diamondGenerators.add(new Generator(600L, ResourseType.DIAMOND, LocationUtil.fromConfigurationSection(diamondSection.getConfigurationSection(key), bukkitWorld)));
            }
            for (String key : emeraldSection.getKeys(false)) {
                emeraldGenerators.add(new Generator(600L, ResourseType.EMERALD, LocationUtil.fromConfigurationSection(diamondSection.getConfigurationSection(key), bukkitWorld)));
            }

            ConfigurationSection traderSection = BedWars.plugin.getConfig().getConfigurationSection("maps").getConfigurationSection(worldName).getConfigurationSection("traders");
            ConfigurationSection defaultTraderSection = traderSection.getConfigurationSection("default");
            ConfigurationSection diamondTraderSection = traderSection.getConfigurationSection("diamond");

            for (String key : defaultTraderSection.getKeys(false)) {
                traders.add(new TraderEntity(((CraftWorld) bukkitWorld).getHandle(),
                        LocationUtil.fromConfigurationSection(defaultTraderSection.getConfigurationSection(key), bukkitWorld), true));
            }

            for (String key : diamondTraderSection.getKeys(false)) {
                traders.add(new TraderEntity(((CraftWorld) bukkitWorld).getHandle(),
                        LocationUtil.fromConfigurationSection(diamondTraderSection.getConfigurationSection(key), bukkitWorld), false));
            }
        }
    }


    @Override
    public boolean load() {
        if (isLoaded()) return true;
        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );
        if (!activeWorldFolder.exists()) activeWorldFolder.mkdirs();
        System.out.println(sourceWorldFolder.getAbsolutePath());
        System.out.println(activeWorldFolder.getAbsolutePath());
        try {
           FileUtils.copyDirectory(sourceWorldFolder, activeWorldFolder);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Fail map load in GameMap from: " + sourceWorldFolder.getName());
            e.printStackTrace(System.err);
            return false;
        }

        WorldCreator creator = new WorldCreator(activeWorldFolder.getName());
        creator.createWorld();

        bukkitWorld = Bukkit.getWorld(activeWorldFolder.getName());
        System.out.println(bukkitWorld);
        System.out.println(activeWorldFolder.getAbsolutePath());

        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(false);

        return isLoaded();
    }

    @Override
    public void unload() {
        if (bukkitWorld != null) Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder != null) {
            try {
                FileUtils.deleteDirectory(activeWorldFolder);
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        bukkitWorld = null;
        activeWorldFolder = null;
    }

    @Override
    public boolean restoreFromSource() {
        unload();
        return load();
    }

    @Override
    public boolean isLoaded() {
        return bukkitWorld != null;
    }

    @Override
    public World getWorld() {
        return bukkitWorld;
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

}
