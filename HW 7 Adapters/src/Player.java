
public class Player implements CoinGamePlayer {

  private int uid;
  private String name;
  CoinGameModel adaptor;

  public Player(int id, CoinGameModel adaptee) {
    this(id, adaptee.getPlayerNames().get(adaptee.getPlayersArray().indexOf(id)), adaptee);
  }

  public Player(int id, String playerName, CoinGameModel adaptee){
    uid = id;
    name = playerName;
    adaptor = adaptee;
  }

  @Override
  public String getName(){
      return name;
  }

  @Override
  public void move(int coinIndex, int newPosition){
    if(isTurn()) {
      adaptor.move(coinIndex, newPosition);
    }
    else{
      throw new IllegalArgumentException("Not this player's turn");
    }
  }

  @Override
  public boolean isTurn() {
    return (uid == adaptor.currentPlayerTurn());
  }
}
