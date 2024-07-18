package com.github.itsheroph.hewoutil.gui.listener;

import com.github.itsheroph.hewoutil.gui.HewoMenuManager;
import com.github.itsheroph.hewoutil.gui.menu.HewoMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

public class HewoMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();

        if(holder == null) return;

        if(holder instanceof HewoMenu) {

            if(event.getCurrentItem() == null) {

                return;

            }

            HewoMenu menu = (HewoMenu) holder;

            if(event.getClickedInventory() != menu.getInventory()) return;

            if(menu.cancelClicks()) {

                event.setCancelled(true);

            }

            menu.onClick(event);

        }

    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();

        if(holder == null) return;

        if(holder instanceof HewoMenu) {

            HewoMenu menu = (HewoMenu) holder;

            menu.onOpen(event);

        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();

        if(holder == null) return;

        if(holder instanceof HewoMenu) {

            HewoMenu menu = (HewoMenu) holder;

            menu.onClose(event);

        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        HewoMenuManager.createPlayer(event.getPlayer());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        HewoMenuManager.removePlayer(event.getPlayer());

    }
}
