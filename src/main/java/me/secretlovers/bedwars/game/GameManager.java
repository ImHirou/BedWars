package me.secretlovers.bedwars.game;

import lombok.Getter;
import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.map.LocalGameMap;

import java.util.ArrayList;

@Getter
public class GameManager {

    public static int gameIndex = 0;
    private ArrayList<Game> games = new ArrayList<>();

    public GameManager() {

    }

    public void addGame() {
        games.add(new Game(gameIndex, new LocalGameMap(BedWars.plugin.getGameMapsFolder(), "test", true)));
        gameIndex++;
    }

    public void removeGame(int index) {
        games.remove(index);
    }



}
