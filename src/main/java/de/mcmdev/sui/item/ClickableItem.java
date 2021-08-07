package de.mcmdev.sui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> consumer;

    public ClickableItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ClickableItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getConsumer() {
        return consumer;
    }
}
