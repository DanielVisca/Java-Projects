package warehouse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class FileHelper {

  private static boolean silentLogging = true;
  private static ArrayList<String> events = new ArrayList<String>();
  private static ArrayList<String> orders = new ArrayList<String>();

  /**
   * Get a list of lines from a given file.
   * @return ArrayList
   */
  public static ArrayList<String> getLinesFromFile(String file) {
    BufferedReader br = null;
    FileReader fr = null;
    String line = "";
    ArrayList<String> lines = new ArrayList<String>();
    try {
      fr = new FileReader(file);
      br = new BufferedReader(fr);
      while ((line = br.readLine()) != null) {
        lines.add(line);
      } 
    } catch (IOException exp) {
      exp.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException exp) {
          exp.printStackTrace();
        }
      }
      if (fr != null) {
        try {
          fr.close();
        } catch (IOException exp) {
          exp.printStackTrace();
        }
      }
    }
    return lines;
  }
  
  /**Write lines to file.
   * @param file where to write it
   * @param lines to write
   */
  public static void writeLinesToFile(String file, String[] lines) {
    BufferedWriter bw = null;
    FileWriter fw = null;
    try {
      fw = new FileWriter(file);
      bw = new BufferedWriter(fw);
      for (String line: lines) {
        bw.write(line + "\n");
      }
    } catch (IOException exp) {
      exp.printStackTrace();
    } finally {
      if (bw != null) {
        try {
          bw.close();
        } catch (IOException exp) {
          exp.printStackTrace();
        }
      }
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException exp) {
          exp.printStackTrace();
        }
      }
    }
  }

  
  /**
   * Log an event in the warehouse.
   * @param event event to log
   */
  public static void logEvent(String event) {
    if (!silentLogging) {
      System.out.println(event);
    }
    events.add(event);
  }
  
  /**
   * Log orders.
   * @param orders to log
   */
  public static void logOrders(String[] orders) {
    for (int i = 0; i < orders.length; i++) {
      if (!silentLogging) {
        System.out.println(orders[i] + " is on a truck");
      }
      FileHelper.orders.add(orders[i]);
    }
  }
  
  /**Write events to file.
   * @param file name of file
   */
  public static void writeEventsToFile(String file) {
    writeLinesToFile(file, events.toArray(new String[events.size()]));
  }
  
  /**Write orders to file.
   * @param file name of file
   */
  public static void writeOrdersToFile(String file) {
    writeLinesToFile(file, orders.toArray(new String[orders.size()]));
  }
}
