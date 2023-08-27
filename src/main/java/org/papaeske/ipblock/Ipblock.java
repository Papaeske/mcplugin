package org.papaeske.ipblock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Ipblock extends JavaPlugin implements Listener {

    private static final String ALLOWED_IP = "80.198.96.146";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (isAllowedIp(event)) {
            Player player = event.getPlayer();
            player.sendMessage(Component.text("Velkommen!").color(NamedTextColor.GREEN));
        } else {
            event.getPlayer().kick(Component.text("Du er vist ikke på skolen makker!"));
        }
    }

    private boolean isAllowedIp(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = player.getAddress().getAddress().getHostAddress();
        return ip.equals(ALLOWED_IP);
    }
}