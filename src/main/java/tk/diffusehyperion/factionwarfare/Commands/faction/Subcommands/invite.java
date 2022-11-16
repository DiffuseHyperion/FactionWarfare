package tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.diffusehyperion.factionwarfare.Utility.factionOwnership;

import java.util.HashMap;
import java.util.List;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.FactionWarfare.data;

public class invite {

    public static HashMap<Player, String> invitedPlayers = new HashMap<>();
    //                   invited, faction name
    public void invite(String[] invited, String faction, Player invitee) {
        for (String s : invited) {
            Player p = Bukkit.getPlayer(s);
            invitedPlayers.put(p, faction);
            assert p != null;
            adventure.sender(p).sendMessage(Component.text("You were invited to join ", NamedTextColor.YELLOW)
                    .append(Component.text(faction).decorate(TextDecoration.BOLD))
                    .append(Component.text(" by " + invitee.getDisplayName() + ", accept or deny the invite with /faction invite ("))
                    .append(Component.text("accept").decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.suggestCommand("/faction invite accept")))
                    .append(Component.text("/"))
                    .append(Component.text("deny").decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.suggestCommand("/faction invite deny")))
                    .append(Component.text(")")));
        }
        adventure.sender(invitee).sendMessage(Component.text("Invitations have been sent!", NamedTextColor.DARK_GREEN));
    }

    public void accept(Player invited) {
        if (!invitedPlayers.containsKey(invited)) {
            adventure.sender(invited).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("You do not have a pending invite.", NamedTextColor.DARK_RED)));
            return;
        }
        String factionname = invitedPlayers.get(invited);
        invited.sendMessage("faction: " + factionname);

        List<String> newlist = data.getStringList(factionname + ".members.members");
        newlist.add(invited.getDisplayName());
        // why does this return a boolean, and always true????

        data.set(factionname + ".members.members", newlist);

        new factionOwnership().update(true);

        // send message to faction chat, implement in the future

        adventure.sender(invited).sendMessage(Component.text("Invitation for ", NamedTextColor.DARK_GREEN)
                .append(Component.text(invitedPlayers.get(invited)).decorate(TextDecoration.BOLD))
                .append(Component.text(" has been "))
                .append(Component.text("accepted", NamedTextColor.GREEN))
                .append(Component.text("!")));

        invitedPlayers.remove(invited);
    }

    public void deny(Player invited) {
        if (!invitedPlayers.containsKey(invited)) {
            adventure.sender(invited).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("You do not have a pending invite.", NamedTextColor.DARK_RED)));
            return;
        }
        adventure.sender(invited).sendMessage(Component.text("Invitation for ", NamedTextColor.DARK_GREEN)
                .append(Component.text(invitedPlayers.get(invited)).decorate(TextDecoration.BOLD))
                .append(Component.text(" has been "))
                .append(Component.text("declined", NamedTextColor.RED))
                .append(Component.text(".")));
        invitedPlayers.remove(invited);
    }
}
