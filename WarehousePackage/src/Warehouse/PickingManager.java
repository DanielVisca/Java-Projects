package warehouse;

public class PickingManager extends ProcessManager {
  
  /**
   * Constructor for storing InventoryManager.
   * @param system master system
   */
  public PickingManager(WarehouseSystem system) {
    super(system);
  }
 
  /** 
   * Job is complete, hand it off.
   * @param worker who completed job
   */
  public void jobComplete(Worker worker) {
    super.jobComplete(worker);
    Job job = worker.getCurrentJob();
    system.sendToMarshalling(job);
  }

  /**
   * Create a new worker.
   * @param name of the worker
   * @return Worker
   */
  public Worker hireWorker(String name) {
    Worker picker = new Picker(name, this);
    addWorker(picker);
    return picker;
  }
  
  /**
   * Item picked.
   * @param sku of the item
   */
  public void pickItem(int sku) {
    system.removeFromInventory(sku);
  }
}
