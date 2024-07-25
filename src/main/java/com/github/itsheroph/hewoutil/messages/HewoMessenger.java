package com.github.itsheroph.hewoutil.messages;

import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class HewoMessenger extends HewoMessengerAbstract {

    public HewoMessenger(HewoPlugin plugin, HewoLogger logger, Map<String, String> messages) {

        super(plugin, logger, messages);

    }

    public HewoMessenger(HewoPlugin plugin, HewoLogger logger, String prefix, Map<String, String> messages) {

        super(plugin, logger, prefix, messages);

    }

    public void sendMessage(List<Player> receivers, String messageID, HewoMsgEntry... replacements) {

        for(Player receiver : receivers) {

            this.sendMessage(receiver, messageID, replacements);

        }

    }

    public void sendMessage(Player receiver, String messageID, HewoMsgEntry... replacements) {

        String message = this.composeMessage(messageID, replacements);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(List<Player> receivers, String messageID, boolean includePrefix, HewoMsgEntry... replacements) {

        for(Player receiver : receivers) {

            this.sendMessage(receiver, messageID, includePrefix, replacements);

        }

    }

    public void sendMessage(Player receiver, String messageID, boolean includePrefix, HewoMsgEntry... replacements) {

        String message = this.composeMessage(messageID, includePrefix, replacements);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(List<Player> receivers, String messageID, boolean includePrefix) {

        for(Player receiver : receivers) {

            this.sendMessage(receiver, messageID, includePrefix);

        }

    }

    public void sendMessage(Player receiver, String messageID, boolean includePrefix) {

        String message = this.composeMessage(messageID, includePrefix);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(Player receiver, String message) {

        receiver.sendMessage(message);

    }
}
