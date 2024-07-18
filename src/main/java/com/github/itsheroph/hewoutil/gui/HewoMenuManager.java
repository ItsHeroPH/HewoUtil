package com.github.itsheroph.hewoutil.gui;

import com.github.itsheroph.hewoutil.gui.listener.HewoMenuListener;
import com.github.itsheroph.hewoutil.plugin.HewoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HewoMenuManager {

    private static final Map<Player, HewoPlayerMenu> playerMap = new HashMap<>();

    public static void setup(HewoPlugin plugin) {

        Bukkit.getPluginManager().registerEvents(new HewoMenuListener(), plugin);

        for(Player player : Bukkit.getOnlinePlayers()) {

            createPlayer(player);

        }

    }

    public static void disable() {

        for(Player player : Bukkit.getOnlinePlayers()) {

            removePlayer(player);

        }

    }

    public static HewoPlayerMenu getPlayer(Player player) {

        if(playerMap.containsKey(player)) {

            return playerMap.get(player);

        } else {

            return createPlayer(player);

        }

    }

    public static HewoPlayerMenu createPlayer(Player player) {

        HewoPlayerMenu playerMenu = new HewoPlayerMenu(player);
        playerMap.put(player, playerMenu);

        return playerMenu;

    }

    public static void removePlayer(Player player) {

        if(!playerMap.containsKey(player)) return;

        HewoPlayerMenu playerMenu = getPlayer(player);

        playerMenu.clearMenu();
        playerMenu.getOwner().closeInventory();

        playerMap.remove(playerMenu.getOwner());

    }
}
