import java.io.IOException;
import java.util.Objects;

public final class Controller {

  private final Model model;
  //private final ReadOnlyBoardView bv;
  private final BoardView view;
  private final IntReader in;
  private final Appendable out;
  public static final String ERROR =
      "error";

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
    //this.bv = new ReadOnlyBoardView(model);
  }

  public boolean isGameOver() {
    return model.isGameOver();
  }

  /**
   * Runs this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console, fails
   */
  public void run() throws IOException {
    while (!isGameOver()) {
      step();
    }
  }

  /**
   * Runs one interaction step of this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console, fails
   */


  public void step() throws IOException {
    ReadOnlyBoardViewModel bv = new ReadOnlyBoardView(model, new Check(0, 0), false);
    view.draw(bv);
    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose a piece to move: ";
    int what = in.nextInt(playerstr + message, this::validateMovable);
    Check c = model.movablePieces().get(what - 1);
    //c.isSelected = true;
    //c.changeSelected(c, true);
    //model.selected = c;
    model.changeSelected(c);

    this.step2(what);

  }

  public void step2(int piece) throws IOException {
    Check c = model.movablePieces().get(piece - 1);
    System.out.println("c x and y is " + Integer.toString(c.x) + Integer.toString(c.y));
    System.out.println(model.board.moves(c));
    ReadOnlyBoardViewModel bv = new ReadOnlyBoardView(model, c, true);
    view.draw(bv);


    //c.isSelected = false;
    //c.changeSelected(c, false);
   //model.selected = null;
    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose where to move to: ";
    int where = in.nextInt(playerstr + message, this::validateMove);
    Check moveIt = model.board.moves(c).get(where - 1);
    model.move(c.x, c.y, moveIt.x, moveIt.y);

    if(model.isGameOver()) {
      // do something here, display winner or whatever, haven't taken the time to
      // finish one of his games yet
      view.draw(bv);
      String winner = "[" + Character.toString(model.getWinner().toChar()) + "]";
      String winMessage = " is the winner";
      out.append(winner).append(winMessage);
    }
  }

  private String validateMovable(int piecenumber) {
    if (piecenumber <= 0 || piecenumber > model.movablePieces().size()) {
      return "This is not a movable piece";
    }

    // It's valid!
    return null;
  }

  private String validateMove(int spacenumber) {
    Check c = model.movablePieces().get((spacenumber - 1));
    // need to write a method that returns an array of move options for a given check
    if(spacenumber <= 0 || spacenumber > model.board.moves(c).size()) {
      return "You cannot move to that space";
    }

    //It's valid!
    return null;
  }

}