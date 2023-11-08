package me.luxsona.plugins.regularity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Infection implements CommandExecutor {
    ArrayList<Player> infectedPlayers = new ArrayList<>();
    int sq = 10*10;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Specify a player to infect.");
            return false;
        }
        else if(args[0].equalsIgnoreCase("step")){
            for (Player i:
                 infectedPlayers) {
                if(Bukkit.getOnlinePlayers().contains(i)){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(!infectedPlayers.contains(p)) {
                            if (p.getLocation().distanceSquared(i.getLocation()) <= sq) {
                                if (Math.random() * 10 >= 5) {
                                    infectedPlayers.add(p);
                                    sender.sendMessage("Player " + p.getName() +
                                            " has been infected by " + i.getName());
                                    return true;
                                }
                                sender.sendMessage("Infection in contact. No " +
                                        "infections yet.");
                            }
                        }
                    }
                }
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("view")){
            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.RED + "Infected Players: ");
            for (Player p:
                 infectedPlayers) {
                sb.append(ChatColor.GOLD).append(p.getName());
                sb.append(" ");
            }
            sender.sendMessage(sb.toString());
            return true;
        }
        else if(args[0].equalsIgnoreCase("random")){
            int random = (int) (Math.random()*Bukkit.getOnlinePlayers().size());
            int sum = 0;
            for (Player p:
                 Bukkit.getOnlinePlayers()) {
                if(sum == random){
                    infectedPlayers.add(p);
                    break;
                }
                sum++;
            };
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Let the" +
                    " infection begin~");
            return true;
        }
        else if(args[0].equalsIgnoreCase("clear")){
            infectedPlayers.clear();
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "All " +
                    "clear~!");
            return true;
        }
        else if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return false;
        }
        else{
            Player infected = Bukkit.getPlayer(args[0]);
            infectedPlayers.add(infected);
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Let the" +
                    " infection begin~");
            return true;
        }
    }

    public void clean(){
        infectedPlayers.clear();
    }
}
