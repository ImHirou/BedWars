package me.secretlovers.bedwars.commands;

import me.secretlovers.bedwars.BedWars;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.Game;
import me.secretlovers.bedwars.game.GameManager;
import me.secretlovers.bedwars.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    private BedWars plugin;

    public GameCommand() {
        plugin = BedWars.plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player player)) return true;

        if(args[0].equals("list")) {
            for(Game game : GameManager.games) {
                player.sendMessage(String.valueOf(game.getId()));
            }
        }
        if(args[0].equals("join")) {
            GameManager.games.get(Integer.parseInt(args[1])).addPlayer(new BedWarsPlayer(player));
        }
        if(args[0].equals("create")) {
            GameManager.games.add(new Game(GameManager.games.size(), plugin.getMap()));
        }
        if(args[0].equals("finish")) {
            GameManager.games.get(Integer.parseInt(args[1])).changeGameState(GameState.FINISH);
        }

        return true;
    }
}
