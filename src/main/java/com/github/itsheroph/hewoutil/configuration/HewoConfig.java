package com.github.itsheroph.hewoutil.configuration;

import com.github.itsheroph.hewoutil.configuration.yaml.HewoYamlReader;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Map.Entry;

public class HewoConfig {

    private final HewoPlugin plugin;
    private final String resource;
    private final File file;
    private YamlConfiguration config = null;

    public HewoConfig(HewoPlugin plugin, String path) {

        this(plugin, path, path);

    }

    public HewoConfig(HewoPlugin plugin, String resource, String path) {

        this(plugin, resource, new File(path));

    }

    public HewoConfig(HewoPlugin plugin, String resource, File file) {

        this.plugin = plugin;
        this.resource = resource;
        this.file = file;

    }

    public HewoPlugin getPlugin() {

        return this.plugin;

    }

    public File getFile() {

        return this.file;

    }

    public YamlConfiguration getConfig() {

        if(this.config == null) {

            try {

                this.reloadConfig();

            } catch (IOException e) {

                this.getPlugin().getPluginLogger().error(e.getMessage());

            }

        }

        return this.config;

    }

    public void saveConfig() {

        String data = this.getConfig().saveToString();
        try {

            BufferedWriter writer = Files.newBufferedWriter(this.getFile().toPath());

            try {

                writer.write(data);

            } finally {

                writer.close();

            }


        } catch (IOException e) {

            this.getPlugin().getPluginLogger().error(e.getMessage());

        }

    }

    public void reloadConfig() throws IOException {

        if(!this.getFile().exists()) {

            HewoResource.saveResource(this.resource, this.getFile().getPath(), this.getPlugin().getPluginLogger());

        } else {

            InputStream defaultConfigStream = HewoResource.getResource(this.resource, this.getPlugin().getPluginLogger());
            int differences = this.getDifferences(defaultConfigStream);

            if(differences > 0) {

                this.getPlugin().getPluginLogger().warning("We found " + differences + " missing option to your " + this.getFile().getName() + ". Reformatting the file...");

                YamlConfiguration currentConfig = YamlConfiguration.loadConfiguration(this.getFile());

                this.getFile().delete();
                HewoResource.saveResource(this.resource, this.getFile().getPath(), this.getPlugin().getPluginLogger());

                YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(this.getFile());

                for(String path : currentConfig.getKeys(true)) {

                    if(newConfig.contains(path)) {

                        newConfig.set(path, currentConfig.get(path));

                    }

                }

                newConfig.save(this.getFile());

            }

        }

        this.config = YamlConfiguration.loadConfiguration(this.getFile());

    }

    private int getDifferences(InputStream defaultConfigStream) throws IOException {

        Map<String, Object> defaultContent = new HewoYamlReader(defaultConfigStream).getContents();
        Map<String, Object> currentContent = new HewoYamlReader(this.getFile()).getContents();

        int differences = 0;

        for(Entry<String, Object> entry : defaultContent.entrySet()) {

            if(!currentContent.containsKey(entry.getKey())) {

                differences = differences + 1;

            }

        }
        return differences;

    }
}
