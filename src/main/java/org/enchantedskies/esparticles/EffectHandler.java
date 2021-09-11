package org.enchantedskies.esparticles;

import me.creeves.particleslibrary.EffectData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EffectHandler extends BukkitRunnable {
    ESParticles esParticles;
    public EffectHandler(ESParticles esParticles) {
        this.esParticles = esParticles;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            EffectData effectData = ESParticles.dataManager.getEffect(player.getUniqueId());
            if (effectData == null || !ESParticles.dataManager.getPlayerStatus(player) || player.isInvisible()) {
                continue;
            }
            effectData.spawnEffect(player);
        }
    }
}
