package de.mcmdev.sui.slot;

import de.mcmdev.sui.Gui;
import de.mcmdev.sui.item.ClickableItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface Slot {

    /**
     * Returns the Gui which uses this slot.
     *
     * @return The gui
     */
    Gui getGui();

    /**
     * Returns the inventory slot index this slot represents.
     *
     * @return The slot index
     */
    int getIndex();

    /**
     * Returns the X position in the Gui of this slot.
     *
     * @return The x position
     */
    int getX();

    /**
     * Returns the Y position in the Gui of this slot.
     *
     * @return The y position
     */
    int getY();

    /** Removes the Item from this slot, without changing the click handler. */
    void removeItem();

    /**
     * Returns the item in this slot
     *
     * @return The item
     */
    ItemStack getItem();

    /**
     * Sets a clickable item to this slot.
     *
     * @param item The clickable item
     */
    void setItem(ClickableItem item);

    /**
     * Sets an ItemStack without changing the click handler to this slot.
     *
     * @param item The ItemStack
     */
    void setItem(ItemStack item);

    /**
     * Sets the click handler for this slot. The consumer is called when the slot is clicked.
     *
     * @param consumer The click consumer.
     */
    void setClickHandler(Consumer<InventoryClickEvent> consumer);

    void handleClick(InventoryClickEvent event);
}
