package com.github.itsheroph.hewoutil.messages.logging;

import org.bukkit.Bukkit;

public class HewoLogger {

    private final String prefix;

    public HewoLogger() {

        this("HewoPlugin");

    }

    public HewoLogger(String prefix) {

        this.prefix = prefix;

    }

    public void error(boolean includePrefix, String... messages) {

        for(String message : messages) {

            this.error(includePrefix, message);

        }

    }

    public void error(boolean includePrefix, String message) {

        this.log(includePrefix, "&c" + message);

    }

    public void error(String... messages) {

        for(String message : messages) {

            this.error(message);

        }

    }

    public void error(String message) {

        this.log("&c" + message);

    }

    public void warning(boolean includePrefix, String... messages) {

        for(String message : messages) {

            this.warning(includePrefix, message);

        }

    }

    public void warning(boolean includePrefix, String message) {

        this.log(includePrefix, "&e" + message);

    }

    public void warning(String... messages) {

        for(String message : messages) {

            this.warning(message);

        }

    }

    public void warning(String message) {

        this.log("&e" + message);

    }

    public void log(String... messages) {

        this.log(true, messages);
    }

    public void log(String message) {

        this.log(true, message);

    }

    public void log(boolean includePrefix, String... messages) {

        for(String message : messages) {

            this.log(includePrefix, message);

        }
    }

    public void log(boolean includePrefix, String message) {

        message = message.replaceAll("&", "§");

        if(includePrefix) {

            message = "[" + this.prefix + "] " + message;

        }

        message = message.replaceAll("&", "§");

        Bukkit.getConsoleSender().sendMessage(message);

    }
}
