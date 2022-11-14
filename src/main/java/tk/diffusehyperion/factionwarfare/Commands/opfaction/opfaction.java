package tk.diffusehyperion.factionwarfare.Commands.opfaction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.diffusehyperion.factionwarfare.Commands.faction.create;

import java.util.Arrays;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;

public class opfaction implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("No command provided.", NamedTextColor.DARK_RED))
                    .append(Component.newline())
                    .append(Component.text("Available commands:")));
            return false;
        }
        switch (args[0]) {
            case "save":
                new save().save(sender);
                return true;
            default:
                adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                        .append(Component.text("Unknown command.", NamedTextColor.DARK_RED)));
                return false;
        }
    }
}
