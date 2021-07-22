package org.enchantedskies.esparticles.shapesdata;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.enchantedskies.esparticles.Shapes;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Shape {
    private Shapes shape;
    private ConfigurationSection configurationSection;

    public Shape(Shapes shape, ConfigurationSection configurationSection) {
        this.shape = shape;
        this.configurationSection = configurationSection;
    }

    public Shape() {

    }

    public abstract List<Location> getWireframe(Player player);

    public static Shape create(Shapes shape, ConfigurationSection configurationSection) {
        try {
            return shape.getShapeClass().getConstructor(Shapes.class, ConfigurationSection.class).newInstance(shape, configurationSection);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Shapes getShape() {
        return shape;
    }

    public ConfigurationSection getConfigurationSection() {
        return configurationSection;
    }

    public Vector getPerpendicularVector(Vector directionVector) {
        double x = ((directionVector.getZ() * 1)) / (directionVector.getX() * -1);
        Vector perpendicularVector = new Vector(x, 0, 1).normalize();
        return perpendicularVector;
    }
}
