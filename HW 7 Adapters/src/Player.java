
public class Player implements CoinGamePlayer {

  private int uid;
  private String name;
  private boolean isTurn;
  CoinGameModel adaptor;

  public Player(int id, CoinGameModel adaptee) {
    this(id, "Player "+id);
    adaptor = adaptee;
  }

  public Player(int id, String playerName){
    uid = id;
    name = playerName;
  }

  @Override
  public String getName(){
    return name;
  }

  @Override
  public void move(int coinIndex, int newPosition){
    adaptor.move(coinIndex,newPosition);
  }

  @Override
  public boolean isTurn() {
    return (adaptor.getPlayersArray().get(uid) == adaptor.currentPlayerTurn());
  }
}
