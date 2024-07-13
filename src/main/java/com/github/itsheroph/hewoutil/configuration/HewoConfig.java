package com.github.itsheroph.hewoutil.configuration;

import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginAwareness;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class HewoConfig {

    private final HewoPlugin plugin;
    private final String resource;
    private final String path;
    private final File file;
    private YamlConfiguration config = null;

    public HewoConfig(HewoPlugin plugin, String path) {

        this(plugin, path, path);

    }

    public HewoConfig(HewoPlugin plugin, String resource, String path) {

        this.plugin = plugin;
        this.resource = resource;
        this.path = path;

        this.file = new File(plugin.getDataFolder(), path);

    }

    public HewoPlugin getPlugin() {

        return this.plugin;

    }

    public File getFile() {

        return this.file;

    }

    public YamlConfiguration getConfig() {

        if(this.config == null) {

            this.reloadConfig();

        }

        return this.config;

    };

    public void saveDefaultConfig() {

        if(!this.getFile().exists()) {

            this.saveResource();

        }

    }

    public void reloadConfig() {

        this.config = YamlConfiguration.loadConfiguration(this.getFile());
        InputStream defaultConfigStream = this.getResource();

        if(defaultConfigStream == null) return;

        YamlConfiguration defConfig;

        if(this.getPlugin().getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8)) {

            defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, Charsets.UTF_8));

        } else {

            byte[] contents;
            defConfig = new YamlConfiguration();

            try {

                contents = ByteStreams.toByteArray(defaultConfigStream);

            } catch(IOException error) {

                this.getPlugin().getPluginLogger().error("Unexpected failure reading " + this.getFile().getName(), error.getMessage());
                return;

            }

            final String text = new String(contents, Charset.defaultCharset());

            if(!text.equals(new String(contents, Charsets.UTF_8))) {

                this.getPlugin().getPluginLogger().warning("Default system encoding may have misread " + this.getFile().getName() + " from the plugin " + this.getPlugin().getName());

            }

            try {

                defConfig.loadFromString(text);

            } catch(InvalidConfigurationException error) {

                this.getPlugin().getPluginLogger().warning("Cannot load configuration from " + this.getPlugin().getName() + ".jar");

            }
        }

        this.config.setDefaults(defConfig);

    }

    public void saveConfig() {

        try {

            this.getConfig().save(this.getFile());

        } catch (IOException e) {

            this.getPlugin().getPluginLogger().warning(e.getMessage());

        }

    }

    public InputStream getResource() {

        URL url = this.getClass().getClassLoader().getResource(this.resource);

        try {

            if(url == null) {

                return null;

            } else {

                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);

                return connection.getInputStream();

            }

        } catch (IOException e) {

            return null;

        }
    }

    private void saveResource() {

        InputStream input = this.getResource();

        if(input == null) {

            this.getPlugin().getPluginLogger().error("The embeded resource " + this.resource + " cannot found in " + this.getPlugin().getName() + ".jar");

        } else {

            File outFile = new File(this.getPlugin().getDataFolder(), this.path);
            File outDir = new File(outFile.getParent());

            if(!outDir.exists()) {

                outDir.mkdirs();

            }

            try {

                if(outFile.exists()) {

                    this.getPlugin().getPluginLogger().warning("Could not save the " + outFile.getName() + " to " + outFile.toString() + " because it is already exists");

                } else {

                    OutputStream output = Files.newOutputStream(outFile.toPath());
                    byte[] buffer = new byte[1024];

                    int len;

                    while((len = input.read(buffer)) > 0) {

                        output.write(buffer, 0, len);

                    }

                    output.close();
                    input.close();

                }

            } catch (IOException e) {

               this.getPlugin().getPluginLogger().error("Could not save the " + outFile.getName() + " to " + outFile.toString());

            }
        }
    }
}
