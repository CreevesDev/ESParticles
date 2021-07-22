package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.enchantedskies.esparticles.Shapes;

import java.util.ArrayList;
import java.util.List;

public class FairyWings extends Shape {
    private double height;
    private double frequency;
    private double size;
    public FairyWings(Shapes shape, ConfigurationSection configurationSection) {
        super(shape, configurationSection);
        this.height = configurationSection.getDouble("height", 1);
        this.frequency = configurationSection.getDouble("frequency", 0.05);
        this.size = configurationSection.getDouble("size", 1);
    }

    @Override
    public List<Location> getWireframe(Player player) {
        Location location = player.getLocation();
        List<Location> wireframe = new ArrayList<>();
        Vector playerDirection = player.getEyeLocation().getDirection();
        //float yaw = player.getEyeLocation().getYaw(); //Subtracted 180 so it is negative half the time (-180, 180)
        //double rotationAngle = Bukkit.getCurrentTick()/20 % Math.PI/3 * Math.signum(-yaw);
        //System.out.println(playerDirection.setY(0).angle(getPerpendicularVector(playerDirection)));
        Vector perpendicularVector1 = getPerpendicularVector(playerDirection);
        Vector perpendicularVector2 = getPerpendicularVector(playerDirection);
        for (double theta = 0; theta < Math.PI; theta += frequency) {
            double radius = Math.sin(2*theta);
            double horizontalOffset = radius * Math.cos(theta) * size;
            double verticalOffset = radius * Math.sin(theta) * size + height;
            Vector wing1Offset = perpendicularVector1.clone().multiply(horizontalOffset).setY(verticalOffset);
            Vector wing2Offset = perpendicularVector2.clone().multiply(-horizontalOffset).setY(verticalOffset);
            Vector parallelOffset = playerDirection.clone().normalize().multiply(0.35).setY(0);
            Location particleLocation = location.clone().add(wing1Offset).subtract(parallelOffset);
            Location mirroredLocation = location.clone().add(wing2Offset).subtract(parallelOffset);
            wireframe.add(particleLocation);
            wireframe.add(mirroredLocation);
        }
        return wireframe;
    }


}
