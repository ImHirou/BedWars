package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        if(!BedWars.plugin.getGameManager().getGames().isEmpty()) {
            for (Game game : BedWars.plugin.getGameManager().getGames()) {
                if(!game.getPlayers().isEmpty()) {
                    for (BedWarsPlayer p : game.getPlayers()) {
                        if (p.getPlayer().getDisplayName().equals(player.getDisplayName())) {
                            game.removePlayer(p);
                        }
                    }
                }
            }
        }

    }

}
