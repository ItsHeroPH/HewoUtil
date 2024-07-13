package com.github.itsheroph.hewoutil.plugin;

import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class HewoPlugin extends JavaPlugin {

    private final HewoLogger pluginLogger;

    public HewoPlugin(HewoLogger pluginLogger) {

        this.pluginLogger = pluginLogger;

    }

    public HewoLogger getPluginLogger() {

        return pluginLogger;

    }
}
