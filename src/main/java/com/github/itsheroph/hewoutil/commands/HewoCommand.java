package com.github.itsheroph.hewoutil.commands;

import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class HewoCommand implements CommandExecutor, TabExecutor {

    private final HewoCMDMessenger messenger;

    protected HewoCommand(HewoCMDMessenger messenger) {

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

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {

        if(!this.mayExecute(commandSender)) {

            this.getMessenger().sendMessage(commandSender, this.getMessenger().composeMessage("command_cant_execute"));
            return true;

        }
        if(!commandSender.hasPermission(this.getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {

            this.getMessenger().sendMessage(commandSender, this.getMessenger().composeMessage("command_no_permission"));
            return true;

        }

        return this.execute(commandSender, arguments);

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {

        return this.getOptions(commandSender, arguments);

    }
}
