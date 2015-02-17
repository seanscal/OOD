import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link StrictCoinGameModel}. */
public class StrictCoinGameModelTest extends AbstractCoinGameModelTest {
  CoinGameModel strictGame = new StrictCoinGameModel("--O-O-O-OO");

  /**
   * Helps by constructing board of the appropriate class.
   *
   * @param board the board to play with
   * @return board of the appropriate class
   * @throws IllegalArgumentException {@code board} is empty
   * @throws java.lang.NullPointerException {@code board} = null
   * @throws java.lang.IllegalArgumentException {@code board} has characters other than 'O' or '-'
   *
   */
  @Override
  protected AbstractCoinGameModel playGame(String board) {
    return new StrictCoinGameModel(board);
  }

  @Test
  public void testMove() throws Exception {
    strictGame.move(0, 1);
    assertEquals("-O--O-O-OO", strictGame.toString());
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOnCoin() throws Exception {
    strictGame.move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOverCoin() throws Exception {
    strictGame.move(1, 0);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveRight() throws Exception {
    strictGame.move(0, 5);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToSameSpot() throws Exception {
    strictGame.move(0, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToNegative() throws Exception {
    strictGame.move(0, -1);
  }

  @Test
  public void testPlayFullGame() throws Exception{
    strictGame.move(0,0); //O---O-O-OO
    strictGame.move(1,1); //OO----O-OO
    strictGame.move(2,2); //OOO-----OO
    strictGame.move(3,3); //OOOO-----O
    strictGame.move(4,4); //OOOOO-----

    assertEquals(true, strictGame.isGameOver());
  }
}