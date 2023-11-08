package me.luxsona.plugins.regularity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriggerWarn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length <= 1){
            sender.sendMessage(ChatColor.RED + "Invalid format. /triggerwarn " +
                    "<Users seperated by Commas> <Content of warning " +
                    "delimited by Quotation marks (\\ )>");
            return false;
        }
        else{
            //Creates a message to send to the other players
            String message = sender.getName() + " has indicated that a " +
                    "potentially triggering scene or subject is coming up. " +
                    "Specific content warnings include:";

            //Gets the players from the first argument, puts them in a list,
            // and splits by comma
            String[] players;

            if(args[0].equalsIgnoreCase("all")){
                int playerList = listPlayers();
                players = getNames().toArray(new String[playerList]);
            }
            else {

                players = args[0].split(",");
            }
            //Makes a string builder.
            StringBuilder fullMessage = new StringBuilder();

            //Append a space to the end of the message
            fullMessage.append(message).append(" ");

            //Add the message content to the full message.
            for(int i = 1; i < args.length; i++){
                fullMessage.append(args[i]);
                fullMessage.append(" ");
            }
            //Create a loop through each of the players specified.
            for(String s: players){
                //If there are any players selected that aren't included,
                // throw an error.
                if(!getNames().contains(s)){
                    sender.sendMessage(ChatColor.RED + "Unknown player " + s);
                    return false;
                }
            }

            //Get each player, then send the full message.
            for(String s: players){
                Player a = Bukkit.getPlayer(s);
                a.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
                        fullMessage.toString());
            }

            //Send a feedback message to the original sender.
            sender.sendMessage(ChatColor.GREEN + " Message: " +
                    fullMessage.toString() + " sent to " +
                    Arrays.toString(players));
            return true;
        }
    }

    private int listPlayers() {
        int i = 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            i++;
        }
        return i;
    }

    /****
     * Gets all player names as strings, then returns the result.
     *
     * @return      All player names as strings.
     */
    private List<String> getNames(){
        List<String> playerNames = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()){
            playerNames.add(p.getName());
        }
        return playerNames;
    }
}
