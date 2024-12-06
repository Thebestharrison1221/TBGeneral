package org.TBCreates.TBGeneral.Listeners.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TBGeneralTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            // Suggest "reload" and "sendpack" for the first argument
            suggestions.add("reload");
            suggestions.add("sendpack");
        }

        return suggestions;
    }
}
