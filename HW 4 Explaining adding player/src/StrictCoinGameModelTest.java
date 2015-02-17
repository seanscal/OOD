import org.junit.Test;
import static org.junit.Assert.*;

public class StrictCoinGameModelTest {

  CoinGameModel strictGame = new StrictCoinGameModel("--O-O-O-OO", 2);
  CoinGameModel invalidPlayers = new StrictCoinGameModel("--O-O-O-OO",0);
  CoinGameModel almostWon = new StrictCoinGameModel("OOOOOOOO-O",2);

  @Test
  public void testGetPlayers() throws Exception {
    assertEquals(2,strictGame.getPlayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInvalidPlayers() throws Exception {
    assertEquals(2, invalidPlayers.getPlayers());
  }
  
  @Test
  public void testAddPlayer() throws Exception {
    strictGame.addPlayer();
    assertEquals(3,strictGame.getPlayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerOnWonBoard() throws Exception {
    assertEquals(false, almostWon.isGameOver());
    almostWon.move(8, 8);
    assertEquals(true, almostWon.isGameOver());
    almostWon.addPlayer();
  }

  @Test
  public void testGetTurn() throws Exception {
    assertEquals(0,strictGame.getTurn());
    strictGame.move(0,1);
    assertEquals(1,strictGame.getTurn());
  }

  @Test
  public void testCurrentPlayerTurn() throws Exception {
    assertEquals(1,strictGame.currentPlayerTurn());
    strictGame.move(0,1);
    assertEquals(2,strictGame.currentPlayerTurn());
    strictGame.move(1,3);
    assertEquals(1,strictGame.currentPlayerTurn());
  }

  @Test
  public void testGetWinner() throws Exception {
    assertEquals(false, almostWon.isGameOver());
    almostWon.move(8,8);
    assertEquals(1,strictGame.getWinner());
  }
}