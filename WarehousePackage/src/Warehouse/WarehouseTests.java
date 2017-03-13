package warehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WarehouseTests {

  @Test
  public void addZoneTestSameCapacity() {
//    Zone zone1 = new Zone("A", 1, 20, 6, 100, 10);
//    Zone zone2 = new Zone("B", 2, 10, 2, 100, 20);
//
//    WarehouseSystem s = new WarehouseSystem();
//    InventoryManager im = new InventoryManager(s, "traversal_table.csv", 0, 0, 0, 0, 0, 0);
//    im.addZone(1, 20, 6, 100, 10);
//    im.addZone(2, 10, 2, 100, 20);
//
//    ArrayList<Zone> floor = new ArrayList<Zone>();
//    floor.add(zone1);
//    floor.add(zone2);
//
//    // System.out.println(im.getFloor().get(0).getName());
//    // System.out.println(im.getFloor().get(1).getName());
//    // System.out.println(zone2.getName());

  }

  @Test
  public void addZoneTestDifferentCapacity() {
//    Zone zone1 = new Zone("A", 4, 4, 5);
//    Zone zone2 = new Zone("B", 2, 3, 5);
//
//    WarehouseSystem s = new WarehouseSystem();
//    InventoryManager im = new InventoryManager(s, "traversal_table.csv", 0, 0, 0, 0);
//    im.addZone(4, 4, 5);
//    im.addZone(2, 3, 5);
//
//    ArrayList<Zone> floor = new ArrayList<Zone>();
//    floor.add(zone1);
//    floor.add(zone2);

//    boolean equal1 = EqualsBuilder.reflectionEquals(im.getFloor().get(0), zone1);
//    boolean equal2 = EqualsBuilder.reflectionEquals(im.getFloor().get(1), zone2);
//
//    assertTrue(equal1);
//    assertTrue(equal2);
  }

  @Test
  public void removeZoneTest() {
//    Zone zone1 = new Zone("A", 1, 20, 6, 100, 10);
//    Zone zone2 = new Zone("A", 4, 35, 4, 100, 10);
//
//    WarehouseSystem s = new WarehouseSystem();
//    InventoryManager im = new InventoryManager(s, "traversal_table.csv", 0, 0, 0, 0, 0, 0);
//    im.addZone(1, 20, 6, 100, 10);
//    im.addZone(4, 35, 4, 100, 10);
//
//    im.removeZone(zone1.getName());
//    im.removeZone(zone2.getName());
//
//    assertEquals(im, []);
  }

  // JobManager test starts here.
  @Test
  public void addFasciaTest() {
    WarehouseSystem ws = new WarehouseSystem();
    JobManager jb = new JobManager(ws);

    // jb.addFascia("");

  }

  // InventoryLocations test starts here.
  @Test
  public void detailsToLocationTest() {

  }

  // Truck test starts here.
  @Test
  public void loadPalletTest() {
    Truck t1 = new Truck();
    Pallet p = new Pallet();
    boolean result = t1.loadPallet(p);
    assertTrue(result);

    Truck t2 = new Truck();
    for (int i = 0; i <= 160; i++) {
      t2.loadPallet(p);
    }
    boolean result1 = t2.loadPallet(p);
    assertFalse(result1);
  }

  @Test
  public void fullTest() {
    Truck t1 = new Truck();
    Truck t2 = new Truck();
    Pallet p1 = new Pallet();
    Pallet p2 = new Pallet();

    t1.loadPallet(p1);
    assertFalse(t1.full());

    for (int i = 0; i <= 160; i++) {
      t2.loadPallet(p2);
    }
    assertTrue(t2.full());
  }

  // Pallet test starts here.
  @Test
  public void addItemPalletTest() {
    Pallet p = new Pallet();
    WarehouseItem item1 = new WarehouseItem(12);
    WarehouseItem item2 = new WarehouseItem(34);
    WarehouseItem item3 = new WarehouseItem(7172);
    p.addItem(item1);
    p.addItem(item2);
    p.addItem(item3);

    ArrayList<WarehouseItem> items = new ArrayList<WarehouseItem>();
    items.add(item1);
    items.add(item2);
    items.add(item3);

    assertEquals(p.getItems().get(0), items.get(0));
    assertEquals(p.getItems().get(1), items.get(1));
    assertNotEquals(p.getItems().get(0), items.get(1));
    assertNotEquals(p.getItems().get(1), items.get(2));
  }

  // Fascia test starts here.
  @Test
  public void getColourFasciaTest() {
    Fascia f = new Fascia(12, "White", "S");

    assertEquals(f.getColour(), "White");
    assertNotEquals(f.getColour(), "Black");
  }

  @Test
  public void getTypeFasciaTest() {
    Fascia f1 = new Fascia(12, "White", "S");
    Fascia f2 = new Fascia(34, "White", "SE");

    assertEquals(f1.getType(), "S");
    assertEquals(f2.getType(), "SE");
    assertNotEquals(f1.getType(), "SE");
    assertNotEquals(f2.getType(), "S");
  }
  
  //WarehouseItem test starts here.
  @Test
  public void getskuWarehouseItemTest() {
    WarehouseItem item = new WarehouseItem(7980);
    
    assertEquals(item.getsku(), 7980);
  }
  
  //Zone test starts here.
  @Test
  public void getNameZoneTest() {
//    Zone zone1 = new Zone("A", 1, 20, 6, 100, 10);
//    Zone zone2 = new Zone("C", 2, 6, 4);
//    
//    assertEquals(zone1.getName(), "A");
//    assertEquals(zone2.getName(), "C");
  }

  @Test
  public void getAislesZoneTest() {

  }
  
  @Test
  public void removeItemZoneTest() {
    
  }
  
  //Level test starts here.
  @Test
  public void getCapacityLevelTest() {
    Level<Object> l1 = new Level<>();
    Level<Object> l2 = new Level<>(100);
    Level<Object> l3 = new Level<>(200, 0);
    
    assertEquals(l1.getCapacity(), 0);
    assertEquals(l2.getCapacity(), 100);
    assertEquals(l3.getCapacity(), 200);
  }
  
  @Test
  public void setCapacityLevelTest() {
    Level<Object> l = new Level<>();
    l.setCapacity(150);
    
    assertEquals(l.getCapacity(), 150);
  }
  
  @Test
  public void getMinLevelTest() {
    Level<Object> l1 = new Level<>(50, 10);
    Level<Object> l2 = new Level<>(50, 0);
    
    assertEquals(l1.getMin(), 10);
    assertEquals(l2.getMin(), 0);
  }
  
  @Test
  public void setMinLevelTest() {
    Level<Object> l = new Level<>();
    l.setMin(20);
    
    assertEquals(l.getMin(), 20);
  }
  
  @Test
  public void atMinLevelTest() {
    Level<Object> l = new Level<>(75, 10);
    
    assertTrue(l.atMin());
  }
  
  //Job test starts here.
  @Test 
  public void getSkusJobTest() {
    Job j = new Job(5051, "C", 4, 2, 6);
    
    assertEquals(j.getSkus(), [5051]);
  }
  
  
  // Loader test starts here.
  @Test
  public void nextTask() {

  }


}
