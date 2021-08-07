package de.mcmdev.sui.mask;

import de.mcmdev.sui.Gui;
import de.mcmdev.sui.slot.Slot;

import java.util.*;

/** A mask that can be used to mark slots using a string representation of each line. */
public class Mask {

    private final Map<Character, List<Slot>> slotMap = new HashMap<>();

    Mask(Gui gui, Builder builder) {
        builder.chars.forEach(
                (point, character) -> {
                    List<Slot> slots =
                            slotMap.computeIfAbsent(character, unused -> new LinkedList<>());
                    slots.add(gui.getSlot(point.x, point.y));
                });
    }

    public static Builder builder(int rows) {
        return new Builder(rows, (o1, o2) -> o1.getX() + (o1.getY() * 9) - o2.getX() + (o2.getY() * 9));
    }

    public static Builder builder(int rows, Comparator<Point> comparator) {
        return new Builder(rows, comparator);
    }

    /**
     * Returns a map with all mapped characters and a list of slots which have been mapped to this
     * character.
     *
     * @return The map
     */
    public Map<Character, List<Slot>> getSlots() {
        return slotMap;
    }

    /**
     * Retrieves all slots that have been mapped to the specified character.
     *
     * @param c The character to lookup
     * @return The slot list
     */
    public List<Slot> getSlots(char c) {
        return slotMap.get(c);
    }

    public static class Builder {
        private final int rows;
        private final Map<Point, Character> chars;
        private int currentRow = 0;

        Builder(int rows, Comparator<Point> slotComparator) {
            this.rows = rows;
            this.chars = new TreeMap<>(slotComparator);
        }

        /**
         * Sets the pattern for the current line and increments the line counter.
         *
         * @param pattern The pattern
         * @return The builder itself
         */
        public Builder pattern(String pattern) {
            char[] patternChars = pattern.toCharArray();
            for (int i = 0; i < patternChars.length; i++) {
                this.chars.put(new Point(i, currentRow), patternChars[i]);
            }
            if (currentRow < rows) {
                currentRow++;
            }
            return this;
        }

        /**
         * Builds the Mask for the given gui.
         *
         * @param gui The gui to use for building the mask
         * @return The mask
         */
        public Mask build(Gui gui) {
            return new Mask(gui, this);
        }
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
