package warehouse;

import java.util.Stack;

/**
 * A level on a rack in the inventory floor, extending Java's public Stack class. Superclass methods
 * not overridden: push(WarehouseItem item), pop(), peek(), empty(), search(WarehouseItem item).
 *
 */
@SuppressWarnings({"serial"})
public class Level<Warehouseitem> extends Stack<Warehouseitem> {
  /**
   * The maximum capacity of this level (depends on the item it holds).
   */
  private int capacity;
  /**
   * The minimum item number this level should contain. If this level is reached the level will need
   * replenishing.
   */
  private int min;


  /**
   * Construct a level.
   */
  public Level() {}


  /**
   * Construct a level with a given maximum capacity.
   */
  public Level(int capacity) {
    this.capacity = capacity;
  }


  /**
   * Construct a level with a given maximum capacity and given minimum capacity.
   */
  public Level(int capacity, int min) {
    this.capacity = capacity;
    this.min = min;
  }


  /**
   * @return The maximum capacity of this level.
   */
  public int getCapacity() {
    return capacity;
  }


  /**
   * Set the capacity of this level.
   * 
   * @param capacity The maximum capacity of this level.
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }


  /**Return the minimum capacity of this level; the amount at which to trigger restocking.
   * @return the minimum capacity of this level
   */
  public int getMin() {
    return min;
  }


  /**Set the minimum capacity of this level; the amount at which to tigger restocking.
   * Set the minimum capacity, at which point the level needs replenishing
   * 
   * @param min The minimum capacity of this level
   */
  public void setMin(int min) {
    this.min = min;
  }
  
  
  /**Return if this level's inventory is at or below the amount that triggers restocking.
   * @return If this level is at or below its minimum capacity (inventory amount)
   */
  public boolean atMin() {
    return this.size() <= this.capacity;
  }

}
