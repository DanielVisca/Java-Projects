package warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

// import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManagerTests {
  WarehouseSystem system = new WarehouseSystem();
  JobManager jobManager;
  PickingManager pickingManager;
  SequencingManager sequencingManager;
  LoadingManager loadingManager;
  InventoryManager inventoryManager;
  Worker jim;
  Worker bob;
  Worker joe;

  /**
   * Get all the variables from the warehouse system and add orders/workers.
   */
  @Before
  public void initialize() {
    jobManager = system.getJobManager();
    pickingManager = system.getPickingManager();
    sequencingManager = system.getSequencingManager();
    loadingManager = system.getLoadingManager();
    inventoryManager = system.getInventoryManager();

    jobManager.processOrder("SES Blue");
    jobManager.processOrder("SES Red");
    jobManager.processOrder("SE Black");
    jobManager.processOrder("SE Black");

    pickingManager.hireWorker("Jim");
    sequencingManager.hireWorker("Bob");
    loadingManager.hireWorker("Joe");

    jim = pickingManager.getWorker("Jim");
    bob = sequencingManager.getWorker("Bob");
    joe = loadingManager.getWorker("Joe");
  }

  @Test
  public void jobManagerTranslation() {
    Job job = jobManager.getJobs().get(0);
    assertNotNull(job);

    // Sku testing
    int[] skus = job.getSkus();
    assertNotNull(skus);

    assertEquals(skus[0], 37);
    assertEquals(skus[1], 38);
    assertEquals(skus[2], 21);
    assertEquals(skus[3], 22);
    assertEquals(skus[4], 43);
    assertEquals(skus[5], 44);
    assertEquals(skus[6], 43);
    assertEquals(skus[7], 44);

    // Pick Testing
    String[] pickingOrder = job.getPickingOrder();
    assertEquals(pickingOrder[0], "B,1,0,0,37");
    assertEquals(pickingOrder[7], "B,1,1,3,44");

    String nextPick = job.getNextPick();
    assertEquals(nextPick, "B,1,0,0,37");
    for (int i = 0; i < 7; i++) {
      job.getNextPick();
    }
    assertNull(job.getNextPick());

    // Order testing
    String[] orders = job.getOrders();
    assertEquals(orders[0], "SES Blue");
    assertEquals(orders[3], "SE Black");


    // Worker
    Worker worker = new Picker("Jerry", null);
    job.setWorker(worker);
    assertEquals(job.getWorker(), worker);

    // Complete
    job.complete();
    assertNull(job.getWorker());

    // ID
    job.setId("test");
    assertEquals(job.getId(), "test");
  }


  @Test
  public void jobLocation() {
    Job job = jobManager.getJobs().get(0);

    // Location testing
    String testLocation = "B,3,2,1,37";
    job.setLocation(testLocation);
    testLocation = testLocation.replace(",", " ");
    assertEquals(job.getLocation(), testLocation);

    ArrayList<Object> testLocationArray = new ArrayList<Object>();
    testLocationArray.add("A");
    testLocationArray.add(new Integer(1));
    testLocationArray.add(new Integer(2));
    testLocationArray.add(new Integer(3));
    testLocationArray.add(new Integer(40));
    job.setLocation(testLocationArray);

    ArrayList<Object> testLocationReturn = job.getLocationList();
    assertEquals(testLocationReturn.get(0), new String("A"));
  }

  @Test
  public void jobContents() {
    Job job = jobManager.getJobs().get(0);
    int[] skus = job.getSkus();

    // Items and pallets
    WarehouseItem item1 = new WarehouseItem(skus[0]);
    WarehouseItem item2 = new WarehouseItem(skus[1]);
    WarehouseItem item3 = new WarehouseItem(skus[2]);
    WarehouseItem item4 = new WarehouseItem(skus[3]);

    job.addItem(item1);
    job.addItem(item2);
    job.addItem(item3);
    job.addItem(item4);
    assertEquals(job.getItems().size(), 4);

    Pallet[] pallets = new Pallet[1];
    pallets[0] = new Pallet();
    pallets[0].addItem(item1);
    pallets[0].addItem(item2);
    pallets[0].addItem(item3);
    pallets[0].addItem(item4);

    job.setPallets(pallets);
    assertEquals(job.getPallets()[0], pallets[0]);

    assertTrue(job.verify());

    job.discardContents();
    assertEquals(job.getItems().size(), 0);
    assertNull(job.getPallets());
  }

  @Test
  public void jobManagerAddRemoval() {
    Job job1 = new Job(15, "A", 0, 1, 2);
    Job job2 = new Job(49, "B", 1, 0, 3);

    jobManager.addJob(job1);
    jobManager.addJob(job2);
    jobManager.removeJob(job1);

    ArrayList<Job> jobs = jobManager.getJobs();
    assertEquals(jobs.size(), 2);
    assertEquals(jobs.get(1), job2);

    jobManager.removeJob(job2);
    assertEquals(jobs.size(), 1);
  }

  @Test
  public void workerValidity() {

    assertNotNull(jim);
    assertTrue(jim instanceof Picker);
    assertTrue(bob instanceof Sequencer);
    assertTrue(joe instanceof Loader);
    assertEquals(joe.getManager(), loadingManager);
    assertNotNull(pickingManager.getWorker("Steven"));
  }

  @Test
  public void processManagerJobSequence() {
    assertEquals(pickingManager.getJobsToDo().size(), 1);

    pickingManager.setStatus("Jim", "ready");
    pickingManager.setStatus("Jim", "pick 1");

    assertEquals(pickingManager.getJobsToDo().size(), 0);
    assertEquals(pickingManager.getJobsInProgress().size(), 1);

    jim.jobComplete();

    assertEquals(pickingManager.getJobsInProgress().size(), 0);
    assertEquals(sequencingManager.getJobsToDo().size(), 1);

    sequencingManager.nextJob("Bob");

    assertEquals(sequencingManager.getJobsInProgress().size(), 0);
    assertEquals(loadingManager.getJobsToDo().size(), 1);

    loadingManager.nextJob("Joe");
    assertEquals(loadingManager.getJobsInProgress().size(), 0);

    // Picking manager get job when there is none
    pickingManager.nextJob("Jim");
    assertNull(jim.getCurrentJob());
  }

  @Test
  public void loadingFull() {
    pickingManager.setStatus("Jim", "ready");
    jim.jobComplete();
    sequencingManager.nextJob("Bob");

    // Truck full, send shipment
    Truck truck = loadingManager.getTruck();
    // Fill truck
    for (int i = 0; i < 159; i++) {
      truck.loadPallet(new Pallet());
    }
    loadingManager.nextJob("Joe");
    pickingManager.nextJob("Jim");
  }

  @Test
  public void processManagerFailure() {
    pickingManager.setStatus("Jim", "ready");
    pickingManager.setStatus("Jim", "Pick 1");
    jim.discardCurrentJob();

    assertNull(jim.getCurrentJob());
    assertEquals(pickingManager.getJobsToDo().size(), 1);

    pickingManager.setStatus("Jim", "ready");
    assertNotNull(jim.getCurrentJob());
    assertEquals(pickingManager.getJobsToDo().size(), 0);
    assertEquals(pickingManager.getJobsInProgress().size(), 1);
  }

  @Test
  public void workerAlreadyHasJob() {
    jobManager.processOrder("SES Blue");
    jobManager.processOrder("SES Red");
    jobManager.processOrder("SE Black");
    jobManager.processOrder("SE Black");

    ArrayList<Job> jobs = pickingManager.getJobsToDo();
    jim.startNextJob(jobs.get(0));
    assertFalse(jim.startNextJob(jobs.get(1)));
    assertEquals(jim.getCurrentJob(), jobs.get(0));
  }

  @Test
  public void workerMethods() {

    // Check status
    pickingManager.setStatus("Jim", "ready");
    assertEquals(jim.getStatus(), "ready");
    pickingManager.setStatus("Jim", "Pick 1");
    pickingManager.setStatus("Jim", "Pick 2");
    pickingManager.setStatus("Jim", "Pick 3");
    pickingManager.setStatus("Jim", "Pick 4");

    // Check verify
    jim.verifyJob();
    assertNotNull(jim.getCurrentJob());


    assertNull(bob.getStatus());
    jim.jobComplete();
    sequencingManager.nextJob("Bob");
    assertEquals(bob.getStatus(), "ready");

    // Test get string
    assertEquals(bob.toString(), "Bob");
  }

  @Test
  public void pickerPickTypes() {
    pickingManager.setStatus("Jim", "ready");
    assertEquals(jim.getStatus(), "ready");
    pickingManager.setStatus("Jim", "Pick 1");
    pickingManager.setStatus("Jim", "Pick 2");
    pickingManager.setStatus("Jim", "Pick 3");
    ((Picker) jim).pickSku(12);

    jim.verifyJob();
    assertNull(jim.getCurrentJob());

    // Job is reset
    pickingManager.setStatus("Jim", "ready");

    // Full pick
    assertEquals(jim.getCurrentJob().getPickingIndex(), 0);

    pickingManager.setStatus("Jim", "ready");
    pickingManager.setStatus("Jim", "Pick 1");
    pickingManager.setStatus("Jim", "Pick 2");
    pickingManager.setStatus("Jim", "Pick 3");
    pickingManager.setStatus("Jim", "Pick 4");
    pickingManager.setStatus("Jim", "Pick 5");
    pickingManager.setStatus("Jim", "Pick 6");
    pickingManager.setStatus("Jim", "Pick 7");
    assertEquals(jim.getCurrentJob().getPickingIndex(), 8);
    pickingManager.setStatus("Jim", "Pick 8");

    // Pick when theres no pick
    assertNull(jim.getCurrentJob());
    jim.nextTask();
    assertNull(jim.getCurrentJob());
  }

  @Test
  public void systemTest() {

    // Getters
    assertEquals(system.getJobManager(), jobManager);
    assertEquals(system.getPickingManager(), pickingManager);
    assertEquals(system.getSequencingManager(), sequencingManager);
    assertEquals(system.getLoadingManager(), loadingManager);
    assertEquals(system.getInventoryManager(), inventoryManager);

    // Constructor
    system = new WarehouseSystem("orders.txt");
    ArrayList<String> input = system.getInput();
    assertNotNull(input);
    system.allocateJobs();
    system.endDay();
    assertEquals(system.getInput().size(), 0);
    system.updateInput("orders.txt");
    assertEquals(system.getInput(), input);

    // Call main
    WarehouseSystem.main(new String[] {});

    Rack rack = inventoryManager.getRackAtLocation("A", 0, 0);
    inventoryManager.reportFloorStock();
    system.replenishInventory("A", 0, 0, 1, 1);
    rack = inventoryManager.getRackAtLocation("A", 0, 0);
    rack.replenish(1, 1);
    int inventory = rack.getInventory(1);
    int capacity = rack.getLevels().get(1).getCapacity();
    assertEquals(inventory, capacity);
  }
}

