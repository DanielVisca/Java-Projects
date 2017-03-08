package quiz2;

public class ComicBook extends Book {

  private String author;
  private String artist;
  
  public ComicBook() {
  }

  public ComicBook(String title, String author) {
    this(title, author, null);
  }

  public ComicBook(String title, String author, String artist) {
    super(title);
    this.author = author;
    this.artist = artist;
  }

  public String getCreators() {
    return "Author: " + author + ", Artist: " + artist;
  }
}