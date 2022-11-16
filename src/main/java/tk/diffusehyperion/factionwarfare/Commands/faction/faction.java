package tk.diffusehyperion.factionwarfare.Commands.faction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands.create;
import tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands.invite;
import tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands.promote;
import tk.diffusehyperion.factionwarfare.Utility.commandChecks;
import tk.diffusehyperion.factionwarfare.Utility.factionOwnership;

import java.util.Arrays;
import java.util.Objects;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.FactionWarfare.data;
import static tk.diffusehyperion.factionwarfare.Utility.factionOwnership.playerRoles;

public class faction implements CommandExecutor {

    private final commandChecks commandChecks = new commandChecks();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!commandChecks.hasRequiredArgs(args.length, 1, sender,
                Component.text("No command provided.", NamedTextColor.DARK_RED)
                        .append(Component.newline())
                        .append(Component.text("Available commands:"))
                        .append(Component.newline())
                        .append(Component.text("/faction (", NamedTextColor.WHITE))

                        .append(Component.text("create", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction create")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("invite", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction invite")))

                        .append(Component.text("/", NamedTextColor.WHITE))
                        .append(Component.text("promote", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction promote")))

                        .append(Component.text(")", NamedTextColor.WHITE)))) {
            return true;
        }
        switch (args[0]) {
            case "create":
                if (!commandChecks.isPlayer(sender, "create")) {
                    return true;
                }
                if (commandChecks.isInFaction(sender.getName(), sender, Component.text("You are already in a faction.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                if (!commandChecks.hasRequiredArgs(args.length, 2, sender,
                        Component.text("No faction name provided.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                String[] factionNameArray = Arrays.copyOfRange(args, 1, args.length);
                String factionname = String.join(" ", factionNameArray);
                if (data.contains(factionname)) {
                    commandChecks.sendError(adventure.sender(sender), Component.text("Name is already taken.", NamedTextColor.DARK_RED));
                }
                new create().create(String.join(" ", factionNameArray), (Player) sender);
                return true;
            case "invite":
                if (!commandChecks.isPlayer(sender, "invite")) {
                    return true;
                }
                if (!commandChecks.hasRequiredArgs(args.length, 2, sender,
                        Component.text("No subcommand provided.", NamedTextColor.DARK_RED)
                                .append(Component.newline())
                                .append(Component.text("Available commands:"))
                                .append(Component.newline())
                                .append(Component.text("/faction invite (", NamedTextColor.WHITE))

                                .append(Component.text("player", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite player")))

                                .append(Component.text("/", NamedTextColor.WHITE))
                                .append(Component.text("accept", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite accept")))

                                .append(Component.text("/", NamedTextColor.WHITE))
                                .append(Component.text("deny", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite deny")))

                                .append(Component.text(")", NamedTextColor.WHITE)))) {
                    return true;
                }
                invite invite = new invite();
                switch (args[1]) {
                    case "player":
                        if (!commandChecks.hasRequiredArgs(args.length, 3, sender, Component.text("No players provided.", NamedTextColor.DARK_RED))) {
                            return true;
                        }
                        if (!commandChecks.isInFaction(((Player) sender).getDisplayName(), sender, Component.text("You are not in a faction."))) {
                            return true;
                        }
                        if (!commandChecks.hasRoleOrLower(((Player) sender).getDisplayName(), factionOwnership.FactionRoles.moderator, sender,
                                Component.text("Only ", NamedTextColor.DARK_RED)
                                .append(Component.text("owners and moderators").decorate(TextDecoration.BOLD))
                                .append(Component.text(" can invite players.", NamedTextColor.DARK_RED)))) {
                            return true;
                        }
                        String[] invitedPlayersArray = Arrays.copyOfRange(args, 2, args.length);
                        for (String s : invitedPlayersArray) {
                            if (Objects.isNull(Bukkit.getPlayer(s))) {
                                adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                                        .append(Component.text(s + " is offline. You can only invite online players.", NamedTextColor.DARK_RED)));
                                return true;
                            }
                            if (commandChecks.isInFaction(s, sender, Component.text(s + " is already in a faction. You can only invite faction-less players.", NamedTextColor.DARK_RED))) {
                                return true;
                            }
                        }

                        invite.invite(invitedPlayersArray, playerRoles.get(((Player) sender).getDisplayName()).getValue1(), (Player) sender);
                        return true;
                    case "accept":
                        invite.accept((Player) sender);
                        return true;
                    case "deny":
                        invite.deny((Player) sender);
                        return true;
                    default:
                        commandChecks.sendError(adventure.sender(sender), Component.text("Unknown subcommand.", NamedTextColor.DARK_RED)
                                .append(Component.newline())
                                .append(Component.text("Available commands:"))
                                .append(Component.newline())
                                .append(Component.text("/faction invite (", NamedTextColor.WHITE))

                                .append(Component.text("player", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite player")))

                                .append(Component.text("/", NamedTextColor.WHITE))
                                .append(Component.text("accept", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite accept")))

                                .append(Component.text("/", NamedTextColor.WHITE))
                                .append(Component.text("deny", NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
                                        .clickEvent(ClickEvent.suggestCommand("/faction invite deny")))

                                .append(Component.text(")", NamedTextColor.WHITE)));
                        return true;
                }
            case "promote":
                if (!commandChecks.isPlayer(sender, "promote")) {
                    return true;
                }
                if (!commandChecks.hasRoleOrHigher(sender.getName(), factionOwnership.FactionRoles.owner, sender, Component.text("You need to be an owner in a faction to promote.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                if (!commandChecks.hasRequiredArgs(args.length, 2, sender, Component.text("No players provided.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                if (!commandChecks.isInFaction(args[1], sender, Component.text(args[1] + " is not in a faction.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                if (!Objects.equals(playerRoles.get(sender.getName()).getValue1(), playerRoles.get(args[1]).getValue1())) {
                    commandChecks.sendError(adventure.sender(sender), Component.text(args[1] + " is not in your faction.", NamedTextColor.DARK_RED));
                    return true;
                }
                if (commandChecks.hasRoleOrLower(args[1], factionOwnership.FactionRoles.moderator, sender, Component.text(args[1] + " is already an moderator. A faction can only have 1 owner.", NamedTextColor.DARK_RED))) {
                    return true;
                }
                new promote().promote(args[1], sender);
                return true;
            default:
                adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                        .append(Component.text("Unknown command.", NamedTextColor.DARK_RED))
                        .append(Component.newline())
                        .append(Component.text("Available commands:")));
                adventure.sender(sender).sendMessage(Component.text("/faction (")
                        .append(Component.text("create").decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction create")))
                        .append(Component.text("/"))
                        .append(Component.text("invite").decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction invite")))
                        .append(Component.text("/"))
                        .append(Component.text("promote").decorate(TextDecoration.UNDERLINED)
                                .clickEvent(ClickEvent.suggestCommand("/faction promote")))
                        .append(Component.text(")")));
                return true;
        }
    }
}
