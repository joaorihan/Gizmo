package net.jeqo.gizmo.listeners;

import net.jeqo.gizmo.Gizmo;
import net.jeqo.gizmo.data.Utilities;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static net.jeqo.gizmo.listeners.Prime.gamemode;

public class Advance implements Listener {

    Gizmo plugin = Gizmo.getPlugin(Gizmo.class);
    String shift12 = plugin.getConfig().getString("Unicodes.shift-12");
    String shift36 = plugin.getConfig().getString("Unicodes.shift-36");
    String shift256 = plugin.getConfig().getString("Unicodes.shift-256");
    String shift501 = plugin.getConfig().getString("Unicodes.shift-501");


    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (e.getView().getTitle().equals(ChatColor.WHITE + shift256 + shift256 + plugin.getConfig().getString("Unicodes.background") + shift501 + shift36 + plugin.getConfig().getString("Unicodes.welcome-screen")) || e.getView().getTitle().equals(ChatColor.WHITE + shift36 + shift12 + plugin.getConfig().getString("Unicodes.welcome-screen"))) {

            if (Objects.equals(plugin.getConfig().getString("Sound-on-Advance.enable"), "true")) {
                p.playSound(p.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound-on-Advance.sound")), Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("Sound-on-Advance.volume"))), Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("Sound-on-Advance.pitch"))));
            }

            p.setGameMode(gamemode);
            Prime.screening = false;

            if (Objects.equals(plugin.getConfig().getString("enable-fade"), "true")) {
                if (Objects.equals(plugin.getConfig().getString("fade-mode"), "A")) {
                    p.sendTitle((String) plugin.getConfig().get("Unicodes.background"), "", 0, 5, plugin.getConfig().getInt("fade-time"));
                } else if (Objects.equals(plugin.getConfig().getString("fade-mode"), "B")) {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("fade-time"), 1, false, false));
                }
            }
            p.setGameMode(Protect.joinGm);
            p.sendMessage(Utilities.hex(plugin.getConfig().getString("messages.welcome-message").replace(",", "\n").replace("[", "").replace("]", "")));

            if (Objects.equals(plugin.getConfig().getString("Command-on-Advance.enable"), "true")) {
                p.performCommand(Objects.requireNonNull(plugin.getConfig().getString("Command-on-Advance.command")));
            }
        }
    }


    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();

        if ((Objects.equals(plugin.getConfig().getString("Auto-Close.enable"), "true"))) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if (Prime.screening.equals(true)) {
                        p.closeInventory();
                    }
                }
            }, Long.parseLong(Objects.requireNonNull(plugin.getConfig().getString("Auto-Close.time"))));
        }
    }
}