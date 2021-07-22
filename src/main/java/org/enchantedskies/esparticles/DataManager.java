package org.enchantedskies.esparticles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {
    private ESParticles ESParticles;
    private File pluginDirectory;
    private File effectsFolder;
    private YamlConfiguration playerYAML;
    private YamlConfiguration effectsYAML;
    private YamlConfiguration configYAML;
    private File playerFile;
    private HashMap<String, EffectData> nameToEffectData;
    private HashMap<UUID, EffectData> uuidToEffectData = new HashMap<>();
    private HashMap<UUID, Boolean> uuidToStatus = new HashMap<>();

    public DataManager(ESParticles ESParticles) {
        this.ESParticles = ESParticles;
        this.pluginDirectory = ESParticles.getDataFolder();
        load();
    }

    public void load() {
        this.pluginDirectory.mkdir();
        this.effectsFolder = new File(this.pluginDirectory, "/effects/");
        this.effectsFolder.mkdir();
        configYAML = YamlConfiguration.loadConfiguration(getFile("config.yml"));
        effectsYAML = YamlConfiguration.loadConfiguration(getFile("effects.yml"));
        playerFile = getFile("data.yml");
        playerYAML = YamlConfiguration.loadConfiguration(playerFile);
        nameToEffectData = loadEffects();
        loadPlayerEffects();
    }

    private File getFile(String fileName) {
        File file = new File(this.pluginDirectory, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    private HashMap<String, EffectData> loadEffects() {
        HashMap<String, EffectData> nameToEffectData = new HashMap<>();
        for (Map.Entry<String, Object> item : effectsYAML.getValues(false).entrySet()) {
            if (item.getValue() instanceof ConfigurationSection) {
                String name = item.getKey();
                EffectData effectData = new EffectData(name, (ConfigurationSection) item.getValue());
                nameToEffectData.put(name, effectData);
            }
        }
        return nameToEffectData;
    }

    private void loadPlayerEffects() {
        for (Map.Entry<String, Object> item : playerYAML.getValues(false).entrySet()) {
            if (item.getValue() instanceof ConfigurationSection) {
                UUID uuid = UUID.fromString(item.getKey());
                ConfigurationSection effectSection = (ConfigurationSection) item.getValue();
                String effectName = effectSection.getString("effect");
                boolean on = effectSection.getBoolean("on");
                EffectData effectData = nameToEffectData.get(effectName);
                uuidToEffectData.put(uuid, effectData);
                uuidToStatus.put(uuid, on);
            }
        }
    }

    public EffectData getPlayerEffect(Player player) {
        return uuidToEffectData.get(player.getUniqueId());
    }

    public boolean getPlayerStatus(Player player) {
        return uuidToStatus.getOrDefault(player.getUniqueId(), true);
    }

    public void setEffect(Player player, EffectData effectData) {
        uuidToEffectData.put(player.getUniqueId(), effectData);
        savePlayer(player, effectData, true);
    }

    public void setEffectStatus(Player player, boolean on) {
        UUID uuid = player.getUniqueId();
        uuidToStatus.put(uuid, on);
        savePlayer(player, getEffect(uuid), on);
    }

    public void savePlayer(Player player, EffectData effectData, boolean on) {
        ConfigurationSection effectSection = playerYAML.createSection(String.valueOf(player.getUniqueId()));
        effectSection.set("effect", effectData.getName());
        effectSection.set("on", on);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    playerYAML.save(playerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(ESParticles);
    }

    public List<String> getEffectNames() {
        List<String> effectNames = new ArrayList<>();
        for (String name : nameToEffectData.keySet()) {
            effectNames.add(name);
        }
        return effectNames;
    }

    public EffectData getEffect(String effectName) {
        return nameToEffectData.get(effectName);
    }


    public YamlConfiguration getEffectsYAML() {
        return effectsYAML;
    }

    public EffectData getEffect(UUID uuid) {
        return uuidToEffectData.get(uuid);
    }
}
