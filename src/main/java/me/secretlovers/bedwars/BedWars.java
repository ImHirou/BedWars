package me.secretlovers.bedwars;

import io.vertx.core.Vertx;
import lombok.Getter;
import me.secretlovers.bedwars.commands.GameCommand;
import me.secretlovers.bedwars.database.PlayerManager;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.GameManager;
import me.secretlovers.bedwars.game.resourses.GeneratorAST;
import me.secretlovers.bedwars.game.trader.Trader;
import me.secretlovers.bedwars.game.trader.TraderEntity;
import me.secretlovers.bedwars.listener.*;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    private void registerEntity() {
        EntityTypes.b.a(120, new MinecraftKey("TraderEntity"), TraderEntity.class);
        EntityTypes.b.a(30, new MinecraftKey("GeneratorAST"), GeneratorAST.class);
    }

}
