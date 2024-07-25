package com.github.itsheroph.hewoutil.configuration;

import com.github.itsheroph.hewoutil.plugin.HewoPlugin;

import java.util.HashMap;
import java.util.Map;

public class HewoLang {

    private final HewoConfig config;

    public HewoLang(HewoPlugin plugin, String path) {

        this(plugin, path, path);

    }

    public HewoLang(HewoPlugin plugin, String resource, String path) {

        this.config = new HewoConfig(plugin, resource, path);
        this.config.getConfig().options().copyDefaults(true);

    }

    public Map<String, String> getMessages() {

        Map<String, String> messages = new HashMap<>();

        for(String path : this.config.getConfig().getKeys(true)) {

            String message = this.config.getConfig().getString(path);

            if(message != null && !this.config.getConfig().isConfigurationSection(path)) {

                messages.put(path, message);

            }

        }

        return messages;

    }
}
