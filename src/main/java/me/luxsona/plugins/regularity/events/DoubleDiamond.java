package me.luxsona.plugins.regularity.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleDiamond implements Listener {
    /**
     * Drop two diamonds when a player mines a diamond ore block with a
     * 50% chance.
     */

    @EventHandler
    public void onMineDiamondOre(BlockBreakEvent block, Player player){
        if(block.getBlock().getType() == Material.DIAMOND_ORE){
            if(Math.random() < 0.5){
                block.getBlock().getWorld().dropItemNaturally(block.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 2));
                player.playSound(player.getLocation(), "minecraft:block.note_block.pling", 1, 1);
            }
        }
    }
}
