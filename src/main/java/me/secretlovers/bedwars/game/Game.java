package me.secretlovers.bedwars.game;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.team.Team;
import me.secretlovers.bedwars.game.team.TeamColor;
import me.secretlovers.bedwars.map.GameMap;
import me.secretlovers.bedwars.utils.LocationUtil;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

@Getter
public class Game {

    private final int id;
    private GameState gameState;
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();
    private GameMap gameMap;

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
                    LocationUtil.fromConfigurationSection(teamSection.getConfigurationSection(key).getConfigurationSection("spawn"), gameMap.getWorld())));
        }
    }

    public void changeGameState(GameState gameState) {
        if(this.gameState == gameState) return;
        if(this.gameState == GameState.PLAYING && gameState == GameState.STARTING || gameState == GameState.WAITING) return;

        switch (gameState) {

            case UNKNOWN:
                gameMap.load();
                break;
            case WAITING:
                waitingPhase();
                break;
            case STARTING:
                startingPhase();
                break;
            case PLAYING:

                break;
            case FINISH:
                finishPhase();
                break;

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
        for(Team team : teams) {
            team.getPlayers().forEach(player -> {
                player.getPlayer().teleport(team.getSpawnLocation());
            });
        }
    }
    private void finishPhase() {
        gameMap.getWorld().getPlayers().forEach(player -> {
            player.kickPlayer("idi nahui");
        });
        gameMap.unload();
    }

    public void addPlayer(BedWarsPlayer player) {
        players.add(player);
        playerChangeTeam(player, teams.get(0));
        System.out.println(player.getNickname());
        System.out.println(teams.get(0).getSpawnLocation());
        System.out.println(teams.get(0).getMaxPlayers());
        System.out.println(teams.get(0).getTeamColor().toString());
        System.out.println(teams.get(0));
        waitingPhase();
    }
    public void removePlayer(BedWarsPlayer player) {
        players.remove(player);
    }
    private void playerChangeTeam(BedWarsPlayer player, Team team) {
        int index = teams.indexOf(team);
        Team team1 = teams.get(index);
        if (team1 == null) return;
        if (team1.getMaxPlayers() > team1.getPlayers().size()) {
            teams.get(index).getPlayers().add(player);
        }
    }

}
