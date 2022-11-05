package net.jeqo.gizmo;

import net.jeqo.gizmo.data.Commands;
import net.jeqo.gizmo.data.Tab;
import net.jeqo.gizmo.listeners.Advance;
import net.jeqo.gizmo.listeners.Prime;
import net.jeqo.gizmo.listeners.Break;
import net.jeqo.gizmo.listeners.Protect;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Gizmo extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("|--[ GIZMO ]---------------------------------------------------------|");
        getLogger().info("|                           Plugin loaded.                           |");
        getLogger().info("|-------------------------------------------------[ MADE BY JEQO ]---|");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        loadListeners();
        loadCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("|--[ GIZMO ]---------------------------------------------------------|");
        getLogger().info("|                          Shutting down...                          |");
        getLogger().info("|-------------------------------------------------[ MADE BY JEQO ]---|");
    }


    private static final Logger log = Bukkit.getLogger();

    public void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new Prime(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);
        Bukkit.getPluginManager().registerEvents(new Advance(), this);
        Bukkit.getPluginManager().registerEvents(new Protect(), this);
    }

    public void loadCommands() {
        Objects.requireNonNull(getCommand("gizmo")).setExecutor(new Commands());

        TabCompleter tc = new Tab();
        Objects.requireNonNull(this.getCommand("gizmo")).setTabCompleter(tc);
    }
}