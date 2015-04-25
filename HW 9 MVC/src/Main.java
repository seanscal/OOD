import java.io.IOException;

/**
 * Runs the game
 */
public final class Main {
  public static void main(String[] args) throws IOException {
    new Controller(Model.checkers()).run();
  }
}