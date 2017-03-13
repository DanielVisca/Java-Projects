package warehouse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used solely for optimizing lists of SKUs for best picking order.
 *
 */
public class WarehousePicking {

  // Cache of locations
  private static HashMap<Integer, String> locations = null;

  /**
   * Based on the Integer SKUs in List 'skus', return a List of locations, where each location is a
   * String containing 5 pieces of information: the zone character (in the range ['A'..'B']), the
   * aisle number (an integer in the range [0..1]), the rack number (an integer in the range
   * ([0..2]), and the level on the rack (an integer in the range [0..3]), and the SKU number.
   * 
   * @param skus the list of SKUs to retrieve.
   * @return the List of locations.
   */
  public static String[] optimize(int[] skus) {
    String[] optimizedLocations = new String[skus.length];

    // Cache locations
    if (locations == null) {
      locations = new HashMap<Integer, String>();
      ArrayList<String> lines = FileHelper.getLinesFromFile("traversal_table.csv");
      // Store locations by sku key
      for (String line: lines) {
        int sku = Integer.parseInt(line.split(",")[4]);
        locations.put(sku, line);
      }
    }

    // Go through each SKU
    for (int i = 0; i < skus.length; i++) {
      // Get locations from sku
      optimizedLocations[i] = locations.get(skus[i]);
    }

    return optimizedLocations;
  }
}
