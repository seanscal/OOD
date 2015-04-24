import java.io.IOException;
import java.util.Objects;

public final class Controller {

  private final Model model;
  private final ReadOnlyBoardView bv;
  private final BoardView view;
  private final IntReader in;
  private final Appendable out;

  public static final String ERROR =
      "error";

  public Controller(Model model) {
    this(model, new ReadOnlyBoardView(model),
         new BoardView(System.out),
         IntReader.create(System.in, System.out, ERROR),
         System.out);
  }

  /**
   * Constructs a controller given the components it will control.
   *
   * @param model the model
   * @param view  the view, for rendering the model
   * @param in    the source of user moves
   * @param out   the output stream
   */
  public Controller(Model model, ReadOnlyBoardView bv, BoardView view, IntReader in,
                    Appendable out) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    Objects.requireNonNull(in);
    Objects.requireNonNull(out);
    Objects.requireNonNull(bv);

    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
    this.bv = bv;
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
      //step();
    }
  }

  /**
   * Runs one interaction step of this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console, fails
   */


  public void step() throws IOException {
    view.draw(bv);
    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose a piece to move:";
    int what = in.nextInt(playerstr + message, this::validateMovable);
    this.step2(what);

  }

  public void step2(int piece) {
    //once we have a method that
    Player who = model.getNextPlayer();
    String playerstr = "[" + Character.toString(who.toChar()) + "]";
    String message = " Choose where to move to:";
    int where = in.nextInt(playerstr + message, this::validateMove);
    //model.move(need values here);

    if(model.isGameOver()) {
      // do something here, display winner or whatever, haven't taken the time to
      // finish one of his games yet
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
    // need to write a method that returns an array of move options for a given check
    if(spacenumber <= 0 || spacenumber > model.moveOptions(Check number).size()) {
      return "You cannot move to that space";
    }

    //It's valid!
    return null;
  }

}