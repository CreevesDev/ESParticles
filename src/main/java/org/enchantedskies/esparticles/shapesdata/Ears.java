package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.enchantedskies.esparticles.Shapes;

import java.util.ArrayList;
import java.util.List;

public class Ears extends Shape {

    private double height;
    private double frequency;
    private double size;
    private double spread;
    public Ears(Shapes shape, ConfigurationSection configurationSection) {
        super(shape, configurationSection);
        this.height = configurationSection.getDouble("height", 0.2);
        this.frequency = configurationSection.getDouble("frequency", 0.05);
        this.size = configurationSection.getDouble("size", 1);
        this.spread = configurationSection.getDouble("spread", 0.2);
    }

    @Override
    public List<Location> getWireframe(Player player) {
        Location location = player.getLocation();
        List<Location> wireframe = new ArrayList<>();
        Vector playerDirection = player.getEyeLocation().getDirection();
        Vector perpendicularVector1 = getPerpendicularVector(playerDirection);
        Vector perpendicularVector2 = getPerpendicularVector(playerDirection);
        for (double theta = 0; theta < Math.PI; theta += frequency) {
            double radius = size * Math.cos(theta);
            double horizontalOffset = radius * Math.cos(theta) + spread;
            double verticalOffset = radius * Math.sin(theta) + height + 0.5;
            Vector ear1Offset = perpendicularVector1.clone().multiply(horizontalOffset).setY(verticalOffset);
            Vector ear2Offset = perpendicularVector2.clone().multiply(-horizontalOffset).setY(verticalOffset);
            Location particleLocation = location.clone().add(ear1Offset);
            Location mirroredLocation = location.clone().add(ear2Offset);
            wireframe.add(particleLocation);
            wireframe.add(mirroredLocation);
        }
        return wireframe;
    }
}
