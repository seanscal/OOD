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
   * @param view the view, for rendering the model
   * @param in the source of user moves
   * @param out the output stream
   */
  public Controller(Model model, ReadOnlyBoardView bv, BoardView view, IntReader in, Appendable out) {
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
   * @throws IOException if an I/O operation, such as writing to the console,
   * fails
   */
  public void run() throws IOException {
    while (! isGameOver()) {
      //step();
    }
  }

  /**
   * Runs one interaction step of this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console,
   * fails
   *
   *
   * */

/*
   public void step() throws IOException {
    view.draw(bv);

    Player who = model.getNextPlayer();
    Check what = in.nextInt()
    int where = in.nextInt(who + ": ", this::validateMove);

    model.move(who, where);

    if (model.isGameOver()) {
      view.draw(bv);

      if (model.getStatus() == Model.Status.Stalemate) {
        out.append(NOBODY_WINS).append('\n');
      } else {
        String winner = model.getWinner().toString();
        out.append(String.format(PLAYER_WINS, winner)).append('\n');
      }
    }
  }
  */
}