package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.enchantedskies.esparticles.Shapes;

import java.util.ArrayList;
import java.util.List;

public class Wisp extends Shape {

    private double angleOffset;
    private double radius;
    private double frequency;
    private double speed;
    private double waveFrequency;
    private double height;

    public Wisp(Shapes shape, ConfigurationSection configurationSection) {
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
        double trigInput = (Bukkit.getCurrentTick()*speed);
        double xCoordinate = location.getX() + radius * Math.cos(trigInput);
        double zCoordinate = location.getZ() + radius * Math.sin(trigInput);
        double cyclePosition = (Math.sin(Bukkit.getCurrentTick() * waveFrequency) + 1)/2;
        double yCoordinate = cyclePosition*height + location.getY();
        Location particleLocation = new Location(player.getWorld(), xCoordinate, yCoordinate, zCoordinate);
        wireframe.add(particleLocation);
        return wireframe;
    }
}
