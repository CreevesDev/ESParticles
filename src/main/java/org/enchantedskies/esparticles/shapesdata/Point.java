package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.enchantedskies.esparticles.Shapes;

import java.util.ArrayList;
import java.util.List;

public class Point extends Shape {
    private double spread;
    private double yOffset;

    public Point(Shapes shape, ConfigurationSection configurationSection) {
        super(shape, configurationSection);
        spread = configurationSection.getDouble("spread", 0.5);
        yOffset = configurationSection.getDouble("offset", 0);
    }

    @Override
    public List<Location> getWireframe(Player player) {
        Location location = player.getLocation();
        List<Location> wireframe = new ArrayList<>();
        double xCoordinate = location.getX() + (2 * spread * Math.random() - spread) - 0.2;
        double yCoordinate = location.getY() + (2 * spread * Math.random() - spread) + yOffset;
        double zCoordinate = location.getZ() + (2 * spread * Math.random() - spread) - 0.2;
        Location particleLocation = new Location(player.getWorld(), xCoordinate, yCoordinate, zCoordinate);
        wireframe.add(particleLocation);
        return wireframe;
    }

    public double getSpread() {
        return spread;
    }
}
