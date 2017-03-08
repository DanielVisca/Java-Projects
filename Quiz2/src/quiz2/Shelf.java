package quiz2;

public interface Shelf<T> {

  /** Adds shelfItem to the beginning of the shelf. */
  public void addLeft(T shelfItem);

  /** Adds shelfItem to the end of the shelf. */
  public void addRight(T shelfItem);

  /** Returns the left-most item on the shelf. */
  public T getLeft();

  /** Returns the right-most item on the shelf. */
  public T getRight();
}
