package warehouse;

public class Picker extends Worker {
  /**
   * Default constructor for picker.
   * @param name name of the picker
   */
  public Picker(String name, ProcessManager manager) {
    super(name, "Picker", manager);
  }

  /**
   * Perform the next task in the job.
   */
  public void nextTask() {
    // Get the location and sku of the next pick (and display on reader)
    if (currentJob == null) {
      FileHelper.logEvent("Picker warning, no job to pick from");
      return;
    }
    String nextPick = currentJob.getNextPick();

    // If no next pick then job is complete
    if (nextPick == null) {
      logTask("to Marshaling");
      jobComplete();
      return;
    }
    String[] splitPick = nextPick.split(",");
    // Create the item (scan) thus removing from inventory
    int sku = Integer.parseInt(splitPick[4]);
    pickSku(sku);
  }
  
  /**
   * Pick sku.
   * @param sku thats picked
   */
  public void pickSku(int sku) {
    WarehouseItem orderItem = new WarehouseItem(sku);
    currentJob.addItem(orderItem);

    // Remove from inventory and log
    logTask("pick SKU " + sku);
    ((PickingManager) manager).pickItem(sku);
  }
}
 
