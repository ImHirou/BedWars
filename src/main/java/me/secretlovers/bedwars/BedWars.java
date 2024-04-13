package me.secretlovers.bedwars;

import io.vertx.core.Vertx;
import lombok.Getter;
import me.secretlovers.bedwars.commands.GameCommand;
import me.secretlovers.bedwars.commands.WarpCommand;
import me.secretlovers.bedwars.database.PlayerManager;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.GameManager;
import me.secretlovers.bedwars.map.GameMap;
import me.secretlovers.bedwars.map.LocalGameMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class BedWars extends JavaPlugin {

    public static BedWars plugin;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private final File gameMapsFolder = new File(getDataFolder(), "gameMaps");
    private GameMap map;

    @Override
    public void onEnable() {
        plugin = this;
        playerManager = new PlayerManager(Vertx.vertx());
        gameManager = new GameManager();

        initGameMapsFolder();

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("game").setExecutor(new GameCommand());

    }

    @Override
    public void onDisable() {
        for(Game game : GameManager.games) {
            game.getGameMap().unload();
        }
    }

    private void initGameMapsFolder() {
        getDataFolder().mkdirs();

        if (!gameMapsFolder.exists()) gameMapsFolder.mkdirs();
        map = new LocalGameMap(gameMapsFolder, "test", false);
        System.out.println(gameMapsFolder.getAbsolutePath());
    }

}
