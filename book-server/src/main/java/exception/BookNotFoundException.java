package exception;

public class BookNotFoundException extends Exception {


    public BookNotFoundException(String isbn) {
        super("Book with isbn:" + isbn + "was not found");
    }
}
