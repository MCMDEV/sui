package de.mcmdev.sui;

import de.mcmdev.sui.toggle.ToggleGui;
import de.mcmdev.sui.toggle.ToggleItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SuiPlugin extends JavaPlugin {

    boolean sponge = false;

    @Override
    public void onEnable() {
        List<ToggleItem> toggleItems = new ArrayList<>();

        toggleItems.add(new ToggleItem(new ItemStack(Material.SPONGE), player -> sponge, (player, aBoolean) -> sponge = aBoolean));
        toggleItems.add(new ToggleItem(new ItemStack(Material.REDSTONE), player -> true, (player, aBoolean) -> {}));
        toggleItems.add(new ToggleItem(new ItemStack(Material.DIAMOND), player -> false, (player, aBoolean) -> {}));

        getCommand("test").setExecutor((sender, command, label, args) -> {
            Player player = (Player) sender;
            new ToggleGui(player, toggleItems).open();
            return true;
        });
    }
}
