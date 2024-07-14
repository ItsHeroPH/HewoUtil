package com.github.itsheroph.hewoutil.plugin.command;

import com.github.itsheroph.hewoutil.messages.command.HewoCMDMessenger;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HewoCMDHandler implements CommandExecutor, TabExecutor {

    private final Map<String, HewoCommand> commandMap;
    private final HewoPlugin plugin;
    private final HewoCMDMessenger messenger;

    public HewoCMDHandler(HewoPlugin plugin, HewoCMDMessenger messenger, HewoCommand... commands) {

        this.commandMap = new HashMap<>();
        this.plugin = plugin;
        this.messenger = messenger;

        this.addCommands(commands);

    }

    public HewoPlugin getPlugin() {

        return this.plugin;

    }

    public HewoCMDMessenger getMessenger() {

        return this.messenger;

    }

    public Map<String, HewoCommand> getCommandMap() {

        return this.commandMap;

    }

    public HewoCommand getCommand(String command) {

        return this.getCommandMap().getOrDefault(command, null);

    }

    public List<HewoCommand> getCommands() {

        return new ArrayList<>(this.getCommandMap().values());

    }

    public void addCommands(HewoCommand... commands) {

        for (HewoCommand command : commands) {

            this.addCommand(command);

        }

    }

    public void addCommand(HewoCommand command) {

        this.getCommandMap().put(command.getName(), command);

        for(String alias : command.getAliases()) {

            this.getCommandMap().put(alias, command);

        }

    }

    public boolean hasCommand(String command) {

        return this.getCommandMap().containsKey(command);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {

        String cmdName = arguments.length == 0 ? "help" : arguments[0].toLowerCase();

        if(this.hasCommand(cmdName)) {

            HewoCommand hewoCommand = this.getCommand(cmdName);

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
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] arguments) {

        if(arguments.length == 1) return new ArrayList<>(this.getCommandMap().keySet());

        if(arguments.length >= 2) {

            String cmdName = arguments[0].toLowerCase();
            if(this.hasCommand(cmdName)) {

                HewoCommand hewoCommand = this.getCommand(cmdName);

                return hewoCommand.getOptions(commandSender, arguments);

            }

        }

        return List.of();

    }

}
