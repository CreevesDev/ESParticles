package org.enchantedskies.esparticles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class EffectData {
    private final List<ParticleData> particles = new ArrayList<>();
    private String name;

    public EffectData(String name, ConfigurationSection configurationSection) {
        this.name = name;
        for (Object item : configurationSection.getValues(false).values()) {
            if (item instanceof ConfigurationSection) {
                ConfigurationSection particleSection = (ConfigurationSection) item;
                ParticleData particle = new ParticleData(particleSection);
                particles.add(particle);
            }
        }
    }

    public void spawnEffect(Player player) {
        for (ParticleData particle : particles) {
            particle.spawnParticle(player);
        }
    }

    public String getName() {
        return name;
    }

    public List<ParticleData> getParticles() {
        return particles;
    }
}
