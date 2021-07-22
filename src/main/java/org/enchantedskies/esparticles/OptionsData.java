package org.enchantedskies.esparticles;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class OptionsData {
    private List<Color> colours = new ArrayList<>();
    private float scale;
    public OptionsData(ConfigurationSection configurationSection) {
        ConfigurationSection coloursSection = configurationSection.getConfigurationSection("colours");
        for (Object item : coloursSection.getValues(false).values()) {
            if (item instanceof ConfigurationSection) {
                ConfigurationSection colourSection = (ConfigurationSection) item;
                int red = colourSection.getInt("red", 0);
                int green = colourSection.getInt("green", 0);
                int blue = colourSection.getInt("blue", 0);
                colours.add(Color.fromRGB(red, green, blue));
            }
        }
        scale = (float) configurationSection.getDouble("scale", 1);
    }

    public float getScale() {
        return scale;
    }

    public List<Color> getColours() {
        return colours;
    }
}
