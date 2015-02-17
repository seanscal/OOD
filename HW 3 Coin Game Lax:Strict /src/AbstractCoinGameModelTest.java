import org.junit.Test;
import static org.junit.Assert.*;

 /*
  * Defines tests for {@link AbstractCoinGameModel}s without knowing or caring which
  * concrete implementation is being tested.
  */
public abstract class AbstractCoinGameModelTest {

  String board = "--O-O-O-OO";
  String endGame = "OOO-----";
  String noCoins = "-------";
  String illegal = "--O--A-s21-OO";
  String empty = "";
  String nothing;

   /**
    * Helps by constructing CoinGameModel of the appropriate class.
    *
    * @param board the board to play with
    * @return board of the appropriate class
    * @throws IllegalArgumentException {@code board} is empty
    * @throws java.lang.NullPointerException {@code board} = null
    * @throws java.lang.IllegalArgumentException {@code board} has characters other than 'O' or '-'
    *
    */
  protected abstract AbstractCoinGameModel playGame(String board);

  @Test
  public void testBoardSize() throws Exception {
    assertEquals(10, playGame(board).boardSize());
  }

  @Test
  public void testCoinCount() throws Exception {
    assertEquals(5, playGame(board).coinCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCoinCountDoesntWorkWhenIllegal() throws Exception {
    int x = playGame(illegal).coinCount();

    //Check whether the IllegalArgumentException is thrown before this
    //as this would fail if it is not.
    assertEquals(x,4);
  }

  @Test
  public void testGetCoinPosition() throws Exception {
    assertEquals(2, playGame(board).getCoinPosition(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCoinPositionException() throws Exception {
    playGame(noCoins).getCoinPosition(0);
  }

  @Test
  public void testIsGameOverFalse() throws Exception {
    assertEquals(false, playGame(board).isGameOver());
  }

  @Test
  public void testIsGameOverTrue() throws Exception {
    assertEquals(true, playGame(endGame).isGameOver());
  }

  @Test
  public void testIsGameOverNoCoins() throws Exception {
    assertEquals(true, playGame(noCoins).isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacters() throws Exception {
    playGame(illegal).boardSize();
  }

   @Test(expected = IllegalArgumentException.class)
   public void testEmpty() throws Exception {
     playGame(empty).boardSize();
   }

   @Test(expected = NullPointerException.class)
   public void testNull() throws Exception {
     playGame(nothing).boardSize();
   }

}