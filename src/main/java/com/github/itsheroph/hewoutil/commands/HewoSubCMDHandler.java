package com.github.itsheroph.hewoutil.commands;

import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HewoSubCMDHandler implements CommandExecutor, TabExecutor {

    private final String name;
    private final HewoCMDMessenger messenger;
    private final Map<String, HewoSubCommand> commandMap = new HashMap<>();

    protected HewoSubCMDHandler(HewoCMDMessenger messenger, String commandName,  HewoSubCommand... commands) {

        this.name = commandName;
        this.messenger = messenger;

        for(HewoSubCommand command : commands) {

            this.addCommand(command);

        }

    }

    public String getName() {

        return this.name;

    }

    public HewoCMDMessenger getMessenger() {

        return this.messenger;

    }

    public Map<String, HewoSubCommand> getCommandMap() {

        return this.commandMap;

    }

    public void addCommand(HewoSubCommand command) {

        this.getCommandMap().put(command.getName(), command);

    }

    public HewoSubCommand getCommand(String commandName) {

        return this.getCommandMap().get(commandName);

    }

    public boolean hasCommand(String commandName) {

        return this.getCommandMap().containsKey(commandName);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {

        String cmdName = arguments.length == 0 ? "help" : arguments[0].toLowerCase();

        if(this.hasCommand(cmdName)) {

            HewoSubCommand hewoCommand = this.getCommand(cmdName);

            if(!hewoCommand.mayExecute(commandSender)) {

                this.getMessenger().sendMessage(commandSender, this.getMessenger().composeMessage("command_cant_execute"));
                return true;

            }
            if(!commandSender.hasPermission(hewoCommand.getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {

                this.getMessenger().sendMessage(commandSender, this.getMessenger().composeMessage("command_no_permission"));
                return true;

            }

            return hewoCommand.execute(commandSender, arguments);

        } else {

            this.getMessenger().sendMessage(commandSender, this.getMessenger().composeMessage("command_unknown"));
            return true;

        }

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] arguments) {

        if(arguments.length == 1) return new ArrayList<>(this.getCommandMap().keySet());

        if(arguments.length >= 2) {

            String cmdName = arguments[0].toLowerCase();
            if(this.hasCommand(cmdName)) {

                HewoSubCommand hewoCommand = this.getCommand(cmdName);

                return hewoCommand.getOptions(commandSender, arguments);

            }

        }

        return List.of();

    }
}
