package com.github.itsheroph.hewoutil.plugin.command;

import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class HewoCommand {

    private final HewoCMDMessenger messenger;

    public HewoCommand(HewoCMDMessenger messenger) {

        this.messenger = messenger;

    }

    public abstract String getName();

    public abstract List<String> getAliases();

    public abstract List<String> getOptions(String[] arguments);

    public abstract String getPermission();

    public abstract boolean mayExecute(CommandSender sender);

    public abstract boolean execute(CommandSender sender, String[] arguments);

    public HewoCMDMessenger getMessenger() {

        return this.messenger;

    }
}
