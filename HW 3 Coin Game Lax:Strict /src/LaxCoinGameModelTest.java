import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link LaxCoinGameModel}. */
public class LaxCoinGameModelTest extends AbstractCoinGameModelTest {
  CoinGameModel laxGame = new LaxCoinGameModel("--O-O-O-OO");

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
  protected AbstractCoinGameModel playGame(String board)
  {
    return new LaxCoinGameModel(board);
  }

  @Test
  public void testMove() throws Exception {
    laxGame.move(0, 1);
    assertEquals("-O--O-O-OO", laxGame.toString());
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOnOtherCoin() throws Exception {
    laxGame.move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveRight() throws Exception {
    laxGame.move(0, 5);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToSameSpot() throws Exception {
    laxGame.move(0, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToNegative() throws Exception {
    laxGame.move(0, -1);
  }

  @Test
  public void testPlayFullGame() throws Exception{
    laxGame.move(4,0); //O-O-O-O-O-
    laxGame.move(4,1); //OOO-O-O---
    laxGame.move(4,3); //OOOOO-----

    assertEquals(true, laxGame.isGameOver());
  }
}