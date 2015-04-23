import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * A facility for testing your game by comparing its output to the reference
 * implementation.
 */
public class NetworkClientTester {
  /**
   * The interface that {@code NetworkClientTester} uses to run your game.
   * Implement this interface so that calling {@code runGame} runs your game
   * with the given {@code InputStream} for input and {@code Appendable} for
   * output. Then pass a {@code GameRunner} object to {@code
   * assertGameMatches} in order to test it.
   */
  @FunctionalInterface
  public interface GameRunner {
    void runGame(InputStream input, Appendable output) throws Exception;
  }

  /**
   * Asserts that the output of the game represented by {@code runner}
   * matches the output of the reference implementation on the given
   * sequence of user inputs. In particular, this method runs your game,
   * captures its output, and looks for prompts of the form {@code "\n[o]"}
   * and {@code "\n[+]"}. Each time it sees such a prompt, it sends the next
   * element of {@code userInput}, converted to a string using
   * {@code toString} and followed by a newline. If a prompt appears and
   * {@code userInput} is exhausted, it halts the game at that point, in
   * order to allow tests that don't finish the game.
   *
   * <p>
   * As an example of how to use this, suppose that you have a Controller
   * class whose constructor takes a model, an InputStream, and an Appendable,
   * and that runs the game when a method {@code playGame()} is called. If
   * you want to test your game on the sequence of user inputs "1", "1", "2",
   * and "1" (with a newline after each), you could write:
   *
   * <pre>{@code
   *   assertGameMatches(
   *     (input, output) -> new Controller(model, input, output).playGame(),
   *     1, 1, 2, 1);
   * }</pre>
   *
   * <p>
   * This code will run both your game and the reference implementation on
   * that sequence of input and then assert that their output matches.
   *
   * @param runner    to run the game
   * @param userInput user input to send to the game, a line at a time
   * @throws IOException if there's a network problem
   */
  public static void assertGameMatches(GameRunner runner,
                                       Object... userInput)
      throws IOException
  {
    String actual = runLocalGameWithInput(runner, userInput);
    String expected = runRemoteGameWithInput(userInput);

    assertEquals(expected, actual);
  }

  private static String runLocalGameWithInput(GameRunner runner,
                                              Object... userInput)
      throws IOException
  {
    PipedOutputStream send = new PipedOutputStream();
    PipedInputStream recv  = new PipedInputStream();

    Thread gameThread = new Thread() {
      @Override
      public void run() {
        try (InputStream input = new PipedInputStream(send);
             Writer output =
                 new FlushingOutputStreamWriter(new PipedOutputStream(recv)))
        {
          runner.runGame(input, output);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    };

    gameThread.start();
    String result = runGameWithInput(send, recv, userInput);
    gameThread.stop();

    return result;
  }

  private static String runRemoteGameWithInput(Object... userInput)
      throws IOException
  {
    try (Socket socket = new Socket(HOST, PORT);
         InputStream in = socket.getInputStream();
         OutputStream out = socket.getOutputStream()) {
      return runGameWithInput(out, in, userInput);
    }
  }
  private static final String HOST = "cs3500.ccs.neu.edu";
  private static final int PORT = 3500;

  private static String runGameWithInput(OutputStream send0,
                                         InputStream recv0,
                                         Object... userInput)
      throws IOException
  {
    StringBuilder output     = new StringBuilder();
    Queue<Object> inputQueue = new ArrayDeque<>(Arrays.asList(userInput));

    Reader recv = new InputStreamReader(recv0);
    Writer send = new OutputStreamWriter(send0);

    int matchLength = 0;
    int c;

    loop:
    while ((c = recv.read()) >= 0) {
      output.append((char) c);

      switch (matchLength) {
        case 0:
          if (c == '\n') ++matchLength;
          break;

        case 1:
          if (c == '[') ++matchLength;
          else matchLength = 0;
          break;

        case 2:
          if (c == 'o' || c == '+') ++matchLength;
          else matchLength = 0;
          break;

        case 3:
          matchLength = 0;
          if (c == ']') {
            if (inputQueue.isEmpty()) {
              break loop;
            } else {
              send.append(inputQueue.remove().toString())
                  .append('\n')
                  .flush();
            }
          }
      }
    }

    return output.toString();
  }
}

/**
 * Like {@link OutputStreamWriter}, but automatically flushes after
 * every write.
 */
class FlushingOutputStreamWriter extends Writer {
  private final OutputStream os;

  public FlushingOutputStreamWriter(OutputStream os) {
    this.os = os;
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    byte[] bytes = new byte[len];

    for (int i = 0; i < len; ++i) {
      bytes[i] = (byte) cbuf[i + off];
    }

    os.write(bytes, off, len);
    os.flush();
  }

  @Override
  public void flush() throws IOException {
    os.flush();
  }

  @Override
  public void close() throws IOException {
    os.close();
  }
}
