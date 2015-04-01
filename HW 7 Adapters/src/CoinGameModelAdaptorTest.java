import org.junit.Test;
import static org.junit.Assert.*;

public class CoinGameModelAdaptorTest {

    String[] players = {"x","y","z"};
    CoinGameModelAdaptor game = CoinGameModelAdaptor.CoinGameModelAdaptor("O-O-O-O", players);

  @Test
  public void test_a_bunch_of_stuff() {
    assertEquals(7,game.boardSize());
    CoinGamePlayer[] players = game.getPlayOrder();

    assertEquals("x", players[0].getName());
    assertEquals("y", players[1].getName());
    assertEquals("z", players[2].getName());
    assertEquals(true,players[0].isTurn());
    assertEquals(false,players[1].isTurn());
    assertEquals(false,players[2].isTurn());
    players[0].move(1,1);
    assertEquals(false,players[0].isTurn());
    assertEquals(true,players[1].isTurn());
    assertEquals(false,players[2].isTurn());


    CoinGamePlayer a = game.addPlayerAfter(players[0],"a");

    CoinGamePlayer[] players2 = game.getPlayOrder();
    assertEquals("x", players2[0].getName());
    assertEquals("a", players2[1].getName());
    assertEquals("y", players2[2].getName());
    assertEquals("z", players2[3].getName());

    assertEquals(false,players2[0].isTurn());
    assertEquals(false,players2[1].isTurn());
    assertEquals(true,players2[2].isTurn());
    assertEquals(false,players2[3].isTurn());

    try {
      players[0].move(1, 1);
    }
    catch (Exception ex) {
      assertEquals(ex.getMessage(), "Not this player's turn");
    }
  }

}