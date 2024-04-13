package me.secretlovers.bedwars.game.team;

import lombok.Getter;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import org.bukkit.Location;

import java.util.ArrayList;

@Getter
public class Team {

    private final TeamColor teamColor;
    private final int maxPlayers;
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();
    private final Location spawnLocation;

    public Team(TeamColor teamColor, int maxPlayers, Location spawnLocation) {
        this.teamColor      = teamColor;
        this.maxPlayers     = maxPlayers;
        this.spawnLocation  = spawnLocation;
    }

}
