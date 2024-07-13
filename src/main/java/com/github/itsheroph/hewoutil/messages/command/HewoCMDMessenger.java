package com.github.itsheroph.hewoutil.messages.command;

import com.github.itsheroph.hewoutil.messages.HewoMessengerAbstract;
import com.github.itsheroph.hewoutil.messages.HewoMsgEntry;
import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HewoCMDMessenger extends HewoMessengerAbstract {

    public HewoCMDMessenger(HewoPlugin plugin, HewoLogger logger) {

        super(plugin, logger);

    }

    public HewoCMDMessenger(HewoPlugin plugin, HewoLogger logger, String prefix) {

        super(plugin, logger, prefix);

    }

    public void sendMessage(List<CommandSender> receivers, String messageID, HewoMsgEntry... replacements) {

        for(CommandSender receiver : receivers) {

            this.sendMessage(receiver, messageID, replacements);

        }

    }

    public void sendMessage(CommandSender receiver, String messageID, HewoMsgEntry... replacements) {

        String message = this.composeMessage(messageID, replacements);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(List<CommandSender> receivers, String messageID, boolean includePrefix, HewoMsgEntry... replacements) {

        for(CommandSender receiver : receivers) {

            this.sendMessage(receiver, messageID, includePrefix, replacements);

        }

    }

    public void sendMessage(CommandSender receiver, String messageID, boolean includePrefix, HewoMsgEntry... replacements) {

        String message = this.composeMessage(messageID, includePrefix, replacements);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(List<CommandSender> receivers, String messageID, boolean includePrefix) {

        for(CommandSender receiver : receivers) {

            this.sendMessage(receiver, messageID, includePrefix);

        }

    }

    public void sendMessage(CommandSender receiver, String messageID, boolean includePrefix) {

        String message = this.composeMessage(messageID, includePrefix);

        if(message == null) return;

        this.sendMessage(receiver, message);

    }

    public void sendMessage(CommandSender receiver, String message) {

        receiver.sendMessage(message);

    }

}
