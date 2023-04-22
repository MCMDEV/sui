package de.mcmdev.sui;

import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class SuiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        boolean useClickSound = getConfig().getBoolean("useClickSound", false);
        Key clickSound = Key.key(getConfig().getString("clickSound", "minecraft:ui.button.click"));

        Sui.provideInstance(new Sui(this, useClickSound, clickSound));
    }
}
