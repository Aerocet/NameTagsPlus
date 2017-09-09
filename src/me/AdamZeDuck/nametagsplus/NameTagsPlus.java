package me.AdamZeDuck.nametagsplus;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;

import static org.bukkit.event.EventPriority.LOWEST;

public class  NameTagsPlus extends JavaPlugin implements Listener {

    private static Chat chat = null;

    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    Team team = null;

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getServer().getPluginManager().disablePlugin(this);
        }
        getServer().getPluginManager().registerEvents(this, this);
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        String prefix = chat.getPlayerPrefix(p);
        String suffix = chat.getPlayerSuffix(p);
        prefix = prefix.replaceAll("&", "§");
        suffix = suffix.replaceAll("&", "§");
        String newname = prefix + name + suffix;
        p.setPlayerListName(newname);
        if (board.getTeam(p.getName()) == null) {
            team = board.registerNewTeam(p.getName());
        } else {
            team = board.getTeam(p.getName());
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        if (!team.hasEntry(p.getName())) {
            team.addEntry(p.getName());
        }
    }

}
