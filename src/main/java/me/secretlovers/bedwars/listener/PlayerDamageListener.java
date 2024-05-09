package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event) {

        if((event.getDamager() instanceof Player)) return;
        if(event.getEntity() instanceof Player) return;

        Player player = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        BedWarsPlayer bedWarsPlayer = BedWars.plugin.getGameManager().getPlayerByBukkitPlayer(player);
        BedWarsPlayer bedWarsVictim = BedWars.plugin.getGameManager().getPlayerByBukkitPlayer(victim);

        Game game = BedWars.plugin.getGameManager().getGames().get(bedWarsPlayer.getGameID());
        if(game.teamByColor(bedWarsPlayer.getTeam())
                == game.teamByColor(bedWarsVictim.getTeam())) event.setCancelled(true);


    }

}
