package com.github.itsheroph.hewoutil.gui.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HewoMenuItem {

    private final Material material;
    private final ItemMeta meta;

    public HewoMenuItem(Material material) {

        this.material = material;
        this.meta = Bukkit.getItemFactory().getItemMeta(material);

    }

    public static HewoMenuItem create(ItemStack item) {

        return new HewoMenuItem(item.getType());

    }

    public static HewoMenuItem create(Material material) {

        return new HewoMenuItem(material);

    }

    public HewoMenuItem setDisplayName(String name) {

        this.getMeta().setDisplayName(name.replaceAll("&", "§"));

        return this;

    }

    public HewoMenuItem setLore(String... lore) {

        return this.setLore(Arrays.asList(lore));

    }

    public HewoMenuItem setLore(List<String> lore) {

        this.getMeta().setLore(lore.stream().map(l -> l.replaceAll("&", "§")).collect(Collectors.toList()));

        return this;

    }

    public HewoMenuItem appendLore(String lore) {

        List<String> loreList = this.getMeta().getLore();

        if(loreList == null) {

            return this.setLore(lore);

        } else {

            loreList.add(lore);
            return this.setLore(loreList);

        }

    }

    public HewoMenuItem addEnchantment(Enchantment enchantment, int level, boolean ignore_max_level) {

        this.getMeta().addEnchant(enchantment, level, ignore_max_level);

        return this;

    }

    public HewoMenuItem addEnchantment(Enchantment enchantment, int level) {

        return this.addEnchantment(enchantment, level, true);

    }

    public HewoMenuItem addEnchantment(Enchantment enchantment) {

        return this.addEnchantment(enchantment, 0);

    }

    public HewoMenuItem addEffect(PotionEffect effect, boolean override) {

        PotionMeta meta = (PotionMeta) this.getMeta();
        meta.addCustomEffect(effect, override);

        return this;

    }

    public HewoMenuItem addEffect(PotionEffect effect) {

        return this.addEffect(effect, true);

    }

    public HewoMenuItem addFlags(ItemFlag... flags) {

        this.getMeta().addItemFlags(flags);

        return this;

    }

    public HewoMenuItem unbreakable(boolean unbreakable) {

        this.getMeta().setUnbreakable(unbreakable);

        return this;

    }

    public HewoMenuItem glow() {

        return this.addEnchantment(Enchantment.LURE, 1).addFlags(ItemFlag.HIDE_ENCHANTS);

    }

    public ItemMeta getMeta() {

        return this.meta;

    }

    public Material getMaterial() {

        return this.material;

    }

    public ItemStack build() {

        ItemStack item = new ItemStack(this.getMaterial());

        item.setItemMeta(this.getMeta());

        return item;

    }

}
