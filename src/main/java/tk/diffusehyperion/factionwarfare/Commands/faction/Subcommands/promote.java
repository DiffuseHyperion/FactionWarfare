package tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.javatuples.Pair;
import tk.diffusehyperion.factionwarfare.Utility.factionOwnership;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.Utility.factionOwnership.playerRoles;

public class promote {
    public void promote(String promotee, CommandSender promoter) {
        playerRoles.put(promotee, new Pair<>(factionOwnership.FactionRoles.moderator, playerRoles.get(promotee).getValue1()));
        adventure.sender(promoter).sendMessage(Component.text(promotee + " has been promoted to a ", NamedTextColor.GREEN)
                .append(Component.text("moderator", NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .append(Component.text("!")));
    }
}
