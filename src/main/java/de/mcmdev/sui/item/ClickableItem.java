package de.mcmdev.sui.item;

import de.mcmdev.sui.Gui;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class ClickableItem<G extends Gui> {

    private final ItemStack itemStack;
    private BiConsumer<G, InventoryClickEvent> consumer;

    public ClickableItem(ItemStack itemStack, BiConsumer<G, InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ClickableItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public BiConsumer<G, InventoryClickEvent> getConsumer() {
        return consumer;
    }
}
