package src.Utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ListUtils {
    /**
     * Removes duplicate entries from a list.
     *
     * @param list The input list of any type.
     * @param <T>  The type of the list elements.
     * @return A new list without duplicate entries.
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null) {
            return null;
        }

        // Use a LinkedHashSet to maintain the order and remove duplicates.
        return new ArrayList<>(new LinkedHashSet<>(list));
    }
}
