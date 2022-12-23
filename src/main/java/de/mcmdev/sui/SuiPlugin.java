package de.mcmdev.sui;

import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class SuiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        boolean useClickSound = getConfig().getBoolean("useClickSound", false);
        Sound clickSound = Sound.valueOf(getConfig().getString("clickSound", "UI_BUTTON_CLICK"));

        Sui.provideInstance(new Sui(this, useClickSound, clickSound));
    }
}
