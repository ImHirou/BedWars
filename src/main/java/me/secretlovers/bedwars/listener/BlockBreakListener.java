package me.secretlovers.bedwars.listener;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.team.Team;
import me.secretlovers.bedwars.utils.LocationUtil;
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
        BedWarsPlayer bedWarsPlayer = BedWars.plugin.getGameManager().getPlayerByBukkitPlayer(player);

        if(bedWarsPlayer == null) return;

        switch (event.getBlock().getType()) {
            case WOOL, WOOD, ENDER_STONE -> {
            }
            case BED_BLOCK, BED -> {
                Game game = BedWars.plugin.getGameManager().getGames().get(bedWarsPlayer.getGameID());
                Team team = game.teamByColor(bedWarsPlayer.getTeam());
                if (LocationUtil.isEqual(team.getBedLocation(), event.getBlock().getLocation()) ||
                        LocationUtil.isEqual(team.getBedLocation().getBlock().getRelative(BlockFace.EAST).getLocation(), event.getBlock().getLocation())) {
                    event.setCancelled(true);
                    team.placeBed();
                    return;
                }
                for (Team t : game.getTeams()) {
                    if (LocationUtil.isEqual(t.getBedLocation(), event.getBlock().getLocation()) ||
                            LocationUtil.isEqual(t.getBedLocation().getBlock().getRelative(BlockFace.EAST).getLocation(), event.getBlock().getLocation())) {
                        t.setHasBed(false);
                        event.setCancelled(true);
                        event.getBlock().getRelative(BlockFace.EAST).setType(Material.AIR);
                        event.getBlock().setType(Material.AIR);
                        return;
                    }
                }
            }
            default -> event.setCancelled(true);
        }
    }

}
