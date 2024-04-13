package me.secretlovers.bedwars.commands;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.map.LocalGameMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    private LocalGameMap map;
    public WarpCommand() {
        map = (LocalGameMap) BedWars.plugin.getMap();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player player)) return true;

        player.teleport(map.getWorld().getSpawnLocation());

        return true;
    }
}
