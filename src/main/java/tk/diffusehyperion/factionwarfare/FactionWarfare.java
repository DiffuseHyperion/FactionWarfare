package tk.diffusehyperion.factionwarfare;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import tk.diffusehyperion.factionwarfare.Commands.faction.faction;
import tk.diffusehyperion.factionwarfare.Commands.opfaction.opfaction;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class FactionWarfare extends JavaPlugin {

    public static BukkitAudiences adventure;
    public static File dataFile;
    public static YamlConfiguration data;

    @Override
    public void onEnable() {
        // Initialize an audiences instance for the plugin
        adventure = BukkitAudiences.create(this);

        getCommand("faction").setExecutor(new faction());
        getCommand("opfaction").setExecutor(new opfaction());

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        data = YamlConfiguration.loadConfiguration(dataFile);

    }

    @Override
    public void onDisable() {
        try {
            data.save(dataFile);
            adventure.console().sendMessage(Component.text("Data saved successfully! ", NamedTextColor.DARK_GREEN));
        } catch (IOException e) {
            adventure.console().sendMessage(Component.text("Error: ", NamedTextColor.RED)
                    .append(Component.text("Data could not be saved!", NamedTextColor.DARK_RED))
                    .append(Component.newline())
                    .append(Component.text("Error logs are in the console.", NamedTextColor.DARK_RED)));
            throw new RuntimeException(e);
        }

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }
}
