package quiz2;

import java.util.ArrayList;

public class TextBook extends Book {

  private ArrayList<String> authors = new ArrayList<>();
  protected int edition = 1;

  public TextBook() {
  }

  public TextBook(String title, ArrayList authors) {
    super(title);
    this.authors = authors;
  }

  public void setEdition(int ed) {
    edition = ed;
  }

  public String getCreators() {
    return authors.toString();
  }
}
