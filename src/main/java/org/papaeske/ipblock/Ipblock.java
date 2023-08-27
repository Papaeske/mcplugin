package org.papaeske.ipblock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public final class Ipblock extends JavaPlugin implements Listener {

    private FileConfiguration config;
    private File configFile;
    private String allowedIp;

    @Override
    public void onEnable() {
        // Initialize configuration
        config = getConfig();
        configFile = new File(getDataFolder(), "config.yml");

        // Load the configuration
        loadConfig();

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        // Copy the default configuration from resources if the config file doesn't exist
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        // Load the configuration from the file
        config = YamlConfiguration.loadConfiguration(configFile);

        // Read the allowed IP from the configuration
        allowedIp = config.getString("allowed_ip", "80.198.96.146");

        // Save the configuration (including default values if necessary)
        saveCustomConfig();
    }

    private void saveCustomConfig() {
        // Save the allowed IP to the configuration
        config.set("allowed_ip", allowedIp);

        // Save the configuration to the file
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (isAllowedIp(event)) {
            Player player = event.getPlayer();
            player.sendMessage(Component.text("Velkommen til!").color(NamedTextColor.GREEN));
        } else {
            event.getPlayer().kick(Component.text("Du er vist ikke på skolen makker!"));
        }
    }

    private boolean isAllowedIp(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = player.getAddress().getAddress().getHostAddress();
        return ip.equals(allowedIp);
    }
}