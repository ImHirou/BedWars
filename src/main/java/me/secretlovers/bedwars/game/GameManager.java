package me.secretlovers.bedwars.game;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.map.LocalGameMap;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class GameManager {

    public static int gameIndex = 0;
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();

    public GameManager() {

    }

    public void addGame() {
        games.add(new Game(gameIndex, new LocalGameMap(BedWars.plugin.getGameMapsFolder(), "test", true)));
        gameIndex++;
    }

    public void removeGame(int index) {
        games.remove(index);
    }

    public void addPlayer(BedWarsPlayer player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        for(BedWarsPlayer p : players) {
            if(p.getPlayer() == player) {
                players.remove(p);
                return;
            }
        }
    }
    public Game findGameByPlayer(Player player) {
        for(Game game : games) {
            for(BedWarsPlayer bedWarsPlayer : game.getPlayers()) {
                if(bedWarsPlayer.getPlayer().getDisplayName().equals(player.getDisplayName())) {
                    return game;
                }
            }
        }
        return null;
    }

    public BedWarsPlayer getPlayerByBukkitPlayer(Player player) {
        for(BedWarsPlayer p : players) {
            if(p.getPlayer().getDisplayName().equals(player.getDisplayName())) {
                return p;
            }
        }
        return null;
    }

}
