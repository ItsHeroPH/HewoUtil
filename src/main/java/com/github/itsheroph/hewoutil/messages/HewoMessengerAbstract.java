package com.github.itsheroph.hewoutil.messages;

import com.github.itsheroph.hewoutil.configuration.HewoConfig;
import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class HewoMessengerAbstract {

    private final Map<String, String> messages;
    private final HewoLogger logger;
    private final String prefix;

    public HewoMessengerAbstract(HewoPlugin plugin, HewoLogger logger) {

        this(plugin, logger, "HewoPlugin");

    }

    public HewoMessengerAbstract(HewoPlugin plugin, HewoLogger logger, String prefix) {

        this.messages = new HashMap<>();
        this.logger = logger;
        this.prefix = prefix;
        HewoConfig lang = new HewoConfig(plugin, "lang.yml");
        lang.getConfig().options().copyDefaults(true);
        lang.saveDefaultConfig();

        for(Entry<String, Object> key : lang.getConfig().getValues(false).entrySet()) {

            this.getMessages().put(key.getKey(), (String) key.getValue());

        }

    }

    public Map<String, String> getMessages() {

        return this.messages;

    }

    public String getPrefix() {

        return this.prefix;

    }

    public HewoLogger getLogger() {

        return this.logger;

    }

    @Nullable
    public String composeMessage(String messageID, HewoMsgEntry... replacements) {

        return this.composeMessage(messageID, true, replacements);
    }

    @Nullable
    public String composeMessage(String messageID, boolean includePrefix,  HewoMsgEntry... replacements) {

        String message = this.composeMessage(messageID, includePrefix);

        if(message == null) return null;

        for(HewoMsgEntry replacement : replacements) {

            message = message.replace(replacement.getKey(), replacement.getReplacement());

        }

        return message;

    }

    @Nullable
    public String composeMessage(String messageID) {

        return this.composeMessage(messageID, true);

    }

    @Nullable
    public String composeMessage(String messageID, boolean includePrefix) {

        String message = this.getMessages().getOrDefault(messageID, null);
        String prefixFormat = this.getMessages().getOrDefault("message_prefix_format", null);

        if(prefixFormat == null) {

            this.getLogger().error("Missing language option &4&omessage_prefix_format. &cConsider adding it on your lang.yml");
            return "";

        }

        if(message == null) {

            this.getLogger().error("Missing language option &4&o" + messageID + ". &cConsider adding it on your lang.yml");
            return "";

        }

        if(message.isEmpty() || message.equals("ignore") || message.equals("ignored")) {

            return null;

        }

        if(includePrefix && !(prefixFormat.isEmpty() || prefixFormat.equals("ignore") || prefixFormat.equals("ignored"))) {

            prefixFormat = prefixFormat.replace("<prefix>", this.getPrefix());

            message = prefixFormat + " &r" + message;

        }

        message = message.replaceAll("&", "§");

        return message;
    }
}
