package me.luxsona.plugins.regularity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Creates a publically available notepad for all players to see and use.
 * @author Luxsona
 * @version 1.0
 * @since 1.0
 * @see CommandExecutor
 */
public class Notepad implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Create a linked list to store the notepad's contents.
        LinkedList<String> notepad = new LinkedList<>();

        //Process the args to determine what the sender wishes.
        processArgs(args, notepad);
        return false;
    }

    private void processArgs(String[] args, LinkedList<String> notepad) {
        /*
        //Options: Add, Remove, Clear, Edit.
        for (String s : args) {
            switch(s){
                case "Add":
                case "add":
                    //We're adding a line.
                    addLine(notepad, s);
                    break;
                case "remove":
                    //Check if the next arg is a number.
            }
        }
    }
    }
         */
    }
}