import org.junit.Test;

import static org.junit.Assert.*;

public class StrictCoinGameModelTest {

  @Test
  public void testScenario1_1() {
    assertEquals(CoinGameTestScenarios.scenario1("-OOO-O---"), "OOOO-----");
  }

  @Test
  public void testScenario1_2() {
    assertEquals(CoinGameTestScenarios.scenario1("---OOOO"), "OOOO---");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScenario1_3() {
    CoinGameTestScenarios.scenario1("O--O");
  }

  @Test
  public void testScenario2_1() {
    assertEquals(CoinGameTestScenarios.scenario2("O--O-O"), "OO--O-");
  }

  @Test
  public void testScenario2_2() {
    try {
      CoinGameTestScenarios.scenario2("-O-O-O");
    } catch (Exception ex) {
      assertEquals(ex.getMessage(), "Coin cannot move onto another coin.");
    }
  }

  @Test
  public void testScenario2_3() {
    try {
      CoinGameTestScenarios.scenario2("OOO---");
    } catch (Exception ex) {
      assertEquals(ex.getMessage(), "Coin cannot move right.");
    }
  }

  @Test
  public void testScenario3_1() {
    assertEquals(CoinGameTestScenarios.scenario3("OO-OO-O-O"), "OOO-OO-O-");
  }

  @Test
  public void testScenario3_2() {
    try {
      CoinGameTestScenarios.scenario3("OOOO----O-O");
    } catch (Exception ex) {
      assertEquals(ex.getMessage(), "Coin cannot move over another coin using strict rules.");
    }
  }

  @Test
  public void testScenario4() {
    assertEquals(CoinGameTestScenarios.scenario4("O-OO-O"), "O-OOO-");
  }

  @Test
  public void testScenario5_1() {
    assertEquals(CoinGameTestScenarios.scenario5("O--------O"), true);
  }

  @Test
  public void testScenario5_2() {
    assertEquals(CoinGameTestScenarios.scenario5("O--------OO"), false);
  }

  @Test
  public void testBoardSize() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("OO---OOO-O", 2);
    assertEquals("OO---OOO-O", strict.toString());
  }

  @Test
  public void testCoinCount() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("OO-O--O-OO-OO----O---O", 2);
    assertEquals(strict.coinCount(), 10);
  }

  @Test
  public void testGetCoinPosition() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("O-OO--O", 2);
    assertEquals(strict.getCoinPosition(0), 0);
    assertEquals(strict.getCoinPosition(1), 2);
    assertEquals(strict.getCoinPosition(2), 3);
    assertEquals(strict.getCoinPosition(3), 6);
  }

  @Test
  public void testIsGameOver() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("OOO---", 1);
    assertEquals(strict.isGameOver(), true);
    strict = new StrictCoinGameModel("-", 2);
    assertEquals(strict.isGameOver(), true);
    strict = new StrictCoinGameModel("----------", 3);
    assertEquals(strict.isGameOver(), true);
    strict = new StrictCoinGameModel("-OOOOO", 1);
    assertEquals(strict.isGameOver(), false);
  }

  @Test
  public void testMoveAndToString() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("-O--OO", 1);
    strict.move(1, 2); // -OO--O
    assertEquals(strict.toString(), "-OO--O");
    try {
      strict.move(0, -2);
    } catch (Exception ex) {
      assertEquals(ex.getMessage(), "Coin cannot move off the board.");
    }
  }

  @Test
  public void testGetPlayers() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("-O--O", 2);
    assertEquals(strict.getPlayers(), 2);
    strict.addPlayer(0);
    assertEquals(strict.getPlayers(), 3);
    for (int i = 0; i < 10; i++) {
      strict.addPlayer(0);
    }
    assertEquals(strict.getPlayers(), 13);
  }

  @Test
  public void testPlayersTurnsWinners() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("-O--O", 1);
    assertEquals(strict.currentPlayerTurn(), 0);
    strict.addPlayer(0); //<1,0>
    assertEquals(strict.currentPlayerTurn(), 0);
    strict.addPlayer(2); //<1,0,2>
    assertEquals(strict.currentPlayerTurn(), 0);
    strict.move(1, 2); // -OO--
    assertEquals(strict.currentPlayerTurn(), 2);
    strict.move(0, 0); // O-O--
    assertEquals(strict.currentPlayerTurn(), 1);
    strict.move(1, 1);
    assertEquals(strict.currentPlayerTurn(), 0);
    assertEquals(strict.getWinner(), 1);
  }

  @Test
  public void testPlayersTurnsWinners2() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("-O-OO-O", 3);
    assertEquals(strict.currentPlayerTurn(), 0);
    assertEquals(strict.getWinner(), -1);
    strict.move(3, 5); // -O-OOO-
    assertEquals(strict.currentPlayerTurn(), 1);
    strict.move(0, 0); // O--OOO-
    assertEquals(strict.currentPlayerTurn(), 2);
    strict.move(1, 1); // OO--OO-
    assertEquals(strict.currentPlayerTurn(), 0);
    strict.addPlayer(1); //<0,3,1,2>
    strict.move(2, 2); // OOO--O-
    assertEquals(strict.currentPlayerTurn(), 3);
    strict.move(3, 3);
    assertEquals(strict.isGameOver(), true);
    assertEquals(strict.getWinner(), 3);
  }

  @Test
  public void addPlayerOnWonBoard() throws Exception {
    StrictCoinGameModel strict = new StrictCoinGameModel("OO-O", 2);
    strict.move(2, 2); //OOO-
    try {
      strict.addPlayer(0);
    } catch (Exception ex) {
      assertEquals(ex.getMessage(), "Cannot add players to a finished game");
    }
  }
}