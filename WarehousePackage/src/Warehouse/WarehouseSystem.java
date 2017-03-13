package warehouse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class WarehouseSystem {
  private ArrayList<String> input = new ArrayList<String>();

  // All the managers than make the warehouse work
  private JobManager jobManager = new JobManager(this);
  private InventoryManager inventoryManager =
      new InventoryManager(this, "traversal_table.csv", 2, 2, 3, 4, 30, 5);
  // TODO Create exception to catch wrong file, wrong file type, wrong max/min capacities
  private PickingManager pickingManager = new PickingManager(this);
  private SequencingManager sequencingManager = new SequencingManager(this);
  private LoadingManager loadingManager = new LoadingManager(this);

  /**
   * Get job manager.
   * 
   * @return JobManager
   */
  public JobManager getJobManager() {
    return jobManager;
  }

  /**
   * Get inventory manager.
   * 
   * @return InventoryManager
   */
  public InventoryManager getInventoryManager() {
    return inventoryManager;
  }

  /**
   * Get picking manager.
   * 
   * @return PickingManager
   */
  public PickingManager getPickingManager() {
    return pickingManager;
  }

  /**
   * Get sequencing manager.
   * 
   * @return SequencingManager
   */
  public SequencingManager getSequencingManager() {
    return sequencingManager;
  }

  /**
   * Get loading manager.
   * 
   * @return LoadingManager;
   */
  public LoadingManager getLoadingManager() {
    return loadingManager;
  }

  /**
   * Default Constructor for warehouse.
   */
  public WarehouseSystem() {}



  /**
   * Constructor with a given input File.
   * 
   * @param inputFile orders to allocate
   */
  public WarehouseSystem(String inputFile) {
    this.input = FileHelper.getLinesFromFile(inputFile);
  }

  /**
   * Return the input.
   * 
   * @return ArrayList
   */
  public ArrayList<String> getInput() {
    return this.input;
  }

  /**
   * Add input from the given file (FIFO).
   * 
   * @param newInput new orders to add
   */
  public void updateInput(String newInput) {
    input.addAll(FileHelper.getLinesFromFile(newInput));
  }

  /**
   * Updates the status of all managers and input given input.
   */
  public void allocateJobs() {
    while (input.size() > 0) {
      String line = input.remove(0);
      receiveOrder(line);

    }
  }

  /**
   * Receive status of job, and send to the manager for that step of process.
   * 
   * @param status received from system
   */
  public void receiveOrder(String status) {
    String[] lineSegments = status.split(" ", 3);
    // ex: for picker input, last segment is "pick 1"
    // update the status of each worker and orders
    switch (lineSegments[0]) {
      case "Order":
        jobManager.processOrder(lineSegments[1] + " " + lineSegments[2]);
        break;
      case "Picker":
        pickingManager.setStatus(lineSegments[1], lineSegments[2]);
        break;
      case "Loader":
        loadingManager.setStatus(lineSegments[1], lineSegments[2]);
        break;
      case "Sequencer":
        sequencingManager.setStatus(lineSegments[1], lineSegments[2]);
        break;
      case "Replenisher":
        inventoryManager.setStatus(lineSegments[1], lineSegments[2]);
        break;
      default:
        break;
    }
  }

  /**
   * Send to picking.
   * 
   * @param job to send
   */
  public void sendToPicking(Job job) {
    pickingManager.queueJob(job);
    FileHelper.logEvent("Job " + job + " ready for picking");
  }

  /**
   * Send to marshalling.
   * 
   * @param job to send
   */
  public void sendToMarshalling(Job job) {
    sequencingManager.queueJob(job);
    FileHelper.logEvent("Job " + job + " ready for sequencing");
  }


  /**
   * Send to loading.
   * 
   * @param job to send
   */
  public void sendToLoading(Job job) {
    loadingManager.queueJob(job);
    FileHelper.logEvent("Job " + job + " ready for loading");
  }

  /**
   * Job is loaded.
   * 
   * @param job that was loaded
   */
  public void jobLoaded(Job job) {
    jobManager.removeJob(job);
    FileHelper.logEvent("Job " + job + " loaded on a truck");
  }

  /**
   * Remove inventory.
   * 
   * @param sku to remove
   */
  public void removeFromInventory(int sku) {
    inventoryManager.removeInventory(sku);
    FileHelper.logEvent("Item " + sku + " removed from inventory");
  }


  /**
   * Replenish inventory.
   * 
   * @param sku to remove
   */
  public void replenishInventory(String zone, int aisle, int rack, int level, int sku) {
    inventoryManager.replenishLevel(zone, aisle, rack, level, sku);
    FileHelper.logEvent("Replenished " + sku + " at location " + zone + " " + aisle + " " + rack 
        + " " + level);
  }

  /**
   * End the day at the factory.
   */
  public void endDay() {
    inventoryManager.reportFloorStock();
    FileHelper.writeEventsToFile("events.csv");
    FileHelper.writeOrdersToFile("orders_on_truck.csv");
  }

  /**
   * Main loop.
   */
  public static void main(String[] args) {
    WarehouseSystem warehouseSystem = new WarehouseSystem("orders.txt");
    warehouseSystem.allocateJobs();
    warehouseSystem.endDay();
  }
}
