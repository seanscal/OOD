import java.util.ArrayList;

public class CoinGameModelAdaptor implements NewCoinGameModel {

  private int boardSize;
  private boolean[] boardArray;
  private int turn;
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
    return new Player(adaptor.getWinner(),adaptor);
  }

  @Override
  public CoinGamePlayer getCurrentPlayer(){
    return new Player(adaptor.currentPlayerTurn(),adaptor);
  }

  @Override
  public CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name){
    if (predecessor == null) {
      throw new NullPointerException("Predecessor value null");
    }
    if (name == null) {
      throw new NullPointerException("Name value null");
    }

    int index;
    Player player = new Player(players.size(),name);
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
