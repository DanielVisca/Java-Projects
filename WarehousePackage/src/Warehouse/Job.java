package warehouse;

import java.util.ArrayList;

public class Job {

  // Unique identifier
  private String id;
  // Worker assigned to this job
  private Worker worker = null;


  // Below are potential properties of the job
  // Picked items
  private ArrayList<WarehouseItem> items = new ArrayList<WarehouseItem>();
  // Pallets in the job
  private Pallet[] pallets = null;
  // Location of job
  private String location;

  // Descriptive list of orders
  private String[] orders;
  private int[] skus = null;
  private int pickingIndex = 0;
  private String[] pickingOrder = null;

  /**
   * Default constructor for job.
   * 
   * @param skus of the job
   * @param orders describing the skus
   */
  public Job(int[] skus, String[] orders) {
    this.skus = skus;
    this.orders = orders;
    pickingOrder = WarehousePicking.optimize(skus);
    id = "";
    for (int i : this.skus) {
      id = id + String.format("%02d", i);
    }
  }


  /**
   * Replenisher's constructor for job.
   * 
   * @param sku at location to be replenished
   * @param zone of location to be replenished
   * @param aisle of location to be replenished
   * @param rack of location to be replenished
   * @param level that needs to be replenished
   */
  public Job(int sku, String zone, int aisle, int rack, int level) {
    this.skus = new int[] {sku};
    String location = zone + " "
              + ((Integer) aisle).toString() + " "
              + ((Integer) rack).toString() + " "
              + ((Integer) level).toString();
    this.location = location;
    this.id = Integer.toString(sku);
  }


  /**
   * Get skus.
   * 
   * @return int[]
   */
  public int[] getSkus() {
    return skus;
  }

  /**
   * Get picking order.
   * 
   * @return String[]
   */
  public String[] getPickingOrder() {
    return pickingOrder;
  }

  /**
   * Get the next location to pick.
   * 
   * @return String representing location and sku
   */
  public String getNextPick() {
    String nextPick = null;
    if (pickingIndex < pickingOrder.length) {
      nextPick = pickingOrder[pickingIndex];
    }
    // If we're done picking return null
    if (nextPick == null) {
      FileHelper.logEvent("Missing translation data for job " + this);
      return null;
    }
    String[] loc = nextPick.split(",");
    setLocation(loc[0] + " " + loc[1] + " " + loc[2] + " " + loc[3]);
    pickingIndex++;
    return nextPick;
  }

  /**
   * Get the orders in this job.
   * 
   * @return String[]
   */
  public String[] getOrders() {
    return orders;
  }

  /**
   * Get items.
   * 
   * @return ArrayList
   */
  public ArrayList<WarehouseItem> getItems() {
    return items;
  }


  /**
   * Set the pallets for this job.
   * 
   * @param pallets for this job
   */
  public void setPallets(Pallet[] pallets) {
    this.pallets = pallets;
  }

  /**
   * Get pallets.
   * 
   * @return ArrayList
   */
  public Pallet[] getPallets() {
    return pallets;
  }

  /**
   * Get id of the job.
   * 
   * @return string
   */
  public String getId() {
    return id;
  }

  /**
   * Set id of the job.
   * 
   * @param id of the job
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * String representation of the job.
   * 
   * @return String
   */
  public String toString() {
    return id;
  }


  /**
   * Add item .
   * 
   * @param item to add
   */
  public void addItem(WarehouseItem item) {
    items.add(item);
  }

  /**
   * Verify the current job.
   * 
   * @return boolean whether the job is filled
   */
  public boolean verify() {
    ArrayList<Integer> tempSkus = new ArrayList<Integer>();
    for (int i = 0; i < skus.length; i++) {
      tempSkus.add(skus[i]);
    }

    for (WarehouseItem item: items) {
      Integer sku = Integer.valueOf(item.getsku());
      if (tempSkus.contains(sku)) {
        tempSkus.remove(sku);
      } else {
        return false;
      }
    }

    return true;
  }

  /**
   * Discard contents of the job.
   */
  public void discardContents() {
    pallets = null;

    if (items != null) {
      items.clear();
      pickingIndex = 0;
    }
  }

  /**
   * Set location of the job (for replenishers).
   * 
   * @param location as String
   */
  public void setLocation(String location) {
    this.location = location.replace(",", " ");
  }

  /**
   * Set location of the job.
   * 
   * @param location as ArrayList
   */
  public void setLocation(ArrayList<Object> location) {
    this.location = "";
    for (int i = 0; i < location.size(); i++) {
      if (i != 0) {
        this.location += " ";
      }
      this.location += location.get(i).toString();
    }
  }

  /**
   * Get the location of this job.
   * 
   * @return String
   */
  public String getLocation() {
    return location;
  }

  /**
   * Get the location of this job.
   *
   * @return ArrayList
   */
  public ArrayList<Object> getLocationList() {
    ArrayList<Object> locList = new ArrayList<Object>();
    String[] splitList = location.split(" ");
    locList.add(splitList[0]);
    locList.add(Integer.parseInt(splitList[1]));
    locList.add(Integer.parseInt(splitList[2]));
    locList.add(Integer.parseInt(splitList[3]));
    locList.add(Integer.parseInt(splitList[4]));

    return locList;
  }


  /**
   * Set worker.
   * 
   * @param worker assigned to this job
   */
  public void setWorker(Worker worker) {
    this.worker = worker;
  }

  /**
   * Get the worker assigned to this job.
   * 
   * @return Worker
   */
  public Worker getWorker() {
    return worker;
  }

  /**
   * Job complete.
   */
  public void complete() {
    setWorker(null);
  }
  
  /**
   * Get picking index.
   * @return int
   */
  public int getPickingIndex() {
    return pickingIndex;
  }
}

