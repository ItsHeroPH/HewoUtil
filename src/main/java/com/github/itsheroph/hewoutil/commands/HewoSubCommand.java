package com.github.itsheroph.hewoutil.commands;

import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class HewoSubCommand {

    private final HewoCMDMessenger messenger;

    public HewoSubCommand(HewoCMDMessenger messenger) {

        this.messenger = messenger;
    }

    public HewoCMDMessenger getMessenger() {

        return this.messenger;

    }

    public abstract String getName();

    public abstract List<String> getOptions(CommandSender commandSender, String[] arguments);

    public abstract String getPermission();

    public abstract boolean mayExecute(CommandSender commandSender);

    public abstract boolean execute(CommandSender commandSender, String[] arguments);
}
