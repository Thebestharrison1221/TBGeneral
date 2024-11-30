package org.TBCreates.TBGeneral.commands;

import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TBGeneralCommand implements CommandExecutor, TabCompleter {
    private final TBGeneral plugin;

    public TBGeneralCommand(TBGeneral plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(this.plugin.getPrefix() + " &7Usage: /tbgeneral reload");
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            return this.handleReload(sender);
        } else {
            sender.sendMessage(this.plugin.getPrefix() + " &cUnknown subcommand. Usage: /tbgeneral reload");
            return false;
        }
    }

    private boolean handleReload(CommandSender sender) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("tbgeneral.admin")) {
                player.sendMessage(this.plugin.getPrefix() + " &cYou do not have permission to reload the plugin.");
                return false;
            }
        }

        this.plugin.reloadConfig();
        sender.sendMessage(this.plugin.getPrefix() + " &aPlugin reloaded successfully!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            // Provide the "reload" suggestion
            suggestions.add("reload");
        }
        return suggestions;
    }
}