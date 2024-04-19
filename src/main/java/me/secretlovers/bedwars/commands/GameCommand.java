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

    private final BedWars plugin;

    public GameCommand() {
        plugin = BedWars.plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GameManager gameManager = plugin.getGameManager();

        if(args[0].equals("create") && args.length == 1) {
            gameManager.addGame();
            return true;
        }

        if(!(sender instanceof Player player)) return true;

        if(args.length == 1) {
            if(args[0].equals("list")) {
                for(Game game : gameManager.getGames()) {
                    player.sendMessage(String.valueOf(game.getId()));
                }
                return true;
            }
        }
        else if(args.length == 2) {
            if (args[0].equals("join")) {
                gameManager.getGames().get(Integer.parseInt(args[1])).addPlayer(new BedWarsPlayer(player));
                return true;
            }
            if (args[0].equals("finish")) {
                gameManager.getGames().get(Integer.parseInt(args[1])).changeGameState(GameState.FINISH);
                return true;
            }
        }

        return true;
    }
}
