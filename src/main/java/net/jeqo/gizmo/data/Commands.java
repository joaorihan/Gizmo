package net.jeqo.gizmo.data;

import jdk.jshell.execution.Util;
import net.jeqo.gizmo.Gizmo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Wrapper;
import java.util.Objects;

import static net.jeqo.gizmo.listeners.Prime.saveInv;

public class Commands implements CommandExecutor {

    Gizmo plugin = Gizmo.getPlugin(Gizmo.class);

    String shift12 = plugin.getConfig().getString("Unicodes.shift-12");
    String shift36 = plugin.getConfig().getString("Unicodes.shift-36");
    String shift256 = plugin.getConfig().getString("Unicodes.shift-256");
    String shift501 = plugin.getConfig().getString("Unicodes.shift-501");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                if (p.hasPermission("gizmo.reload")) {
                    p.sendMessage("");
                    p.sendMessage(Utilities.hex("   #ee0000/gizmo reload &7- Reloads the Gizmo config."));
                    p.sendMessage(Utilities.hex("   #ee0000/gizmo test &7- Displays a test welcome screen."));
                    p.sendMessage("");
                    p.sendMessage(Utilities.hex("   #ee0000Gizmo 1.0.2-BETA &7- Made by Jeqo"));
                    p.sendMessage("");
                } else {
                    p.sendMessage("");
                    p.sendMessage(Utilities.hex("   #ee0000Gizmo 1.0.2-BETA &7- Made by Jeqo"));
                    p.sendMessage("");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                if (p.hasPermission("gizmo.reload")) {
                    plugin.reloadConfig();
                    p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.config-reloaded")));
                } else {
                    p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.no-permission")));
                }
            } else if (args[0].equalsIgnoreCase("show")) {
                if (args.length > 1) {
                    Player t = Bukkit.getPlayer(args [1]);
                    if (p.hasPermission("gizmo.show")) {
                        if (t != null) {
                            saveInv.put(t.getName(), t.getInventory().getContents());
                            t.getInventory().clear();

                            if (Objects.equals(plugin.getConfig().getString("enable-background"), "true")) {
                                Objects.requireNonNull(t.getPlayer()).openInventory(plugin.getServer().createInventory(null, 54, ChatColor.WHITE + shift256 + shift256 + plugin.getConfig().getString("Unicodes.background") + shift501 + shift36 + plugin.getConfig().getString("Unicodes.welcome-screen")));
                                p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + Objects.requireNonNull(plugin.getConfig().getString("messages.show-screen-others")).replace("%player%", t.getName())));
                            } else if (Objects.equals(plugin.getConfig().getString("enable-background"), "false")) {
                                Objects.requireNonNull(t.getPlayer()).openInventory(plugin.getServer().createInventory(null, 54, ChatColor.WHITE + shift36 + shift12 + plugin.getConfig().getString("Unicodes.welcome-screen")));
                                p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + Objects.requireNonNull(plugin.getConfig().getString("messages.show-screen-others")).replace("%player%", t.getName())));
                            }

                        } else {
                            p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.player-not-found")));
                        }
                    } else {
                        p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.no-permission")));
                    }


                } else {
                    if (p.hasPermission("gizmo.show")) {

                        saveInv.put(p.getName(), p.getInventory().getContents());
                        p.getInventory().clear();

                        if (Objects.equals(plugin.getConfig().getString("enable-background"), "true")) {
                            Objects.requireNonNull(p.getPlayer()).openInventory(plugin.getServer().createInventory(null, 54, ChatColor.WHITE + shift256 + shift256 + plugin.getConfig().getString("Unicodes.background") + shift501 + shift36 + plugin.getConfig().getString("Unicodes.welcome-screen")));
                            p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.show-screen")));
                        } else if (Objects.equals(plugin.getConfig().getString("enable-background"), "false")) {
                            Objects.requireNonNull(p.getPlayer()).openInventory(plugin.getServer().createInventory(null, 54, ChatColor.WHITE + shift36 + shift12 + plugin.getConfig().getString("Unicodes.welcome-screen")));
                            p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + plugin.getConfig().getString("messages.show-screen")));
                        }
                    } else {
                        p.sendMessage(Utilities.hex("#ee0000[Gizmo] " + Utilities.hex(plugin.getConfig().getString("messages.no-permission"))));
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            plugin.reloadConfig();
            plugin.getLogger().info("|--[ GIZMO ]---------------------------------------------------------|");
            plugin.getLogger().info("|                          Config reloaded.                          |");
            plugin.getLogger().info("|-------------------------------------------------[ MADE BY JEQO ]---|");
        }
        return true;
    }
}