package tk.diffusehyperion.factionwarfare.Commands.opfaction.Subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.Utility.factionOwnership.playerRoles;

public class role {
    public void role(String pname, CommandSender sender) {
        Bukkit.getLogger().info("role method triggered");
        if (!playerRoles.containsKey(pname)) {
            adventure.sender(sender).sendMessage(Component.text(pname + " is not in a faction.", NamedTextColor.DARK_GREEN));
            return;
        }
        switch (playerRoles.get(pname).getValue0()) {
            case owner:
                adventure.sender(sender).sendMessage(Component.text(pname + " is an owner in the faction " + playerRoles.get(pname).getValue1(), NamedTextColor.DARK_GREEN));
                break;
            case moderator:
                adventure.sender(sender).sendMessage(Component.text(pname + " is an moderator in the faction " + playerRoles.get(pname).getValue1(), NamedTextColor.DARK_GREEN));
                break;
            case member:
                adventure.sender(sender).sendMessage(Component.text(pname + " is an member in the faction " + playerRoles.get(pname).getValue1(), NamedTextColor.DARK_GREEN));
                break;
        }
    }
}
