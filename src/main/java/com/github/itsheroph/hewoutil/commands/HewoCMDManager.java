package com.github.itsheroph.hewoutil.commands;

import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.command.CommandExecutor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HewoCMDManager {
    
    private static final Map<String, CommandExecutor> commandMap = new HashMap<>();

    public static void registerCommand(HewoPlugin plugin, HewoCommand... commands) {

        for(HewoCommand command : commands) {

            registerCommand(plugin, command);

        }

    }


    public static void registerCommand(HewoPlugin plugin, HewoCommand command) {

        plugin.getCommand(command.getName()).setExecutor(command);
        commandMap.put(command.getName(), command);
        
    }
    
    public static CommandExecutor[] getCommands() {
        
        return commandMap.values().toArray(new CommandExecutor[0]);
        
    }

    public static @Nullable CommandExecutor getCommand(String commandName) {

        return commandMap.getOrDefault(commandName, null);

    }

    public static void registerSubCommand(HewoPlugin plugin, HewoSubCMDHandler cmdHandler) {

        plugin.getCommand(cmdHandler.getName()).setExecutor(cmdHandler);
        commandMap.put(cmdHandler.getName(), cmdHandler);

    }

}
