package warehouse;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Creates a map of SKUs and their locations in the inventory floor, based on a given input CSV file
 * that lists locations and SKUs.
 *
 */
public class InventoryLocations {
  /**
   * The map of all items in the warehouse and their locations. Set to protected visibility in case
   * a worker or another process manager needs access.
   */
  protected Map<Integer, ArrayList<Object>> itemMap;

  /**
   * Generates a map of all item SKUs and their locations in the warehouse based on inputFIle.
   * @param inputFile The CSV file based on which to stock the floor
   */
  public InventoryLocations(String inputFile) {
    // Generate an array of locations to get items from
    ArrayList<String> locations = FileHelper.getLinesFromFile(inputFile);
    // TODO Create exception to catch wrong file.

    // Create TreeMap
    this.itemMap = new TreeMap<Integer, ArrayList<Object>>();

    // Go through strings in array from file to populate map
    for (int i = 0; i < locations.size(); i++) {
      String item = locations.get(i);
      // Split each item by comma to get at each of its aspects
      String[] details = item.split(",");
      // Get details from String array
      String zone = details[0];
      int aisle = Integer.parseInt(details[1]);
      int rack = Integer.parseInt(details[2]);
      int level = Integer.parseInt(details[3]);
      // Turn the String[] into a location
      ArrayList<Object> location = detailsToLocation(zone, aisle, rack, level);
      // Assign SKU key with object array of details as values
      int sku = Integer.parseInt(details[4]);
      itemMap.put(sku, location);
    }
  }
  
  //TODO: Refactor so that ArrayList<Object> isn't passed around - set up method to translate to/from string
  public String static locationToString(String zone, int aisle, int rack, int level) {
	  // TODO
	  return "";
  }
  
  public ArrayList<Object> static stringToLocation(String locationString) {
	  // TODO
	  String[] locationString = line.split(" ");
      // Get details from string array
      String zone = locationString[0];
      int aisle = Integer.parseInt(locationString[1]);
      int rack = Integer.parseInt(locationString[2]);
      int level = Integer.parseInt(locationString[3]);
      // Make details into location
      ArrayList<Object> location = warehouseMap.detailsToLocation(zone, aisle, rack, level);
  }
  
  /**
   * Turns a set of input variables that represent a location in the warehouse 
   * into an array list for ease of transfer.
   * @param zone The zone of this location
   * @param aisle The aisle of this location
   * @param rack The rack of this location
   * @param level The level of this location
   * @return An array of these location parameters
   */
  public ArrayList<Object> detailsToLocation(String zone, int aisle, int rack, int level) {
    // Create an ArrayList to hold the location details
    ArrayList<Object> location = new ArrayList<Object>();
    location.add(zone);
    location.add(aisle);
    location.add(rack);
    location.add(level);
    // Return the completed array list
    return location;
  }
}
