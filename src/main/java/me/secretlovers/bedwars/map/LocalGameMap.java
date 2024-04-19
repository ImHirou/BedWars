package me.secretlovers.bedwars.map;

import lombok.Getter;
import me.secretlovers.bedwars.game.resourses.Generator;
import me.secretlovers.bedwars.utils.WorldUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LocalGameMap implements GameMap{
    private final File sourceWorldFolder;
    private File activeWorldFolder;
    private World bukkitWorld;
    private final String worldName;
    @Getter
    private List<Generator> diamondGenerators;
    @Getter
    private List<Generator> emeraldGenerators;

    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit) {
        this.sourceWorldFolder = new File(
                worldFolder,
                worldName
        );
        this.worldName = worldName;
        if(loadOnInit) load();

        //TODO implement generator locations
    }


    @Override
    public boolean load() {
        if (isLoaded()) return true;
        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );
        try {
            WorldUtil.copyWorld(sourceWorldFolder, activeWorldFolder);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Fail map load in GameMap from: " + sourceWorldFolder.getName());
            e.printStackTrace(System.err);
            return false;
        }

        bukkitWorld = Bukkit.getWorld(activeWorldFolder.getName());

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
