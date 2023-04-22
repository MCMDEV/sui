package de.mcmdev.sui.gui;

import de.mcmdev.sui.item.GuiItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Slot {

    private final Gui gui;
    private final int id;
    private final int x;
    private final int y;
    private Consumer<InventoryClickEvent> clickHandler;

    public Slot(Gui gui, int id, int x, int y) {
        this.gui = gui;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Gui getGui() {
        return gui;
    }

    public int getIndex() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void removeItem() {
        this.gui.getHandle().clear(id);
    }

    public ItemStack getItemStack() {
        return this.gui.getHandle().getItem(id);
    }

    public Consumer<InventoryClickEvent> getClickHandler() {
        return clickHandler;
    }

    public void setItem(GuiItem item) {
        setItemStack(item.getItemStack());
        if (item.getConsumer() != null) {
            setClickHandler(item.getConsumer());
        }
    }

    public void setItemStack(ItemStack item) {
        this.gui.getHandle().setItem(id, item);
    }

    public void setClickHandler(Consumer<InventoryClickEvent> consumer) {
        this.clickHandler = consumer;
    }

    public void handleClick(InventoryClickEvent event) {
        if (clickHandler == null) return;
        clickHandler.accept(event);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
