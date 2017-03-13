package warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
// import java.util.Queue;

/**
 * The manager of inventory, including WarehouseItems, their locations, and their amount in the
 * floor of the inventory. Also handles automated replenish requests.
 *
 */

public class InventoryManager extends ProcessManager {
  private ArrayList<Zone> floor = new ArrayList<Zone>();
  private static String zoneNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  /**
   * A map of all items with sku as key and an ArrayList of location details as the value Example
   * with one item: {23, ["A", 1, 2, 0]}, where the integers are aisle, rack, and level. Set to
   * protected in case another manager or a worker needs access to it.
   */
  protected InventoryLocations warehouseMap;

  /**
   * Get the zones in the floor.
   * 
   * @return The zones in the floor.
   */
  public ArrayList<Zone> getFloor() {
    return floor;
  }

  /**
   * Get the string of the zone names (the alphabet).
   * 
   * @return The string that lists the alphabet for naming zones.
   */
  public static String getZoneNames() {
    return zoneNames;
  }
  

  /**
   * Return a zone with the given name.
   * 
   * @param name The name of the zone in the floor
   */
  public Zone getZone(String name) {
    for (Zone z : getFloor()) {
      if (z.getName().equals(name)) {
        return z;
      }
    }
    return null;
  }


  /**
   * Create and add a zone to the warehouse floor. Assume all levels have same capacity.
   * @param aisles The number of aisles
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   * @param levelCapacity The maximum number of items per level
   * @param levelMin The minimum number of items per level
   */
  public void addZone(int aisles, int racks, int numberOfLevels, int levelCapacity, int levelMin) {
    // Find the last letter that has already been assigned to a zone
    // String name = getZoneNames().substring(getFloor().size());
    String name = String.valueOf(getZoneNames().charAt(getFloor().size()));

    // Create, name, and add the zone to the warehouse floor
    Zone zone = new Zone(name, aisles, racks, numberOfLevels, levelCapacity, levelMin);
    getFloor().add(zone);
  }


  /**
   * Create and add a zone to the warehouse floor. Assume each level has a different capacity.
   * @param aisles The number of aisles
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   */
  public void addZone(int aisles, int racks, int numberOfLevels) {
    // Find the last letter that has already been assigned to a zone
    // String name = getZoneNames().substring(getFloor().size()); // This substringed the whole
    // alphabet after the given index
    String name = String.valueOf(getZoneNames().charAt(getFloor().size()));
    // Create, name, and add the zone to the warehouse floor
    Zone zone = new Zone(name, aisles, racks, numberOfLevels);
    getFloor().add(zone);
  }


  /**
   * Remove a given zone from the warehouse floor.
   * 
   * @param zoneName The name of the zone to be removed.
   */
  public void removeZone(String zoneName) {
    // Boolean to check successful removal
    boolean removed = false;
    // Find index of zone to remove
    for (int i = 0; i < getFloor().size(); i++) {
      // If that zone can be found in the floor
      if (getFloor().get(i).getName().equals(zoneName)) {
        // Remove that zone
        this.getFloor().remove(i);
        removed = true;
      }
    }
    // Logging the event or error
    if (removed) {
      FileHelper.logEvent("Zone " + zoneName + " successfully removed.");
    } else {
      FileHelper.logEvent("Zone " + zoneName + " was not found!");
    }
  }


  /**
   * Construct an InventoryManager and the floor it manages, using a given traversal file as a guide
   * to the locations of the items. Assume all levels have the same maximum and minimum capacities.
   * 
   * @param system The WarehouseSystem this manager communicates with
   * @param traversalFile The file to generate a map of the inventory
   * @param zones The number of zones of the floor of the warehouse.
   * @param aisles The number of aisles per zone
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   * @param levelCapacity The maximum number of items per level
   * @param levelMin The minimum number of items per level
   */
  public InventoryManager(WarehouseSystem system, String traversalFile, int zones, int aisles,
      int racks, int numberOfLevels, int levelCapacity, int levelMin) {
    // Connect to WarehouseSystem
    super(system);
    // Instantiate and name each zone with a letter of the alphabet
    for (int z = 0; z < zones; z++) {
      this.addZone(aisles, racks, numberOfLevels, levelCapacity, levelMin);
    }
    // Generate a map of the items and their locations
    this.warehouseMap = new InventoryLocations(traversalFile);
    // Fills levels with inventory
    stockFloor("initial.csv");
  }

/**
   * Construct an InventoryManager and the floor it manages, using a given traversal file as a guide
   * to the locations of the items. Assume each level has a unique capacity, which must be set
   * later!
   * 
   * @param system The WarehouseSystem this manager communicates with
   * @param traversalFile The file to generate a map of the inventory
   * @param zones The number of zones of the floor of the warehouse
   * @param aisles The number of aisles per zone
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   */
  public InventoryManager(WarehouseSystem system, String traversalFile, int zones, int aisles,
      int racks, int numberOfLevels) {
    // Connect to WarehouseSystem
    super(system);
    // Instantiate and name each zone with a letter of the alphabet
    for (int z = 0; z <= zones; z++) {
      this.addZone(aisles, racks, numberOfLevels);
    }
    // Generate a map of the items and their locations
    this.warehouseMap = new InventoryLocations(traversalFile);
    // Fills levels with inventory
    stockFloor("initial.csv");
  }

/**
   * Stock all levels in the warehouse with a number of warehouse items, according to the input CSV
   * file, initialFile.
   * 
   * @param initialFile The input file, a CSV of all locations and their inventory levels
   */
  public void stockFloor(String initialFile) {
    // Turn input file into an Array of strings
    ArrayList<String> locationStock = FileHelper.getLinesFromFile(initialFile);

    // Stock levels not listed in initialFile to full
    stockEmptyLevels();
    
    // Iterate through each string to get location data for less than full levels
    for (int i = 0; i < locationStock.size(); i++) {
      // Turn the first four parts of the string into location
      String line = locationStock.get(i);
      String[] locationString = line.split(",");
      // Get details from string array
      String zone = locationString[0];
      int aisle = Integer.parseInt(locationString[1]);
      int rack = Integer.parseInt(locationString[2]);
      int level = Integer.parseInt(locationString[3]);
      // Make details into location
      ArrayList<Object> location = warehouseMap.detailsToLocation(zone, aisle, rack, level);
      // Create warehouse items based on amount and stock in that location
      int amount = Integer.parseInt(locationString[4]);
      int sku = getSkuFromLocation(location);
      Rack rackLocation = getRackAtLocation(zone, aisle, rack);
      rackLocation.getLevels().get(level).clear();
      for (int n = 0; n < amount; n++) {
        WarehouseItem item = new WarehouseItem(sku);
        rackLocation.addItem(level, item);
      }
      
      // Replenish racks as necessary (otherwise orders stall)
      checkAndReplenish(zone, aisle, rack, level);
    }
  }

/**
   * Fills all empty levels in the inventory floor.
   */
  public void stockEmptyLevels() {
    for (Zone eachZone : floor) {
      for (Rack[] eachAisle : eachZone.getAisles()) {
        for (Rack eachRack : eachAisle) {
          eachRack.setInventory();
        }
      }
    }
  }

/** 
   * Report the stock of all levels in the warehouse, and output to final.csv
   */
  public void reportFloorStock() {
    ArrayList<String> locationStock = new ArrayList<String>();
    
    for (Zone zone: floor) {
      Rack[][] aisles = zone.getAisles();

      for (int i = 0; i < aisles.length; i++) {
        for (int j = 0; j < aisles[i].length; j++) {
          Rack rack = aisles[i][j];
          for (int k = 0; k < rack.getNumLevels(); k++) {
            if (rack.getInventory(k) < rack.getLevels().get(k).getCapacity()) {
              locationStock.add(zone.getName() + ","
                  + i + "," + j + "," + k + ","  + rack.getInventory(k));
            }
          }
        }
      }
    }
    
    /*for (String s: locationStock) {
      System.out.println(s);
    }*/

    FileHelper.writeLinesToFile("final.csv", 
                                locationStock.toArray(new String[locationStock.size()]));
  }

/**
   * Remove an item from the associated location according to its sku number. After removal, check
   * if replenishing is needed.
   * 
   * @param sku The sku number of this item.
   */
  public void removeInventory(int sku) {
    // Look up location from the warehouseMap
    ArrayList<Object> location = getLocationFromsku(sku);
    // Go to location and remove item
    Rack rack = getRackAtLocation((String) location.get(0), (Integer) location.get(1),
        (Integer) location.get(2));
    rack.removeItem((Integer) location.get(3));
    // Check for replenishing
    checkAndReplenish((String) location.get(0), (Integer) location.get(1),
        (Integer) location.get(2), (Integer) location.get(3));
  }


  /**
   * Return the rack at the given location in the floor.
   * 
   * @param zone The zone in the floor
   * @param aisle The aisle number
   * @param rack The rack number
   * @return The Rack at this location
   */
  public Rack getRackAtLocation(String zone, int aisle, int rack) {
    // Zones are indexed by Zone object and not their names, this returned -1, added simple helper
    // method
    return getZone(zone).getAisles()[aisle][rack];
  }

/**
   * Return the location of this item based on its sku. The location is returned as an object array
   * with the zone name as a String, and aisle, rack, level as integers.
   * 
   * @param sku The sku of the item whose location must be returned
   * @return The location of this item, listing its location attributes in an ArrayList
   */
  public ArrayList<Object> getLocationFromsku(int sku) {
    return this.warehouseMap.itemMap.get(sku);
  }


  /**
   * Return the sku at this location.
   * 
   * @param location The location whose sku must be found, as an array of strings and integers
   * @return The sku at this location
   */
  public int getSkuFromLocation(ArrayList<Object> location) {
    // Dummy variable to satisfy Java type-checking
    Integer sku = new Integer(1);
    for (Entry<Integer, ArrayList<Object>> entry : warehouseMap.itemMap.entrySet()) {
      if (location.equals(entry.getValue())) {
        sku = entry.getKey();
      }
    }
    return sku;
  }


  /**
   * Check a given location to see if it needs replenishing. If it does, give the job to a
   * replenisher.
   * 
   * @param zone The zone of location to check
   * @param aisle The aisle of location to check
   * @param rack The rack of location to check
   * @param level The level of location to check
   */
  public void checkAndReplenish(String zone, int aisle, int rack, int level) {
    Level<WarehouseItem> toCheck = getRackAtLocation(zone, aisle, rack).getLevels().get(level);
    // Check if that level needs replenishing
    if (toCheck.atMin()) {
      ArrayList<Object> location = new ArrayList<Object>(Arrays.asList(zone, aisle, rack, level));
      int sku = getSkuFromLocation(location);
      // Create a replenishing job, assign to a worker, and add to queue
      Job job = new Job(sku, zone, aisle, rack, level);
      
      // Make sure this job isn't already queued
      boolean jobExists = false;
      for (Job j : jobsToDo) {
        if (j.getLocation().equals(job.getLocation())) {
          jobExists = true;
          break;
        }
      }
      if (!jobExists) {
        this.queueJob(job);
        FileHelper.logEvent("Item " + sku + " at " + job.getLocation() + " needs replenishing");
      }
    }
  }



  /**
   * Replenishes the level at the given location, based on these parameters, with a
   * warehouse item with the given SKU.
   * @param zone The zone of level to replenish
   * @param aisle The aisle of level to replenish
   * @param rack The rack of level to replenish
   * @param level The level to replenish
   * @param sku The SKU of the item to be replenished
   */
  public void replenishLevel(String zone, int aisle, int rack, int level, int sku) {
    // Replenish level in location of replenishing job
    Rack toReplenish = getRackAtLocation(zone, aisle, rack);
    toReplenish.replenish(level, sku);
  }



  /**
   * Remove completed replenishing job from job queue.
   * 
   * @param worker The worker who has completed the job
   */
  public void jobComplete(Worker worker) {
    // Get location from location string of job
    String[] location = worker.currentJob.getLocation().split(" ");
    String zone = (String) location[0];
    Integer aisle = Integer.parseInt(location[1]);
    Integer rack = Integer.parseInt(location[2]);
    Integer level = Integer.parseInt(location[3]);
    // Get sku from job's sku list
    Integer sku = worker.currentJob.getSkus()[0];

    super.jobComplete(worker);
    system.replenishInventory(zone, aisle, rack, level, sku);
  }

  /**
   * Create a new worker.
   * 
   * @param name of the worker
   * @return Worker
   */
  public Worker hireWorker(String name) {
    Worker replenisher = new Replenisher(name, this);
    addWorker(replenisher);
    return replenisher;
  }

  /**
   * Give new job to worker or instruct them to continue.
   * 
   * @param name name of the worker
   */
  public void nextJob(String name) {
    Worker worker = getWorker(name);
    if (worker.getCurrentJob() == null) {
      if (jobsToDo.size() == 0) {
        FileHelper.logEvent("Error no job to do");
        return;
      }

      // Find the job corresponding to the replenish location
      for (Job job : jobsToDo) {
        if (worker.getStatus().contains(job.getLocation())) {
          jobsToDo.remove(job);
          worker.startNextJob(job);
          worker.nextTask();
          jobsInProgress.add(job);
          job.setWorker(worker);
          break;
        }
      }
    } else {
      worker.nextTask();
    }
  }

}
