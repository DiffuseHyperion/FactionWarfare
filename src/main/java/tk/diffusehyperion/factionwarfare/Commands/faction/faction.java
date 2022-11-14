package tk.diffusehyperion.factionwarfare.Commands.faction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;

public class faction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("No command provided.", NamedTextColor.DARK_RED))
                    .append(Component.newline())
                    .append(Component.text("Available commands:")));
            return false;
        }
        switch (args[0]) {
            case "create":
                if (!(sender instanceof Player)) {
                    adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                            .append(Component.text("Only players can run ", NamedTextColor.DARK_RED))
                            .append(Component.text("create", NamedTextColor.RED))
                            .append(Component.text(".")));
                    return true;
                }
                if (args.length == 1) {
                    adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                            .append(Component.text("No faction name provided.", NamedTextColor.DARK_RED)));
                    return true;
                }

                String[] factionNameArray = Arrays.copyOfRange(args, 1, args.length);
                new create().create(String.join(" ", factionNameArray), (Player) sender);
                return true;
            default:
                adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                        .append(Component.text("Unknown command.", NamedTextColor.DARK_RED)));
                return false;
        }
    }
}
