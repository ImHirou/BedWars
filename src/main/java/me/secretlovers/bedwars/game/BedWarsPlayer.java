package me.secretlovers.bedwars.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor
public class BedWarsPlayer {

    private final String nickname;
    private Player player;
    private int kills;
    private int deaths;
    private int finalKills;
    private int bedsBroken;

    public BedWarsPlayer(Player player) {
        nickname    = player.getName();
        this.player = player;
        kills       = 0;
        deaths      = 0;
        finalKills  = 0;
        bedsBroken  = 0;
    }

}
