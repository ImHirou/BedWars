package me.secretlovers.bedwars.game;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.resourses.Generator;
import me.secretlovers.bedwars.game.team.Team;
import me.secretlovers.bedwars.game.team.TeamColor;
import me.secretlovers.bedwars.game.trader.Trader;
import me.secretlovers.bedwars.game.trader.TraderEntity;
import me.secretlovers.bedwars.map.GameMap;
import me.secretlovers.bedwars.map.LocalGameMap;
import me.secretlovers.bedwars.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

@Getter
public class Game {

    private final int id;
    private GameState gameState;
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();
    private final GameMap gameMap;

    public Game(int id, GameMap map) {
        this.id    = id;
        gameMap = map;
        changeGameState(GameState.UNKNOWN);
        ConfigurationSection teamSection = BedWars.plugin.getConfig().getConfigurationSection("maps").
                getConfigurationSection(map.getWorldName()).
                getConfigurationSection("teams");
        for(String key : teamSection.getKeys(false)) {
            teams.add(new Team(TeamColor.valueOf(key),
                    BedWars.plugin.getConfig().getConfigurationSection("maps").
                            getConfigurationSection(map.getWorldName()).getInt("maxPlayers"),
                    LocationUtil.fromConfigurationSection(teamSection.getConfigurationSection(key).
                            getConfigurationSection("spawn"), gameMap.getWorld()),
                    LocationUtil.fromConfigurationSection(teamSection.getConfigurationSection(key).
                            getConfigurationSection("bed"), gameMap.getWorld()),
                    LocationUtil.fromConfigurationSection(teamSection.getConfigurationSection(key).
                            getConfigurationSection("generator"), gameMap.getWorld())));
        }
    }

    public void changeGameState(GameState gameState) {
        if(this.gameState == gameState) return;
        if(this.gameState == GameState.PLAYING && (gameState == GameState.STARTING || gameState == GameState.WAITING)) return;

        switch (gameState) {
            case UNKNOWN -> gameMap.load();
            case WAITING -> waitingPhase();
            case STARTING -> startingPhase();
            case PLAYING -> playingPhase();
            case FINISH -> finishPhase();
        }
    }

    private void waitingPhase() {
        new BukkitRunnable() {
            int timer = 5;
            @Override
            public void run() {
                --timer;
                if(timer<=0) {
                    changeGameState(GameState.STARTING);
                    cancel();
                }
                System.out.println("TO START " + timer);
            }
        }.runTaskTimer(BedWars.plugin, 20L, 20L);
    }
    private void startingPhase() {
        for(Generator generator : ((LocalGameMap) gameMap).getDiamondGenerators()) {
            generator.getArmorStand().spawn();
            generator.getDropper().runTaskTimer(BedWars.plugin, 20L, 1L);
        }
        for(Generator generator : ((LocalGameMap) gameMap).getEmeraldGenerators()) {
            generator.getArmorStand().spawn();
            generator.getDropper().runTaskTimer(BedWars.plugin, 20L, 1L);
        }
        ((LocalGameMap) gameMap).getTraders().forEach(TraderEntity::spawn);
        for(Team team : teams) {
            for(BedWarsPlayer player : team.getPlayers()) {
                System.out.println(team.getSpawnLocation());
                System.out.println(player.getNickname());
                player.getPlayer().teleport(team.getSpawnLocation());
            }
            team.getIronGenerator().getDropper().runTaskTimer(BedWars.plugin, 20L, 1L);
            team.getGoldGenerator().getDropper().runTaskTimer(BedWars.plugin, 20L, 1L);
            team.placeBed();
        }
    }
    private void playingPhase() {

    }
    private void finishPhase() {
        for(BedWarsPlayer player : players) {
            player.getPlayer().kickPlayer("game finished");
        }
        gameMap.unload();
    }

    public void addPlayer(BedWarsPlayer player) {
        player.setGameID(id);
        BedWars.plugin.getGameManager().addPlayer(player);
        players.add(player);
        playerChangeTeam(player, teams.get(players.size()%4));
        if(players.size() > 1) changeGameState(GameState.WAITING);
    }
    public void removePlayer(BedWarsPlayer player) {
        players.remove(player);
        for(Team team : teams) {
            if(!team.getPlayers().isEmpty()) {
                for (BedWarsPlayer p : team.getPlayers()) {
                    if (p == player) team.getPlayers().remove(player);
                }
            }
        }
    }
    private void playerChangeTeam(BedWarsPlayer player, Team team) {
        int index = teams.indexOf(team);
        Team team1 = teams.get(index);
        if (team1 == null) return;
        if (team1.getMaxPlayers() > team1.getPlayers().size()) {
            player.setTeam(teams.get(index).getTeamColor());
            teams.get(index).getPlayers().add(player);
        }
    }

    public BedWarsPlayer playerByNickname(String nickname) {
        for(BedWarsPlayer player : players) {
            if(player.getPlayer().getDisplayName().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    public Team teamByColor(TeamColor color) {
        for(Team team : teams) {
            if(team.getTeamColor() == color) return team;
        }
        return null;
    }

}
