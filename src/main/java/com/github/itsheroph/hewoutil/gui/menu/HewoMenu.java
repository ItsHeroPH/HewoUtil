package com.github.itsheroph.hewoutil.gui.menu;

import com.github.itsheroph.hewoutil.gui.HewoPlayerMenu;
import com.github.itsheroph.hewoutil.gui.item.HewoMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class HewoMenu extends HewoMenuAbstract implements InventoryHolder {

    protected Inventory inventory;
    protected final HewoPlayerMenu playerMenu;

    public HewoMenu(HewoPlayerMenu playerMenu) {

        this.playerMenu = playerMenu;

    }

    @Override
    public @NotNull Inventory getInventory() {

        return this.inventory;

    }

    public HewoPlayerMenu getPlayerMenu() {

        return this.playerMenu;

    }

    public void open() {

        this.inventory = Bukkit.createInventory(this, this.getSlots(), this.getName().replaceAll("&", "§"));

        this.setMenuItems();

        this.getPlayerMenu().getOwner().openInventory(this.getInventory());
        this.getPlayerMenu().pushMenu(this);

    }

    public void back() {

        this.getPlayerMenu().lastMenu().open();

    }

    public void setItem(int index, ItemStack item) {

        this.inventory.setItem(index, item);

    }

    public void fillEmpty() {

        ItemStack filler = HewoMenuItem.create(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .build();
        this.fillEmpty(filler);

    }

    public void fillEmpty(ItemStack item) {

        for(int i = 0; i < this.getSlots(); i++) {

            if(this.inventory.getItem(i) == null) {

                this.setItem(i, item);

            }

        }

    }

}
