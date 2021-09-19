package de.mcmdev.sui;

import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class SuiPlugin extends JavaPlugin {

    private boolean useClickSound;
    private Sound clickSound;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.useClickSound = getConfig().getBoolean("useClickSound", false);
        this.clickSound = Sound.valueOf(getConfig().getString("clickSound", "UI_BUTTON_CLICK"));
    }

    public boolean isUseClickSound() {
        return useClickSound;
    }

    public Sound getClickSound() {
        return clickSound;
    }
}
