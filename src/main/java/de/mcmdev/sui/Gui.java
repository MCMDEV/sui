package de.mcmdev.sui;

import de.mcmdev.sui.item.ClickableItem;
import de.mcmdev.sui.slot.SimpleSlot;
import de.mcmdev.sui.slot.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A minecraft chest inventory interface. Gui's are abstract, because all the rendering is done in
 * {@link #redraw()}. There should be one instance of a gui per player.
 */
public abstract class Gui {

    private final GuiEventListener guiEventListener;
    private final Player player;
    private final Inventory handle;
    private final Map<Integer, Slot> slotMap = new ConcurrentHashMap<>();
    private final Component title;
    private boolean opened;
    private boolean firstDraw;

    /**
     * Create a new Gui of this type.
     *
     * @param player The gui's viewer and owner
     * @param title The title of the gui
     * @param rows The amount of rows in the gui
     */
    public Gui(Player player, Component title, int rows) {
        this.guiEventListener = new GuiEventListener();
        this.player = player;
        this.title = title;
        this.firstDraw = true;

        this.handle = Bukkit.createInventory(player, rows * 9, title);
        this.initSlot();
    }

    private void initSlot() {
        for (int i = 0; i < this.handle.getSize(); i++) {
            slotMap.put(i, new SimpleSlot(this, i, i % 9, i / 9));
        }
    }

    /**
     * This method should be implemented to call all the drawing methods. It is initially called
     * when the Gui is opened. The method can reference itself, but there should not be any
     * unconditional loops.
     */
    public abstract void redraw();

    /**
     * Returns the viewer of this Gui.
     *
     * @return The viewer
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the title of this Gui.
     *
     * @return The title
     */
    public Component getTitle() {
        return title;
    }

    public Inventory getHandle() {
        return handle;
    }

    /**
     * Retrieves a slot at a specified inventory slot index.
     *
     * @param index The inventory slot index
     * @return The slot
     */
    public Slot getSlot(int index) {
        return slotMap.get(index);
    }

    /**
     * Retrieves a slot using x and y position.
     *
     * @param x The x position, starting with zero.
     * @param y The y position, starting with zero.
     * @return The slot
     */
    public Slot getSlot(int x, int y) {
        return getSlot(x + (y * 9));
    }

    /**
     * Sets a clickable item at the specified inventory slot index.
     *
     * @param index The inventory slot index
     * @param item The item to be set
     */
    public void setItem(int index, ClickableItem item) {
        getSlot(index).setItem(item);
    }

    /**
     * Sets a clickable item using x and y position.
     *
     * @param x The slot x position
     * @param y The slot y position
     * @param item The item to be set
     */
    public void setItem(int x, int y, ClickableItem item) {
        getSlot(x, y).setItem(item);
    }

    /**
     * Retrieves the first empty slot in the inventory.
     *
     * @return The index of the slot
     */
    public int getFirstEmptySlot() {
        return this.handle.firstEmpty();
    }

    /**
     * Sets an item into the first empty slot in the inventory.
     *
     * @param item The item to be set
     */
    public void addItem(ClickableItem item) {
        int firstEmptySlot = getFirstEmptySlot();
        if (firstEmptySlot == -1) return;
        setItem(firstEmptySlot, item);
    }

    /**
     * Adds multiple items into the first slots in the inventory.
     *
     * @param items The items to be set
     */
    public void addItem(Iterable<ClickableItem> items) {
        for (ClickableItem item : items) {
            addItem(item);
        }
    }

    /**
     * Fill the inventory with an item, without replacing already used slots
     *
     * @param item The item to be used
     */
    public void fill(ClickableItem item) {
        this.fill(item, false);
    }

    /**
     * Fill the inventory with an item
     *
     * @param item The item to be used
     * @param force If true, replaces all slots, otherwise only places into unused slots
     */
    public void fill(ClickableItem item, boolean force) {
        for (int i = 0; i < this.handle.getSize(); i++) {
            if (force || this.handle.getItem(i) == null) {
                setItem(i, item);
            }
        }
    }

    /** Returns true, if the Gui is drawn the first time */
    public boolean isFirstDraw() {
        return firstDraw;
    }

    /**
     * Removes all items and click handlers from the gui.
     */
    public void clear() {
        this.handle.clear();
        this.slotMap.values().forEach(slot -> slot.setClickHandler(null));
    }

    /** Opens the Gui and registers all the listeners. */
    public void open() {
        if (this.opened) {
            throw new IllegalStateException("Gui is already opened.");
        }
        firstDraw = true;
        redraw();
        firstDraw = false;
        Bukkit.getPluginManager()
                .registerEvents(guiEventListener, JavaPlugin.getPlugin(SuiPlugin.class));
        player.openInventory(this.handle);
        this.opened = true;
    }

    /** Closes the Gui and unregisters all the listeners. */
    public void close() {
        if (player.getOpenInventory().getTopInventory() == handle) {
            player.closeInventory();
        }
        closeInternally();
    }

    private void closeInternally()  {
        opened = false;
        HandlerList.unregisterAll(guiEventListener);
        handle.clear();
    }

    private class GuiEventListener implements Listener {

        @EventHandler
        private void onInventoryClick(InventoryClickEvent event) {
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory() != handle) return;
            if (event.getWhoClicked() != player) return;
            if(!opened) return;
            event.setCancelled(true);

            int slotId = event.getRawSlot();

            if (slotId != event.getSlot()) return;

            Slot slot = slotMap.get(slotId);
            if (slot == null) {
                throw new IllegalStateException("This should not happen.");
            }

            slot.handleClick(event);
        }

        @EventHandler
        private void onInventoryClose(InventoryCloseEvent event)    {
            if (event.getInventory() != handle) return;
            if (event.getPlayer() != player) return;
            if(!opened) return;
            closeInternally();
        }

        @EventHandler
        private void onInventoryDrag(InventoryDragEvent event) {
            if (event.getInventory() != handle) return;
            if (event.getWhoClicked() != player) return;
            if(!opened) return;
            event.setCancelled(true);
        }

        @EventHandler
        public void onDeath(PlayerDeathEvent event) {
            if (event.getEntity() != player) return;
            if(!opened) return;
            close();
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent event) {
            if (event.getPlayer() != player) return;
            if(!opened) return;
            close();
        }

        @EventHandler
        public void onChangeWorld(PlayerChangedWorldEvent event) {
            if (event.getPlayer() != player) return;
            if(!opened) return;
            close();
        }

        @EventHandler
        public void onTeleport(PlayerTeleportEvent event) {
            if (event.getPlayer() != player) return;
            if(!opened) return;
            close();
        }
    }
}
