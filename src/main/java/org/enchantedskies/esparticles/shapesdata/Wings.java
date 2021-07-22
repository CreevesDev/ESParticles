package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.enchantedskies.esparticles.Shapes;

import java.util.List;

public class Wings extends Shape {
    public Wings(Shapes shape, ConfigurationSection configurationSection) {
        super(shape, configurationSection);
    }

    @Override
    public List<Location> getWireframe(Player player) {
        return null;
    }
}
