package de.mcmdev.sui;

import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class Sui {

    private static Sui instance;

    public static void provideInstance(Sui sui) {
        instance = sui;
    }

    static Sui getInstance() {
        return instance;
    }

    private final JavaPlugin plugin;
    private final boolean useClickSound;
    private final Sound clickSound;

    public Sui(JavaPlugin plugin, boolean useClickSound, Sound clickSound) {
        this.plugin = plugin;
        this.useClickSound = useClickSound;
        this.clickSound = clickSound;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public boolean isUseClickSound() {
        return useClickSound;
    }

    public Sound getClickSound() {
        return clickSound;
    }
}
