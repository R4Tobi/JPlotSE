package src.Utils;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static String arrayListWithArrayToString(ArrayList<Double[]> list) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            result.append(Arrays.toString(list.get(i)));
            if (i < list.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
