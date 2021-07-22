package org.enchantedskies.esparticles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabExecutor {
    private String playerOnly;
    private String selectErrorMessage = ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7/particles select &c<Particle Name>");
    private String successfulSelection = ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7You have successfully set your particle!");
    private String noEffectPermission = ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7You do not own this particle!");

    public CommandHandler() {
        this.playerOnly = ChatColor.translateAlternateColorCodes('&', "&7This command can only be used by players.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.playerOnly);
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            Bukkit.dispatchCommand(player, "bossshoppro:bossshop ParticleShop");
        }
        String commandToRun = args[0].toLowerCase();
        if (commandToRun.equals("select")) {
            if (args.length < 2) {
                player.sendMessage(selectErrorMessage);
                return true;
            }
            String particleName = args[1];
            EffectData effectData = ESParticles.dataManager.getEffect(particleName.toLowerCase());
            if (effectData == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7That particle does not exist."));
            }
            if (player.hasPermission("particles.effect." + particleName)) {
                ESParticles.dataManager.setEffect(player, effectData);
                return true;
            }
            else {
                player.sendMessage(noEffectPermission);
            }

        }
        if (commandToRun.equals("reload")) {
            if (player.hasPermission("particles.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &aStarting reload..."));
                ESParticles.dataManager.load();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &aDone!"));
                return true;
            }
        }
        if (commandToRun.equals("toggle")) {
            EffectData effectData = ESParticles.dataManager.getPlayerEffect(player);
            boolean newStatus = !ESParticles.dataManager.getPlayerStatus(player);
            if (effectData == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7You do not have a particle selected."));
                return true;
            }
            ESParticles.dataManager.setEffectStatus(player, newStatus);
            String toggleMessage = ChatColor.translateAlternateColorCodes('&', "&a&lParticles &8» &7Your particle has been turned ");
            if (newStatus) {
                player.sendMessage(toggleMessage + "on");
                return true;
            }
            player.sendMessage(toggleMessage + "off");
            return true;
        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (args.length == 1) {
            tabComplete.add("select");
            tabComplete.add("reload");
            tabComplete.add("toggle");
            return tabComplete;
        }
        if (args[0].toLowerCase().equals("select")) {
            if (args.length == 2) {
                String firstArg = args[1];
                for (String effectName : ESParticles.dataManager.getEffectNames()) {
                    if (effectName.startsWith(firstArg) && sender.hasPermission("particles.effect." + effectName.toLowerCase())) {
                        tabComplete.add(effectName);
                    }
                }
                return tabComplete;
            }
        }
        return null;
    }
}
