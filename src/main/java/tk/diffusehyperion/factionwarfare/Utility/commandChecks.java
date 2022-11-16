package tk.diffusehyperion.factionwarfare.Utility;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.Utility.factionOwnership.playerRoles;

public class commandChecks {
    public boolean isPlayer(CommandSender sender, String command) {
        if (!(sender instanceof Player)) {
            sendError(adventure.sender(sender),
                    Component.text("Only players can run ", NamedTextColor.DARK_RED)
                            .append(Component.text(command.toLowerCase(), NamedTextColor.RED))
                            .append(Component.text(".")));
            return false;
        }
        return true;
    }

    public void sendError(Audience audience, Component text) {
        audience.sendMessage(Component.text("Error: ", NamedTextColor.RED)
                .append(text));
    }

    public boolean hasRequiredArgs(int argsLength, int requiredArgs, CommandSender sender, Component error) {
        if (argsLength < requiredArgs) {
            sendError(adventure.sender(sender), error);
            return false;
        }
        return true;
    }

    public boolean isInFaction(String player, CommandSender sender, Component error) {
        if (!playerRoles.containsKey(player)) {
            sendError(adventure.sender(sender), error);
            return false;
        }
        return true;
    }
    public boolean hasRoleOrHigher(String player, factionOwnership.FactionRoles role, CommandSender sender, Component error) {
        if (!isInFaction(player, sender, Component.text("You are not in a faction."))) {
            return false;
        }
        switch (role) {
            case owner:
                if (!Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.owner)) {
                    sendError(adventure.sender(sender), error);
                    return false;
                }
                return true;
            case moderator:
                if (!Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.owner) ||
                        !Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.moderator)) {
                    sendError(adventure.sender(sender), error);
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    public boolean hasRoleOrLower(String player, factionOwnership.FactionRoles role, CommandSender sender, Component error) {
        if (!isInFaction(player, sender, Component.text("You are not in a faction."))) {
            return false;
        }
        switch (role) {
            case moderator:
                if (Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.owner)) {
                    sendError(adventure.sender(sender), error);
                    return false;
                }
                return true;
            case member:
                if (Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.owner) ||
                        Objects.equals(playerRoles.get(player).getValue0(), factionOwnership.FactionRoles.moderator)) {
                    sendError(adventure.sender(sender), error);
                    return false;
                }
                return true;
            default:
                return true;
        }
    }
}
