package warehouse;

import java.util.ArrayList;

/**
 * A rack (shelving unit) in an aisle in a zone of the warehouse. The rack contains levels,
 * identified by their index number in an ArrayList. Each level contains a unique item type, and
 * therefore has a unique maximum capacity.
 *
 */
public class Rack {
  /**
   * The levels on this rack, which are stacks of warehouse items.
   */
  private ArrayList<Level<WarehouseItem>> levels = new ArrayList<Level<WarehouseItem>>();

  /**
   * @return The ArrayList of levels on this rack.
   */
  public ArrayList<Level<WarehouseItem>> getLevels() {
    return levels;
  }
  
  /** Get the number of levels in this rack.
   * @return int
   */
  public int getNumLevels() {
    return levels.size();
  }


  /**
   * Construct a new rack and construct levels to fill it with.
   * 
   * @param numberOfLevels The number of levels on this rack
   */
  public Rack(int numberOfLevels) {
    for (int n = 0; n < numberOfLevels; n++) {
      this.levels.add(new Level<WarehouseItem>());
    }
  }


  /**
   * Construct a new rack and construct levels to fill it with. Assign the same maximum capacity to
   * all levels.
   * 
   * @param numberOfLevels The number of levels on this rack
   * @param capacity The maximum capacity of each level.
   */
  public Rack(int numberOfLevels, int capacity) {
    for (int n = 0; n < numberOfLevels; n++) {
      this.levels.add(new Level<WarehouseItem>(capacity));
    }
  }


  /**
   * Construct a new rack and construct levels to fill it with. Assign the same maximum & minimum
   * capacities to all levels.
   * 
   * @param numberOfLevels The number of levels on this rack
   * @param capacity The maximum capacity of each level.
   */
  public Rack(int numberOfLevels, int capacity, int min) {
    for (int n = 0; n < numberOfLevels; n++) {
      this.levels.add(new Level<WarehouseItem>(capacity, min));
    }
  }


  /**
   * Reduces by 1 the size of the level where an item has been removed.
   */
  public void addItem(int levelNumber, WarehouseItem item) {
    levels.get(levelNumber).push(item);
  }


  /**
   * Reduces by 1 the size of the level where an item has been removed.
   */
  public void removeItem(int levelNumber) {
    if (levels.get(levelNumber).size() > 0) {
      levels.get(levelNumber).pop();
    } else {
      FileHelper.logEvent("Error removing inventory, none stocked");
    }
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((levels == null) ? 0 : levels.hashCode());
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
    Rack other = (Rack) obj;
    if (levels == null) {
      if (other.levels != null) {
        return false;
      }
    } else if (!levels.equals(other.levels)) {
      return false;
    }
    return true;
  }

  /**
   * Return the number of items at a given level on the rack.
   * 
   * @param levelNumber The level on rack to check the inventory level of
   * @return The number of items currently on this level
   */
  public int getInventory(int levelNumber) {
    return levels.get(levelNumber).size();
  }
  
  
  /**
   * Set the inventory at each empty level to full. Used to initiate a rack and/or level(s).
   * 
   */
  public void setInventory() {
    // Go through each level and stock empty ones to full
    for (Level<WarehouseItem> level : levels) {
      if (level.size() == 0) {
        replenish(levels.indexOf(level), 0);
      }
    }
  }


  /**
   * Set the maximum capacity of a level.
   * 
   * @param levelNumber The level whose capacity must be set
   * @param capacity The maximum capacity to set that level to
   */
  public void setLevelCapacity(int levelNumber, int capacity) {
    levels.get(levelNumber).setCapacity(capacity);
  }


  /**
   * Set the maximum capacity of all levels on this rack.
   * 
   * @param capacity The maximum capacity to set that level to
   */
  public void setAllLevelsCapacity(int capacity) {
    for (int i = 0; i < getLevels().size(); i++) {
      levels.get(i).setCapacity(capacity);
    }
  }


  /**
   * Fill a given level to capacity with a specific sku type.
   * 
   * @param sku The sku of the item to replenish on level
   */
  public void replenish(int level, int sku) {
    // Calculate the number of items needed to reach level capacity
    int toFill = levels.get(level).getCapacity() - levels.get(level).size();
    // Create enough items based on type sku to fill level to capacity
    for (int i = 0; i < toFill; i++) {
      WarehouseItem item = new WarehouseItem(sku);
      levels.get(level).push(item);
    }
  }
}
