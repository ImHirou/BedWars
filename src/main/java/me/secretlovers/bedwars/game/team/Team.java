package me.secretlovers.bedwars.game.team;

import lombok.Getter;
import lombok.Setter;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.resourses.Generator;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import org.bukkit.Location;

import java.util.ArrayList;

@Getter
public class Team {

    private final TeamColor teamColor;
    private final int maxPlayers;
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();
    private final Location spawnLocation;
    private final Location bedLocation;
    private final Generator ironGenerator;
    private final Generator goldGenerator;
    @Setter
    private boolean hasBed;

    public Team(TeamColor teamColor, int maxPlayers, Location spawnLocation, Location bedLocation, Location generatorLocation) {
        this.teamColor      = teamColor;
        this.maxPlayers     = maxPlayers;
        this.spawnLocation  = spawnLocation;
        this.bedLocation = bedLocation;
        this.ironGenerator = new Generator(20L, ResourseType.IRON, generatorLocation);
        this.goldGenerator = new Generator(80L, ResourseType.GOLD, generatorLocation);
        this.hasBed = true;
    }

}
