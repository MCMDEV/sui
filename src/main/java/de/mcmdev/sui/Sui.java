package de.mcmdev.sui;

import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class Sui {

    private static Sui instance;

    public static void provideInstance(Sui sui) {
        instance = sui;
    }

    public static Sui getInstance() {
        return instance;
    }

    private final JavaPlugin plugin;
    private final boolean useClickSound;
    private final Key clickSound;

    public Sui(JavaPlugin plugin, boolean useClickSound, Key clickSound) {
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

    public Key getClickSound() {
        return clickSound;
    }
}
