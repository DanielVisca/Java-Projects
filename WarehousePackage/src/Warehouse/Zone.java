package warehouse;

import java.util.Arrays;

/**
 * A zone inside the warehouse, containing aisles of racks of items. Racks are stored in 
 * a 2D array where the first dimension is the aisle and the second is the rack.
 * 
 */
public class Zone {
  /**
   * A 2D array representing the aisles and the racks within each aisle.
   */
  private Rack[][] aisles;
  /**
   * The name of this zone, an alphabet number between A and Z.
   */
  private String name;
  
  
  /**
   * Construct the racks for this zone, and organize them into aisles.
   * Assume each level has a unique capacity; level capacities must be individually set later.
   * 
   * @param aisles The number of aisles
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   */
  public Zone(String name, int aisles, int racks, int numberOfLevels) {
    this.setName(name);
    
    // Setup the 2D array
    this.setAisles(new Rack[aisles][racks]);
    
    // Initiate all racks
    int aisleNumber = 0;
    int rackNumber = 0;
    // While aisles remain to be created
    while (aisleNumber < aisles) {
      // While racks remain to be created
      while (rackNumber < racks) {
        // Construct and add a rack to a position in the 2D array
        this.getAisles()[aisleNumber][rackNumber] = new Rack(numberOfLevels);
        aisleNumber++;
      }
      rackNumber++;
    }
  }
  
  
  /**
   * Construct the racks for this zone, and organize them into aisles.
   * Assume all levels share the same capacity (same item size).
   * 
   * @param aisles The number of aisles
   * @param racks The number of racks per aisle
   * @param numberOfLevels The number of levels per rack
   * @param levelCapacity The maximum number of items per level
   */
  public Zone(String name, int aisles, int racks, int numberOfLevels, 
                           int levelCapacity, int levelMin) {
    this.setName(name);
    
    // Setup the 2D array
    this.setAisles(new Rack[aisles][racks]);
    
    // Initiate all racks
    int aisleNumber = 0;
    int rackNumber = 0;
    // While aisles remain to be created
    while (aisleNumber < aisles) {
      // While racks remain to be created
      rackNumber = 0;
      while (rackNumber < racks) {
        // Construct and add a rack to a position in the 2D array
        this.aisles[aisleNumber][rackNumber] = 
                         new Rack(numberOfLevels, levelCapacity, levelMin);
        rackNumber++;
      }
      aisleNumber++;
    }
  }
  

  /**
   * Get the name of this zone.
   * @return The name of this zone.
   */
  public String getName() {
    return name;
  }

  
  /**
   * Set the name of this zone.
   * @param name The name of this zone
   */
  public void setName(String name) {
    this.name = name;
  }
  

  /**
   * Get the aisles in this zone.
   * @return The aisles in this zone as a 2D array.
   */
  public Rack[][] getAisles() {
    return aisles;
  }

  /**
   * Set the aisles in this zone.
   * @param aisles The aisles in this zone.
   */
  public void setAisles(Rack[][] aisles) {
    this.aisles = aisles;
  }
  
  
  /**
   * Set all levels on this rack to the same capacity.
   * 
   * @param aisle The aisle of this level
   * @param rack The rack of this level
   * @param capacity The maximum capacity of all levels on this rack
   */
  public void setRackCapacity(int aisle, int rack, int capacity) {
    aisles[aisle][rack].setAllLevelsCapacity(capacity);
  }
  
  
  /**
   * Remove an item from a given location.
   * @param aisle The aisle the item is located in
   * @param rack The rack the item is located on
   * @param level The level that item is located on
   */
  public void removeItem(int aisle, int rack, int level) {
    aisles[aisle][rack].removeItem(level);
  }
 
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.deepHashCode(aisles);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Zone other = (Zone) obj;
    if (!Arrays.deepEquals(aisles, other.aisles)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }


  /**
   * Main loop for testing.
   */
  public static void main(String[] args) {
    Zone z1 = new Zone("A", 1, 2, 3, 4, 5);
    Zone z2 = new Zone("B", 1, 2, 4);
    System.out.println(z1.equals(z2));
  }
  
}
