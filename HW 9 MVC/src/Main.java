import java.io.IOException;

/**
 * Runs an instance of the default game
 */
public final class Main {
  public static void main(String[] args) throws IOException {
    new Controller(Model.checkers()).run();
  }
}