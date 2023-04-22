package de.mcmdev.sui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GuiItem {

    private final ItemStack itemStack;
    private Consumer<InventoryClickEvent> consumer;

    public GuiItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public GuiItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getConsumer() {
        return consumer;
    }
}
