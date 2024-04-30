package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.team.Team;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onEvent(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Game game = BedWars.plugin.getGameManager().findGameByPlayer(player);

        if(game == null) return;

        switch (event.getBlock().getType()) {
            case WOOL, WOOD, ENDER_STONE -> {
            }
            case BED_BLOCK, BED -> {
                BedWarsPlayer bedWarsPlayer = game.playerByNickname(player.getDisplayName());
                Team team = game.teamByColor(bedWarsPlayer.getTeam());
                if (team.getBedLocation() == event.getBlock().getLocation() ||
                        team.getBedLocation().getBlock().getRelative(BlockFace.EAST).getLocation() == event.getBlock().getLocation()) {
                    event.setCancelled(true);
                    return;
                }
                for (Team t : game.getTeams()) {
                    if (t.getBedLocation() == event.getBlock().getLocation() ||
                            t.getBedLocation().getBlock().getRelative(BlockFace.EAST).getLocation() == event.getBlock().getLocation()) {
                        t.setHasBed(false);
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        event.getBlock().getRelative(BlockFace.EAST).setType(Material.AIR);
                    }
                }
            }
            default -> event.setCancelled(true);
        }
    }

}
