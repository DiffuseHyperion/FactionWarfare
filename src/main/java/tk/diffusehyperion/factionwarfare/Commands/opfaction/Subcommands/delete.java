package tk.diffusehyperion.factionwarfare.Commands.opfaction.Subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import tk.diffusehyperion.factionwarfare.Utility.factionOwnership;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.adventure;
import static tk.diffusehyperion.factionwarfare.FactionWarfare.data;

public class delete {
    public void delete(String faction, CommandSender sender, boolean confirm) {
        if (confirm) {
            data.set(faction, null);
            new factionOwnership().update(true);
            adventure.sender(sender).sendMessage(Component.text("Faction ", NamedTextColor.DARK_GREEN)
                    .append(Component.text(faction, NamedTextColor.GREEN))
                    .append(Component.text(" has been deleted.")));
        } else {
            adventure.sender(sender).sendMessage(Component.text("This action cannot be reversed. Are you sure you want to do this? Click ", NamedTextColor.DARK_RED)
                    .append(Component.text("here", NamedTextColor.RED).decorate(TextDecoration.UNDERLINED)
                            .clickEvent(ClickEvent.suggestCommand("/opfaction delete " + faction + " confirm")))
                    .append(Component.text(" to confirm this action.")));
        }
    }
}
