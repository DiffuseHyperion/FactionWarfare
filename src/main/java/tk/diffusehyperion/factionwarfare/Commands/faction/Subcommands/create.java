package tk.diffusehyperion.factionwarfare.Commands.faction.Subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.diffusehyperion.factionwarfare.Utility.factionOwnership;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.FactionWarfare.data;

public class create {
    public void create(String factionname, Player owner) {
        data.set(factionname, "");

        data.set(factionname + ".members", "");

        data.set(factionname + ".members.owner", owner.getDisplayName());
        data.set(factionname + ".members.moderators", "");
        data.set(factionname + ".members.members", "");

        data.set(factionname + ".claimedareas", "");
        data.set(factionname + ".claimedareas.capital", "");
        data.set(factionname + ".claimedareas.stronghold", "");
        data.set(factionname + ".claimedareas.outpost", "");

        owner.playSound(owner, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        adventure.sender(owner).sendMessage(Component.text("Faction has been created!", NamedTextColor.DARK_GREEN));
        new factionOwnership().update(true);
    }
}
