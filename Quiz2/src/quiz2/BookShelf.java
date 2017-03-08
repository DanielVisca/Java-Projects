package quiz2;

import java.util.ArrayList;
import java.util.List;

public class BookShelf<T> implements Shelf<T> {

  private ArrayList<T> contents = new ArrayList<>();
  private int numBooks;

  public BookShelf() {
  }

  public BookShelf(T item) {
    contents.add(0, item);
  }

  public void addLeft(T item) {
    contents.add(0, item);
  }

  public void addRight(T item) {
    contents.add(item);
  }

  public T getLeft() {
    return contents.get(0);
  }

  public T getRight() {
    numBooks = contents.size();
    return contents.get(numBooks - 1);
  }
}