package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.enchantedskies.esparticles.Shapes;

import java.util.ArrayList;
import java.util.List;

public class Helix extends Shape {

    private double angleOffset;
    private double radius;
    private double frequency;
    private double speed;
    private double waveFrequency;
    private double height;

    public Helix(Shapes shape, ConfigurationSection configurationSection) {
        super(shape, configurationSection);
        this.angleOffset = configurationSection.getDouble("angleOffset", 0D);
        this.radius = configurationSection.getDouble("radius", 0.5D);
        this.speed = configurationSection.getDouble("speed", 1);
        this.waveFrequency = configurationSection.getDouble("wave_frequency", 1);
        this.frequency = configurationSection.getDouble("frequency", 0.05);
        this.height = configurationSection.getDouble("height", 2);
    }

    @Override
    public List<Location> getWireframe(Player player) {
        Location location = player.getLocation();
        List<Location> wireframe = new ArrayList<>();
        for (double y = 0; y < height; y+=frequency) {
            double trigInput = y*waveFrequency*2*Math.PI + Bukkit.getCurrentTick()*speed;
            double xCoordinate = location.getX() + radius * Math.cos(trigInput);
            double zCoordinate = location.getZ() + radius * Math.sin(trigInput);
            double yCoordinate = y + location.getY();
            Location particleLocation = new Location(player.getWorld(), xCoordinate, yCoordinate, zCoordinate);
            wireframe.add(particleLocation);
        }
        return wireframe;
    }

    public double getAngleOffset() {
        return angleOffset;
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }
}
