package org.enchantedskies.esparticles;

import org.bukkit.block.data.type.Bed;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ESParticles extends JavaPlugin {
    public static DataManager dataManager;

    @Override
    public void onEnable() {
        this.dataManager = new DataManager(this);
        EffectHandler effectHandler = new EffectHandler(this);
        effectHandler.runTaskTimer(this, 0, 4);
        PluginCommand pluginCommand = this.getCommand("particle");
        pluginCommand.setExecutor(new CommandHandler());
        pluginCommand.setTabCompleter(new CommandHandler());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
