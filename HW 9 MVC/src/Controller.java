import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * handles user input and acts as a go between for the view and the model to provide the
 * necessary information
 */

public final class Controller {

  private final Model model;
  private final BoardView view;
  private final IntReader in;
  private final Appendable out;
  private ArrayList<Check> moves;
  public static final String ERROR =
      "error";

  /**
   * Constructs a controller given a model, uses defaults for other parameters
   * @param model
   */
  public Controller(Model model) {
    this(model, new BoardView(System.out),
         IntReader.create(System.in, System.out, ERROR),
         System.out);
  }

  /**
   * Constructs a controller given the components it will control.
   *
   * @param m the model
   * @param view  the view, for rendering the model
   * @param in    the source of user moves
   * @param out   the output stream
   */
  public Controller(Model m, BoardView view, IntReader in,
                    Appendable out) {
    Objects.requireNonNull(m);
    Objects.requireNonNull(view);
    Objects.requireNonNull(in);
    Objects.requireNonNull(out);

    this.model = m;
    this.view = view;
    this.in = in;
    this.out = out;
    //stores the moves for the selected piece
    this.moves = new ArrayList<Check>();
  }

  /**
   * determines if the game is over
   * @return boolean true if game is over
   */
  public boolean isGameOver() {
    return model.isGameOver();
  }

  /**
   * Runs this controller on its model.
   *
   * @throws IOException if an I/O operation fails
   */
  public void run() throws IOException {
    while (!isGameOver()) {
      step();
    }
  }

  /**
   * Runs the first interaction step of the controller on the model, selecting a piece to move
   *
   * @throws IOException if an I/O operation fails
   */

  public void step() throws IOException {
    ReadOnlyBoardViewModel bv = new ReadOnlyBoardView(model, new Check(0, 0), false, model.turn);

    // displays final screen if game has ended
    if(model.isGameOver()) {
      String winstring = "";
      if (model.getWinner().toChar() == '+'){
        winstring = "First Player";
      }
      else if (model.getWinner().toChar() == 'o'){
        winstring = "Second Player";
      }
      String winner = winstring + "(" + Character.toString(model.getWinner().toChar()) + ")";
      String winMessage = " is the winner";
      out.append(winner).append(winMessage);
      view.draw(bv);
    }

    // draws the game if still in play, receives user input for which piece to move, calls the next
    // step to select where to move to
    view.draw(bv);
    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose a piece to move: ";
    int what = in.nextInt(playerstr + message, this::validateMovable);
    Check c = model.movablePieces().get(what - 1);
    this.moves = model.board.moves(c);
    model.selected = c;
    this.step2(what);

  }

  /**
   * runs the second interaction step of the controller, selecting where to move to
   * @param piece the piece that will be moved
   * @throws IOException if something fails
   */
  public void step2(int piece) throws IOException {
    Check c = model.movablePieces().get(piece - 1);
    ReadOnlyBoardViewModel bv = new ReadOnlyBoardView(model, c, true, model.turn);
    view.draw(bv);


    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose where to move to: ";
    int where = in.nextInt(playerstr + message, this::validateMove);
    Check moveIt = model.board.moves(c).get(where - 1);
    model.move(c.x, c.y, moveIt.x, moveIt.y);

  }

  /**
   * determines if the given number corresponds to a piece that can move
   * @param piecenumber the number of the piece the user wants to move
   * @return a String error message if the piece is not eligible to move
   */
  private String validateMovable(int piecenumber) {
    if (piecenumber <= 0 || piecenumber > model.movablePieces().size()) {
      return "This is not a movable piece";
    }

    // It's valid!
    return null;
  }

  /**
   * determines if the specified number corresponds to a space the selected piece can move to
   * @param spacenumber the number of the space the user wants to move to
   * @return a String error message if the piece cannot be moved to
   */
  private String validateMove(int spacenumber) {
    System.out.println(spacenumber);

    if(spacenumber <= 0 || spacenumber > this.moves.size()) {
      return "Not a valid choice. Please choose a number between 1 and " +
             Integer.toString(this.moves.size()) + ".";
    }

    //It's valid!
    return null;
  }
}
