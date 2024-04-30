package me.secretlovers.bedwars;

import io.vertx.core.Vertx;
import lombok.Getter;
import me.secretlovers.bedwars.commands.GameCommand;
import me.secretlovers.bedwars.commands.WarpCommand;
import me.secretlovers.bedwars.database.PlayerManager;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.GameManager;
import me.secretlovers.bedwars.game.trader.Trader;
import me.secretlovers.bedwars.game.trader.TraderEntity;
import me.secretlovers.bedwars.game.trader.TraderTab;
import me.secretlovers.bedwars.listener.*;
import me.secretlovers.bedwars.map.GameMap;
import me.secretlovers.bedwars.map.LocalGameMap;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
public final class BedWars extends JavaPlugin {

    public static BedWars plugin;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private final File gameMapsFolder = new File(getDataFolder(), "gameMaps");

    @Override
    public void onEnable() {
        plugin          = this;
        playerManager   = new PlayerManager(Vertx.vertx());
        gameManager     = new GameManager();

        initGameMapsFolder();
        registerEntity();
        Trader.initTraders();

        registerEvents();

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("game").setExecutor(new GameCommand());

    }

    @Override
    public void onDisable() {
        for(Game game : gameManager.getGames()) {
            game.getGameMap().unload();
        }
    }

    private void initGameMapsFolder() {
        getDataFolder().mkdirs();
        if (!gameMapsFolder.exists()) gameMapsFolder.mkdirs();
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerLeave(),         this);
        getServer().getPluginManager().registerEvents(new InventoryClick(),      this);
        getServer().getPluginManager().registerEvents(new InteractionListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(),  this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    private void registerEntity() {
        EntityTypes.b.a(120, new MinecraftKey("TraderEntity"), TraderEntity.class);
    }

}
