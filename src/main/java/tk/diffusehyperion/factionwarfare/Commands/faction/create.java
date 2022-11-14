package tk.diffusehyperion.factionwarfare.Commands.faction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.FactionWarfare.data;

public class create {
    public void create(String factionname, Player owner) {
        if (data.contains(factionname)) {
            adventure.sender(owner).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("Name is already taken.", NamedTextColor.DARK_RED)));
            return;
        }
        for (String s : data.getKeys(false)) {
            if (Objects.equals(data.getString(s + ".members.owner"), owner.getDisplayName()) ||
                    data.getStringList(s + ".members.moderators").contains(owner.getDisplayName()) ||
                    data.getStringList(s + ".members.members").contains(owner.getDisplayName())) {

                adventure.sender(owner).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                        .append(Component.text("You are already in a faction.", NamedTextColor.DARK_RED)));
                return;
            }
        }
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
    }
}
