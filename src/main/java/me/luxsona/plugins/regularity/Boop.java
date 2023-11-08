package me.luxsona.plugins.regularity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static org.bukkit.ChatColor.*;

public class Boop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 1){
            sender.sendMessage(ChatColor.RED + "Please specify who you want " +
                    "to boop.");
            return false;
        }
        else{
            Player reciever = Bukkit.getPlayer(args[0]);
            if(reciever == null){
                sender.sendMessage(ChatColor.RED + "Either that player is not" +
                        " online, or you mispelled their username.");
                return false;
            }
            reciever.sendMessage("[" + sender.getName() + "-> You] " +
                    LIGHT_PURPLE + " " + BOLD + "Boop!");
            sender.sendMessage("[Me -> " + reciever.getName() + "] " + LIGHT_PURPLE + " " + BOLD
                    + "Boop!" );
            return true;
        }
    }
}
