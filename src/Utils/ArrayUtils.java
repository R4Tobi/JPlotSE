package src.Utils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ArrayUtils {
    /**
     * Removes duplicate entries from an array.
     *
     * @param array The input array of any type.
     * @param <T>   The type of the array elements.
     * @return A new array without duplicate entries.
     */
    public static <T> T[] removeDuplicates(T[] array) {
        if (array == null) {
            return null;
        }

        // Use a LinkedHashSet to maintain the order and remove duplicates.
        Set<T> uniqueElements = new LinkedHashSet<>(Arrays.asList(array));

        // Convert the set back to an array of the same type as the input array.
        return uniqueElements.toArray(array.clone());
    }
}
