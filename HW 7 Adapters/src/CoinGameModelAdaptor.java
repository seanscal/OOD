import java.util.ArrayList;

public class CoinGameModelAdaptor implements NewCoinGameModel {

  private int boardSize;
  private boolean[] boardArray;
  private ArrayList<Player> players;
  CoinGameModel adaptor;

  public CoinGameModelAdaptor(CoinGameModel adaptee){
    adaptor = adaptee;
    boardSize = adaptee.boardSize();
    boardArray = new boolean[boardSize];

    for(int coinNumber = 0; coinNumber<adaptee.coinCount(); coinNumber++){
      boardArray[adaptee.getCoinPosition(coinNumber)] = true;
    }

    players = new ArrayList<Player>(adaptee.getPlayers());

    for (int i = 0; i < adaptor.getPlayersArray().size(); i++)
    {
      Player player = new Player(adaptor.getPlayersArray().get(i), adaptee);
      players.add(player);
    }
  }

  @Override
  public int boardSize(){
    return boardSize;
  }

  @Override
  public int coinCount(){
    int count = 0;
    for (int i = 0; i < boardSize(); i++) {
      if (boardArray[i]) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int[] getCoinPositions(){
    int[] result = new int[coinCount()];
    int index = 0;
    for(int i=0; i<boardSize; i++){
      if(boardArray[i]){
        result[index++] = i;
      }
    }
    return result;
  }

  @Override
  public CoinGamePlayer[] getPlayOrder(){
    CoinGamePlayer[] playOrder = new CoinGamePlayer[players.size()];
    playOrder = players.toArray(playOrder);
    return playOrder;
  }

  @Override
  public CoinGamePlayer getWinner(){

    Player wins = null;

    Player winner = new Player(adaptor.getWinner(),adaptor);

    //should work, maybe need to compare names instead because its creating a new player?
    for (int i=0; i < players.size(); i++) {
      if (players.get(i) == winner) {
        wins = players.get(i);
      }
    }
    return wins;
  }

  @Override
  public CoinGamePlayer getCurrentPlayer(){

    Player curr = null;

    Player current = new Player(adaptor.currentPlayerTurn(),adaptor);

    for (int i=0; i < players.size(); i++) {
      if (players.get(i) == current) {
        curr = players.get(i);
      }
    }
    return curr;
  }

  @Override
  public CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name){
    if (predecessor == null) {
      throw new NullPointerException("Predecessor value null");
    }
    if (name == null) {
      throw new NullPointerException("Name value null");
    }
    for (int i=0; i < players.size(); i++){
      if (players.get(i).getName().equals(name)) {
        throw new IllegalArgumentException("Name already in use.");
      }
    }

    int index;
    Player player = new Player(players.size(),name,adaptor);
    for (int i=0; i < players.size(); i++){
      if (predecessor.getName().equals(player.getName())) {

        index = i+1;

        if (index == players.size()) {
          players.add(player);
        }

        players.add(index,player);
      }
      else{
        throw new IllegalArgumentException("Predecessor not in array");
      }
    }
    return player;
  }
}
