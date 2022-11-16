package tk.diffusehyperion.factionwarfare.Utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.*;

public class factionOwnership {
    public static HashMap<String, Triplet<String, List<String>, List<String>>> factionOwnership = new HashMap<>();
    //                 Faction Name,      Owner,  Moderators,   Members
    public static HashMap<String, Pair<FactionRoles, String>> playerRoles = new HashMap<>();
    //                 Player name,   Faction Role, Faction
    public enum FactionRoles {
        owner,
        moderator,
        member
    }
    public void update(boolean silent) {
        if (Objects.nonNull(factionOwnership)) {
            factionOwnership.clear();
        }
        for (String factionname : data.getKeys(false)) {
            factionOwnership.put(factionname, new Triplet<>(data.getString(factionname + ".members.owner")
                    , data.getStringList(factionname + ".members.moderators")
                    , data.getStringList(factionname + ".members.members")));
            if (!silent) {
                adventure.console().sendMessage(Component.text("Registering faction ", NamedTextColor.DARK_GREEN)
                        .append(Component.text(factionname, NamedTextColor.GREEN)));
            }

            playerRoles.put(data.getString(factionname + ".members.owner"), new Pair<>(FactionRoles.owner, factionname));
            for (String modname : data.getStringList(factionname + ".members.moderators")) {
                playerRoles.put(modname, new Pair<>(FactionRoles.moderator, factionname));
            }
            for (String membername : data.getStringList(factionname + ".members.members")) {
                playerRoles.put(membername, new Pair<>(FactionRoles.member, factionname));
            }
        }
    }
}
