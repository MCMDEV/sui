package de.mcmdev.sui.slot;

import de.mcmdev.sui.Gui;
import de.mcmdev.sui.item.ClickableItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class SimpleSlot<G extends Gui> implements Slot<G> {

    private final G gui;
    private final int id;
    private final int x;
    private final int y;
    private BiConsumer<G, InventoryClickEvent> clickHandler;

    public SimpleSlot(G gui, int id, int x, int y) {
        this.gui = gui;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public G getGui() {
        return gui;
    }

    @Override
    public int getIndex() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void removeItem() {
        this.gui.getHandle().clear(id);
    }

    @Override
    public ItemStack getItem() {
        return this.gui.getHandle().getItem(id);
    }

    @Override
    public void setItem(ClickableItem<G> item) {
        setItem(item.getItemStack());
        if (item.getConsumer() != null) {
            setClickHandler(item.getConsumer());
        }
    }

    @Override
    public void setItem(ItemStack item) {
        this.gui.getHandle().setItem(id, item);
    }

    @Override
    public void setClickHandler(BiConsumer<G, InventoryClickEvent> consumer) {
        this.clickHandler = consumer;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (clickHandler == null) return;
        clickHandler.accept(gui, event);
    }

    @Override
    public String toString() {
        return "SimpleSlot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
