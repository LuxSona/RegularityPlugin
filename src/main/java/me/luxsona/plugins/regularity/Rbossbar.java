package me.luxsona.plugins.regularity;

import com.sun.org.apache.xpath.internal.operations.Number;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Rbossbar implements CommandExecutor {
    ArrayList<BossBar> bossBars = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 1 ){
            sender.sendMessage(ChatColor.RED + "What do you want to do with " +
                    "your bar? Create? Damage? Remove?");
            return false;
        }
        else if(args[0].equalsIgnoreCase("create")){
            if(args.length == 1){
                sender.sendMessage(ChatColor.RED + "What is the name of the " +
                        "boss you want to create?");
                return false;
            }
            else if(!bossBars.isEmpty()){
                sender.sendMessage(ChatColor.RED + "Multiple boss bars are " +
                        "not supported yet.");
                return false;
            }
            else{
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    if(i == args.length-1){
                        sb.append(args[i]);
                    }
                    else {
                        sb.append(args[i] + " ");
                    }
                }
                String message =
                        ChatColor.translateAlternateColorCodes('&', sb.toString());

                BossBar b = Bukkit.createBossBar(
                        message,
                        BarColor.RED,
                        BarStyle.SOLID);
                bossBars.add(b);
                for(Player p : Bukkit.getOnlinePlayers()){
                    b.addPlayer(p);
                }
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("damage")){
            if(args.length == 1){
                sender.sendMessage(ChatColor.RED + " How much damage from " +
                        "0-100?");
                return false;
            }
            else if(bossBars.isEmpty()){
                sender.sendMessage(ChatColor.RED + " No bossbars created.");
                return false;
            }
            else{
                try{
                    int damage = Integer.parseInt(args[1]);

                    if(damage <= 0 || damage > 100){
                        sender.sendMessage(ChatColor.RED + "Damage must be " +
                                "between 0 and 100");
                        return false;
                    }

                    else{
                        BossBar b = bossBars.get(0);
                        if(b.getProgress()-(double)damage/100 == 0){
                            b.removeAll();
                            bossBars.remove(b);
                        }
                        else if(b.getProgress()-(double)damage/100 <= 0){
                            b.setProgress(0);
                            b.removeAll();
                            bossBars.remove(b);
                        }
                        b.setProgress(b.getProgress()-(double)damage/100);
                        return true;
                    }
                }
                catch(NumberFormatException e){
                    sender.sendMessage(ChatColor.RED + "Damage is not a " +
                            "number.");
                    return false;
                }
            }
        }
        else if(args[0].equalsIgnoreCase("remove")){
            sender.sendMessage(ChatColor.DARK_PURPLE + "Removing...");
            clear();
            sender.sendMessage(ChatColor.AQUA + "Complete!");
            return true;
        }
        return false;
    }

    public void clear(){
        for (BossBar b:
             bossBars) {
            b.removeAll();
        }
        bossBars.clear();
    }
}
