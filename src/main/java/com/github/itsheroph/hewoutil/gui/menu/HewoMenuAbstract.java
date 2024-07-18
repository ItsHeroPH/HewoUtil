package com.github.itsheroph.hewoutil.gui.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract class HewoMenuAbstract {

    public abstract String getName();

    public abstract int getSlots();

    public abstract boolean cancelClicks();

    public abstract void setMenuItems();

    public void onClick(InventoryClickEvent event) {}

    public void onOpen(InventoryOpenEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

}
