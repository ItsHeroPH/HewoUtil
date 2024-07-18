package com.github.itsheroph.hewoutil.gui;

import com.github.itsheroph.hewoutil.gui.menu.HewoMenu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HewoPlayerMenu {

    private final Player owner;
    private final Map<String, Object> dataMap;
    private final List<HewoMenu> history;

    public HewoPlayerMenu(Player player) {

        this.owner = player;
        this.dataMap = new HashMap<>();
        this.history = new ArrayList<>();

    }

    public Player getOwner() {

        return this.owner;

    }

    public void setData(String key, Object value) {

        this.dataMap.put(key, value);

    }

    public void setData(Enum key, Object value) {

        this.setData(key.name(), value);

    }

    public Object getData(String key) {

        return this.dataMap.get(key);

    }

    public Object getData(Enum key) {

        return this.getData(key.name());

    }

    public <T> T getData(String key, Class<T> classRef) {

        Object data = this.getData(key);

        if(data == null) {

            return null;

        } else {

            return classRef.cast(data);

        }

    }

    public <T> T getData(Enum key, Class<T> classRef) {

        Object data = this.getData(key);

        if(data == null) {

            return null;

        } else {

            return classRef.cast(data);

        }

    }

    public HewoMenu lastMenu() {

        this.history.removeLast();
        return this.history.getLast();

    }

    public void pushMenu(HewoMenu menu) {

        this.history.add(menu);

    }

    public void clearMenu() {

        this.history.clear();

    }
}
