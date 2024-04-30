package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Game game = BedWars.plugin.getGameManager().findGameByPlayer(player);
        if (game == null) return;

        BedWarsPlayer bedWarsPlayer = game.playerByNickname(player.getDisplayName());
        Team team = game.teamByColor(bedWarsPlayer.getTeam());
        if (team.isHasBed()) {
            player.teleport(team.getSpawnLocation());
        } else {
            player.kickPlayer("you lose");
        }

    }

}
