package de.mcmdev.sui.mask;

import de.mcmdev.sui.Gui;
import de.mcmdev.sui.slot.Slot;

import java.util.*;
import java.util.function.Function;

/** A mask that can be used to mark slots using a string representation of each line. */
public class Mask<G extends Gui> {

    private final Map<Character, List<Slot<G>>> slotMap = new HashMap<>();

    Mask(G gui, Builder<G> builder) {
        builder.chars.forEach(
                (point, character) -> {
                    List<Slot<G>> slots =
                            slotMap.computeIfAbsent(character, unused -> new LinkedList<>());
                    slots.add((Slot<G>) gui.getSlot(point.x, point.y));
                });
    }

    public static Function<Integer, Comparator<Point>> HORIZONTAL = rows -> Comparator.comparingInt(value -> value.getX() + (value.getY() * 9));
    public static Function<Integer, Comparator<Point>> VERTICAL = rows -> Comparator.comparingInt(value -> value.getY() + (value.getX() * rows));

    public static <G extends Gui> Builder<G> builder(int rows) {
        return new Builder<>(rows, HORIZONTAL);
    }

    public static <G extends Gui> Builder<G> builder(int rows, Function<Integer, Comparator<Point>> comparator) {
        return new Builder<>(rows, comparator);
    }

    /**
     * Returns a map with all mapped characters and a list of slots which have been mapped to this
     * character.
     *
     * @return The map
     */
    public Map<Character, List<Slot<G>>> getSlots() {
        return slotMap;
    }

    /**
     * Retrieves all slots that have been mapped to the specified character.
     *
     * @param c The character to lookup
     * @return The slot list
     */
    public List<Slot<G>> getSlots(char c) {
        return slotMap.get(c);
    }

    public static class Builder<G extends Gui> {
        private final Map<Point, Character> chars;
        private int rows = 0;

        Builder(int rows, Function<Integer, Comparator<Point>> slotComparator) {
            this.chars = new TreeMap<>(slotComparator.apply(rows));
        }

        /**
         * Returns the line counter.
         *
         * @return Amount of rows set
         */
        public int getRows() {
            return rows;
        }

        /**
         * Counts the number of occurrences of a character in the mask.
         *
         * @param lookupCharacter The character to lookup
         * @return The amount of characters
         */
        public int countChars(char lookupCharacter)   {
            int counter = 0;
            for (Character value : chars.values()) {
                if(value == lookupCharacter) counter++;
            }
            return counter;
        }

        /**
         * Sets the pattern for the current line and increments the line counter.
         *
         * @param pattern The pattern
         * @return The builder itself
         */
        public Builder<G> pattern(String pattern) {
            char[] patternChars = pattern.toCharArray();
            for (int i = 0; i < patternChars.length; i++) {
                this.chars.put(new Point(i, rows), patternChars[i]);
            }

            rows++;
            return this;
        }

        /**
         * Builds the Mask for the given gui.
         *
         * @param gui The gui to use for building the mask
         * @return The mask
         */
        public Mask<G> build(G gui) {
            return new Mask<>(gui, this);
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
