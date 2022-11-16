package tk.diffusehyperion.factionwarfare.Commands.opfaction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.diffusehyperion.factionwarfare.Commands.opfaction.Subcommands.delete;
import tk.diffusehyperion.factionwarfare.Commands.opfaction.Subcommands.role;
import tk.diffusehyperion.factionwarfare.Commands.opfaction.Subcommands.save;
import tk.diffusehyperion.factionwarfare.Utility.commandChecks;

import java.util.Objects;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.Utility.factionOwnership.factionOwnership;

public class opfaction implements CommandExecutor {

    private final tk.diffusehyperion.factionwarfare.Utility.commandChecks commandChecks = new commandChecks();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!commandChecks.hasRequiredArgs(args.length, 1, sender,
                Component.text("No command provided.", NamedTextColor.DARK_RED)
                        .append(Component.newline())
                        .append(Component.text("Available commands:"))
                        .append(Component.newline())
                        .append(Component.text("/opfaction (", NamedTextColor.WHITE))

                        .append(Component.text("save", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction save")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("role", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction role")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("delete", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction delete")))

                        .append(Component.text(")", NamedTextColor.WHITE)))) {
            return true;
        }
        switch (args[0]) {
            case "save":
                new save().save(sender);
                return true;
            case "role":
                if (!commandChecks.hasRequiredArgs(args.length, 2, sender, Component.text("No player provided.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                new role().role(args[1], sender);
                return true;
            case "delete":
                if (!commandChecks.hasRequiredArgs(args.length, 2, sender, Component.text("No faction provided.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                if (!factionOwnership.containsKey(args[1])) {
                    commandChecks.sendError(adventure.sender(sender), Component.text("Faction ", NamedTextColor.DARK_RED)
                            .append(Component.text(args[1], NamedTextColor.RED).decorate(TextDecoration.BOLD))
                            .append(Component.text(" does not exist.")));
                    return true;
                }
                boolean confirmation = args.length == 3 && Objects.equals(args[2], "confirm");
                new delete().delete(args[1], sender, confirmation);
                return true;
            default:
                commandChecks.sendError(adventure.sender(sender), Component.text("Unknown command.", NamedTextColor.DARK_RED)
                        .append(Component.newline())
                        .append(Component.text("Available commands:"))
                        .append(Component.newline())
                        .append(Component.text("/opfaction (", NamedTextColor.WHITE))

                        .append(Component.text("save", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction save")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("role", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction role")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("delete", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/opfaction delete")))

                        .append(Component.text(")", NamedTextColor.WHITE)));
                return true;
        }
    }
}
