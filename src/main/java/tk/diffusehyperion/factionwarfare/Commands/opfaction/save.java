package tk.diffusehyperion.factionwarfare.Commands.opfaction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

import static tk.diffusehyperion.factionwarfare.FactionWarfare.*;

public class save {
    public void save(CommandSender sender) {
        try {
            data.save(dataFile);
            adventure.sender(sender).sendMessage(Component.text("Data saved successfully! ", NamedTextColor.DARK_GREEN));
        } catch (IOException e) {
            adventure.sender(sender).sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("Data could not be saved!", NamedTextColor.DARK_RED))
                    .append(Component.newline())
                    .append(Component.text("Error logs are in the console.", NamedTextColor.DARK_RED)));
            throw new RuntimeException(e);
        }
    }
}
