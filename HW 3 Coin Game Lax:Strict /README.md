<h1 id="purpose"><span class="header-section-number">1</span> Purpose</h1>
<p>The primary goal of this assignment is to practice thinking carefully about invariants, including</p>
<ul>
<li>identifying invariants in existing code,</li>
<li>designing invariants for new code, and</li>
<li>writing new code with the invariants that you design.</li>
</ul>
<h1 id="invariants-warm-up-lazy"><span class="header-section-number">2</span> Invariants Warm-Up: <code>Lazy</code></h1>
<p>This section of the assignment is about identifying class invariants and method preconditions.</p>
<p>The class <code>Lazy</code></a> implements lazy evaluation, which is a strategy for computing a value just once, when it’s needed, and then saving it in case it’s needed again.</p>
<p>The <code>Lazy</code> class relies on a class invariant for correctness. Your task is as follows:</p>
<ol type="1">
<li><p>Identify and document <code>Lazy</code>’s class invariant.</p></li>
<li><p>In method <code class="sourceCode java">Lazy#<span class="fu">force</span>()</code>, field <code>value</code> is assigned before field <code>thunk</code>. What could happen if the order of these two assignments was flipped? Add your answer as an <em>implementor comment</em><a href="#fn2" class="footnoteRef" id="fnref2"><sup>2</sup></a> in the method.</p></li>
</ol>
<p>For several small examples of how <code>Lazy</code> works, it may be helpful to see the <code>LazyTest</code> class</a>.</p>
<h1 id="a-coin-game"><span class="header-section-number">3</span> A Coin Game</h1>
<p>In the rest of this problem set, you will design and implement a model for a simple coin game (or rather, a family of coin games).</p>
<h2 id="the-rules"><span class="header-section-number">3.1</span> The Rules</h2>
<p>The coin game is played on a “board” consisting of a strip of paper divided into a sequence of squares. (You can think of it as a one-dimensional grid of boxes.) Each square may be occupied by a single coin, or empty. For convenience, we can write down the state of a coin game using a simple notation of <code>-</code> for empty squares and <code>O</code> (that’s an uppercase letter “o” as in “orange”) for squares containing a coin. For example:</p>
<ul>
<li><code>----</code> is a board with four squares and no coins</li>
<li><code>O--O-</code> is a board of five squares, with coins in the 0th and 3rd positions. (We count from 0.)</li>
<li><code>OOOOOOOO</code> is a board of eight squares, full with eight coins.</li>
</ul>
<p>We refer to the coins by numbering them from the left, starting from 0. So for example, in board configuration <code>--O-O</code>, coin 0 is in position 2 and coin 1 is in position 4.</p>
<p>A move in the game consists of moving a coin from a square to an unoccupied square. (Two coins cannot be placed in the same square.) How coins are allowed to be moved depends on the variant of the game. You will implement two variants:</p>
<ul>
<li><p>In the <em>strict coin game</em>, a coin can be moved any number of squares to the left, but it cannot pass another coin. </p>

<p>However, coin 1 cannot be moved past coin 0, so configuration <code>OO---</code> cannot be reached in only one move.</p></li>
<li><p>In the <em>lax coin game</em>, coins move to the left as in the strict coin game, but they can also skip past other coins. Thus, given configuration <code>-O--O</code>, the three moves valid in the strict coin game shown above are also valid in the lax coin game. But moving coin 1 to position 0, skipping over coin 0 and yielding configuration <code>OO---</code>, is valid in the lax variant as well.</p></li>
</ul>
<p>Game play proceeds by players taking turns, and ends when no move can be made. The last player to make a move wins.</p>
<h2 id="the-interface"><span class="header-section-number">3.2</span> The Interface</h2>
<p>The interface <code>CoinGameModel</code></a> declares the operations for a coin game implementation. It includes observer methods for finding out the number of squares on the board, the number of coins, the position of each coin, and whether the game is over. It has one mutator method, <code>move(int coinIndex, int newPosition)</code>, for making moves. (It doesn’t keep track of whose turn it is or who wins.) The interface also defines a nested exception class <code>IllegalMoveException</code>, which <code>move</code> must throw when the requested move is illegal (as defined by whichever variant of the game is implemented).</p>
<h2 id="your-implementation"><span class="header-section-number">3.3</span> Your Implementation</h2>
<p>Your task is to implement <code>CoinGameModel</code> in two classes, <code>StrictCoinGameModel</code> and <code>LaxCoinGameModel</code>, which enforce the strict and lax versions of the rules, respectively. What you need to do (for each class):</p>
<ol start="3" type="1">
<li><p>Carefully design a representation for the game state. Think carefully about what invariants your class will need to preserve to ensure valid representations. Clearly document these invariants in an implementor comment.</p>
<p>Your choice of representation may significantly affect how easy or difficult it is to implement the necessary operations, and you will be graded on the appropriateness of this choice. (<em>Hint:</em> Good <em>external representations</em> that appear in APIs for convenient testing and debugging do not always make good <em>internal representations</em> for computing with.)</p></li>
<li><p>Implement all the methods in the interface. (<em>Hint:</em> You needn’t implement <em>each</em> method independently in <em>each</em> concrete class, if you can somehow factor out common functionality.)</p></li>
<li><p>Override the <code>toString()</code> method so that it returns the board represented in the external <code>-</code>-and-<code>O</code> notation used to discuss board configurations above.</p></li>
<li><p>Implement public constructors <code>StrictCoinGameModel(String board)</code> and <code>LaxCoinGameModel(String board)</code>. These take a board written in <code>-</code>-and-<code>O</code> notation and initialize the board (however you choose to represent it) to match. If <code>board</code> contains any characters other than <code>'-'</code> and <code>'O'</code>, it does not represent a valid configuration, so the constructors raise an <code>IllegalArgumentException</code>.</p></li>
<li><p>Write sufficient tests to be confident that your code is correct.</p></li>
<li><p>Properly document your code, with Javadoc, as appropriate. Method implementations that inherit Javadoc need not provide their own unless their contract differs from the inherited documentation. Be sure to document all method preconditions.</p></li>
</ol>
<h1 id="list-of-deliverables"><span class="header-section-number">4</span> List of Deliverables</h1>
<ul>
<li><a href="src/Lazy.java"><code>Lazy.java</code> (documented invariants)</li>
<li><a href="src/StrictCoinGameModel.java"><code>StrictCoinGameModel.java</code> (implementation)</li>
<li><a href="src/LaxCoinGameModel.java"><code>LaxCoinGameModel.java</code> (implementation)</li>
<li>all necessary helper code and tests:</li>
<li><a href="src/AbstractCoinGameModel.java"><code>AbstractCoinGameModel.java</code></li>
<li><a href="src/AbstractCoinGameModelTest.java"><code>AbstractCoinGameModelTest.java</code></li>
<li><a href="src/LaxCoinGameModelTest.java"><code>LaxCoinGameModelTest.java</code></li>
<li><a href="src/StrictCoinGameModelTest.java"><code>StrictCoinGameModelTest.java</code></li>
</ul>
<h1 id="grading-standards"><span class="header-section-number">5</span> Grading Standards</h1>
<p>For this assignment, you will be graded on</p>
<ul>
<li>whether your code implements the specification (functional correctness),</li>
<li>how accurately you identify and state invariants,</li>
<li>the comprehensiveness of your test coverage, and</li>
<li>how well you follow the <a href="https://google-styleguide.googlecode.com/svn/trunk/javaguide.html">style guide</a>.</li>
</ul>
<ol>
<li id="fn1"><p>By convenience I mean that we can express what it does without it, but it might be more convenient to have it.<a href="#fnref1">↩</a></p></li>
<li id="fn2"><p>Meaning a comment intended for someone reading your code, rather than part of the documentation for writing clients of your code.<a href="#fnref2">↩</a></p></li>
</ol>
</section>
    </main>
