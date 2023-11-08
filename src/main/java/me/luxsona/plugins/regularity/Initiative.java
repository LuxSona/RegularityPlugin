package me.luxsona.plugins.regularity;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;

import static net.kyori.adventure.key.Key.MINECRAFT_NAMESPACE;
import static net.kyori.adventure.key.Key.key;

/**
 * Rolls for initiative on behalf of the player.
 */
public class Initiative implements CommandExecutor {
    //Create a hash map for each OC and their initiative.
    private Map<String, Integer> initiative = new TreeMap<>();

    //Create three different styles.
    private Style errorStyle = Style.style(NamedTextColor.RED);
    private Style successStyle = Style.style(NamedTextColor.GREEN);
    private Style viewStyle = Style.style(NamedTextColor.AQUA,
            TextDecoration.BOLD);

    //Create a click style.
    private Style clickStyle = Style.style(NamedTextColor.WHITE,
            TextDecoration.UNDERLINED, ClickEvent.runCommand("/initiative " +
                    "next"));
    private int iterator = -1;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            Component text = Component.text().append(Component.text("Error: " +
                    "Missing Command", errorStyle)).build();
            sender.sendMessage(text);
            return false;
        }
        //Rolls initiative with a character.
        else if(args[0].equalsIgnoreCase("Roll")){
            //Args needs to have two parameters.
            if(args.length < 2){
                //Send the client an error message if args does not have two
                // parameters.
                Component text = Component.text().append(Component.text("Error: " +
                        "Missing Character " +
                        "Name", errorStyle)).build();
                sender.sendMessage(text);
                return false;
            }
            //If the character has two parameters, the second should be the
            // name of the character.
            int value = roll();
            String playerCharacter = ChatColor.translateAlternateColorCodes('&',
                    args[1]);
            //Success message to display to the sender.
            Component success = Component.text().append(Component.text(
                    "Success, " + playerCharacter + " rolled a " + value,
                            successStyle)).build();

            //Send the message
            sender.sendMessage(success);

            //Put the character name in initiative
            initiative.put(playerCharacter, value);
            return true;
        }
        //view the initiative map
        else if(args[0].equalsIgnoreCase("View")){
            boolean admode = false;
            //Make the header
            Component header = Component.text().append(Component.text(
                    "-=-=-=-=[Initiative]=-=-=-=-", viewStyle)).build();

            //Make the next turn tab
            Component next = Component.text().append(Component.text(
                    ">> Next " + "Turn >>", clickStyle)).build();

            //Make the footer.
            Component footer = Component.text().append(Component.text(
                    "-=-=-=-=~~~=-=-=-=-", viewStyle)).build();

            if(args.length >= 2){
                //If the admin flag (-a) is used:
                if(args[1].equalsIgnoreCase("-a")){
                    admode = true;

                }
            }
            //Send all these messages to the user
            sender.sendMessage(header);
            sender.sendMessage(viewInitiative());
            sender.sendMessage(footer);
            if(admode){
                sender.sendMessage(next);
            }
            return true;
        }

        //Next turn handler.
        else if(args[0].equalsIgnoreCase("Next")){

            //Return an error if the keyset is empty.
            if(initiative.keySet().isEmpty()) {
                sender.sendMessage(ChatColor.RED + "Initiative is empty!");
                return false;
            }

            //Assign the iterator a 0 value.
            iterator = (iterator+1)%initiative.keySet().size();

            //Create a string list of players.
            ArrayList<String> players =
                    new ArrayList<String>(initiative.keySet());
            players.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return Integer.compare(initiative.get(o2),
                            initiative.get(o1));
                }
            });
            //Get the current turn's character
            String currentTurn = players.get(iterator);

            //Make a turn message.
            Component turnMessage = Component.text().append(Component.text(
                    "It is now " + currentTurn + "'s turn!", viewStyle)).build();

            //Send this message to every player and play a sound near them
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(turnMessage);
                Player player = (Player) p;
                Sound sound = Sound.sound(Key.key("entity.experience_orb.pickup"),
                        Sound.Source.PLAYER, 1f, 1f);
                player.playSound(sound, Sound.Emitter.self());
            }
            return true;
        }
        //Clear all players in the initiative.
        else if(args[0].equalsIgnoreCase("Clear")){
            clearInitiative();
            sender.sendMessage(ChatColor.AQUA + "All players cleared");
            return true;
        }

        //Remove a specific player from the initiative roll.
        else if(args[0].equalsIgnoreCase("Remove")){
            if(args.length < 2){
                sender.sendMessage(ChatColor.RED + "Specify a character to " +
                        "remove.");
                return false;
            }
            if(initiative.keySet().contains(args[1])){
                initiative.remove(args[1]);
                sender.sendMessage(ChatColor.AQUA + "Removed!");
                return true;
            }
            else{
                sender.sendMessage(ChatColor.RED + "That character is not in " +
                        "your list. Character names are case sensitive!");
                return false;
            }
        }
        return false;
    }

    /**
     * Views the initiative and returns a component for it.
     *
     * @return the initiative.
     */
    private String viewInitiative() {
        StringBuilder initiativeRow = new StringBuilder();
        ArrayList<String> playerNames = new ArrayList<>(initiative.keySet());
        playerNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(initiative.get(o2),
                        initiative.get(o1));
            }
        });
        for (String name:
                playerNames) {
            String row = name + "...." + initiative.get(name) + "\n";
            initiativeRow.append(row);
        }
        return initiativeRow.toString();
    }

    /**
     * Clears the initiative roll.
     */
    public void clearInitiative(){
        initiative.clear();
        iterator = -1;
    }

    /**
     * Rolls for you.
     * @return a random int value between 1 and 20.
     */
    public int roll(){
        return (int) (Math.random()*20)+1;
    }

}
