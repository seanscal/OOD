import org.junit.Test;
import static org.junit.Assert.*;

public class StrictCoinGameModelTest {

  @Test
  public void testBoardSize() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("OO---OOO-O", 2);
    assertEquals("OO---OOO-O", strict.toString());
  }

//  @Test
//  public void testCoinCount() throws Exception {
//
//  }
//
//  @Test
//  public void testGetCoinPosition() throws Exception {
//
//  }
//
//  @Test
//  public void testIsGameOver() throws Exception {
//
//  }
//
//  @Test
//  public void testMove() throws Exception {
//
//  }
//
//  @Test
//  public void testGetPlayers() throws Exception {
//
//  }
//
//  @Test
//  public void testAddPlayer() throws Exception {
//
//  }
//
//  @Test
//  public void testGetTurn() throws Exception {
//
//  }
//
//  @Test
//  public void testCurrentPlayerTurn() throws Exception {
//
//  }
//
//  @Test
//  public void testGetWinner() throws Exception {
//
//  }
//
//  @Test
//  public void testToString() throws Exception {
//
//  }
}