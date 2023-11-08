package me.luxsona.plugins.regularity;

import me.luxsona.plugins.regularity.events.DoubleDiamond;
import org.bukkit.plugin.java.JavaPlugin;

public final class Regularity extends JavaPlugin {
    Boop boopCommand = new Boop();
    Rbossbar rbossbar = new Rbossbar();
    TriggerWarn triggerWarn = new TriggerWarn();
    Initiative initiative = new Initiative();
    DiceRoll diceRoll = new DiceRoll();
    @Override
    public void onEnable() {

        System.out.println("Hello, world.");

        //Enable all our commands
        this.getCommand("boop").setExecutor(boopCommand);
        this.getCommand("rbossbar").setExecutor(rbossbar);
        this.getCommand("triggerwarn").setExecutor(triggerWarn);
        this.getCommand("initiative").setExecutor(initiative);
        this.getCommand("DiceRoll").setExecutor(diceRoll);


        //Enable all our events
        getServer().getPluginManager().registerEvents(new DoubleDiamond(),
                this);
    }

    @Override
    public void onDisable() {
        rbossbar.clear();
        initiative.clearInitiative();
        // Plugin shutdown logic
    }
}
