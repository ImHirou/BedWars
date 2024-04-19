package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        for(Game game : BedWars.plugin.getGameManager().getGames()) {
            game.getPlayers().forEach(bedWarsPlayer -> {
                if(bedWarsPlayer.getPlayer() == player) {
                    game.removePlayer(bedWarsPlayer);
                }
            });
        }

    }

}
