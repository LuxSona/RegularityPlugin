package me.luxsona.plugins.regularity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DiceRoll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /**
         * This is a dice roll command. It takes in a number of dice and a number of sides.
         * It then rolls the dice and returns the result.
         *
         *         The command takes in either two or three arguments.
         *         If there are two arguments, the first is the number of
         *         dice, and the second is the number of sides.
         *         If there are three arguments, the first is the number of
         *         dice, the
         *         second is the number of sides, and the third is whether
         *         the result
         *         is broadcast to all players.
         * @param sender The sender of the command.
         *               This is the player who typed the command.
         *
         */

        if(args.length < 2 ){ //Test if args is less than 2
            sender.sendMessage("Please specify the number of dice and the number of sides.");
            return  false;
        }

        else if(args.length == 2){ //Roll the number of sides.
            int dice = Integer.parseInt(args[0]);
            int sides = Integer.parseInt(args[1]);
            int sum = 0;
            int[] rolls = new int[dice];
            for(int i = 0; i < dice; i++){
                int roll = (int) (Math.random()*sides) + 1;
                rolls[i] = roll;
                sum += roll;
            }
            sender.sendMessage("You rolled " + dice +
                    " dice with " + sides + " sides. Rolls: " + Arrays.toString(rolls) +
            " Sum: " + sum);
            return true;
        }

        else if(args.length == 3){
            int dice = Integer.parseInt(args[0]);
            int sides = Integer.parseInt(args[1]);
            int sum = 0;
            for(int i = 0; i < dice; i++){
                sum += (int) (Math.random() * sides) + 1;
            }
            if(args[2].equalsIgnoreCase("p")){
                String sender_username = sender.getName();
                sender.getServer().broadcast(Component.text().append(Component.text(sender_username +
                        "rolled a " + sum)).build());

                return true;
            }
            else{
                sender.sendMessage("You rolled a " + sum);
                return true;
            }
        }
        else{
            sender.sendMessage("Please specify the number of dice and the number of sides.");
            return false;
        }
    }
}
