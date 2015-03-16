<main>
<h1 id="purpose"><span class="header-section-number">1</span> Purpose</h1><ul class="screenonly links">

<p>The goal of this assignment is to practice interface and representation design.</p>
<h1 id="design-exercise-adding-players"><span class="header-section-number">2</span> Design Exercise: Adding Players</h1>
<p>Last week, you implemented the <code>CoinGameModel</code></a> interface with two different sets of rules. As designed, the interface says nothing about players or turns—it merely tracks the state of the game from move to move, and it’s totally oblivious to <em>who</em> takes each turn.</p>
<p>Suppose, instead, that we wanted our coin game classes to keep track of whose turn it is, to enforce the order of play, and to be able to tell us who has won once the game is over. Additionally, we want to allow new players to join the game at any time. However players are identified to and by the client, this identification must be stable—that is, it should not change when new players join.</p>
<p>For these exercises, you will consider how to modify the interface design and the representation to support these new features. (You won’t need to implement any methods.)</p>
<h2 id="interface-design"><span class="header-section-number">2.1</span> Interface Design</h2>
<p>The interface needs to be updated to support keeping track of players, turns, and the winner. It should handle an arbitrary number of players, it must allow new players to join at any time, and players’ identities must be stable.</p>
<ol type="1">
<li>Modify the <code>CoinGameModel</code></a> interface to support the new features.</li>
</ol>
<p>Be sure to list any assumptions you make, and don’t forget Javadoc.</p>
<p><strong>You need not implement this new interface.</strong></p>
<h2 id="representation-and-invariants"><span class="header-section-number">2.2</span> Representation and Invariants</h2>
<p>Once you have designed the interface, you can begin designing a representation for it. Designing the representation means deciding what fields to use in order to implement the functionality specified by the interface, while thinking carefully about how the data corresponds to the information in the problem domain and about what invariants are needed to rule out representations that don’t map to the problem domain.</p>
<ol start="2" type="1">
<li><p>In <code>StrictCoinGameModel.java</code></a>, declare the fields needed to support the methods in the interface you’ve designed.</p></li>
<li><p>Below the declaration of the fields, write an implementor comment describing, as precisely as you can, your representation’s class invariant(s).</p></li>
</ol>
<p><strong>You need not implement any methods in this class.</strong></p>
<h2 id="construction"><span class="header-section-number">2.3</span> Construction</h2>
<p>Constructors and static factories are not part of the Java language’s notion of <code class="sourceCode java"><span class="kw">interface</span></code>, but they are still essential parts of the public interface that a class or hierarchy presents to its clients. Now that you know what your representation is and what your invariants are, you can design how instances will be constructed. You need not implement construction, however—we are just interested in the interface for now.</p>
<ol start="4" type="1">
<li>In <code>StrictCoinGameModel.java</code></a>, describe your constructor API. This comprises the names of any public constructors and static factory methods that you desire<a href="#fn1" class="footnoteRef" id="fnref1"><sup>1</sup></a>, along with the names and types of their parameters, and good Javadoc comments that document their usage, including any preconditions.</li>
</ol>
<p><strong>You need not implement the bodies of any constructors or factories.</strong></p>
<h2 id="testing"><span class="header-section-number">2.4</span> Testing</h2>
<p>Even before you write a line of your concrete class’s implementation, you should have a good idea how to test it. You’ve designed the mechanisms for creating <code>StrictCoinGameModel</code> objects and the methods for manipulating them, which means even though you haven’t implemented it yet, you know how to create game instances and invoke methods on them, and you know what results to expect. That means you can write tests.</p>
<ol start="5" type="1">
<li>In <code>StrictCoinGameModelTest.java</code>, write a comprehensive test suite for the new object construction and player functionality that you designed.</li>
</ol>
<p>You don’t need to test any features from Problem Set 3, since you’ve tested those already. Of course, you probably can’t get away with avoiding them entirely; for example, to test the winner functionality, you probably need to play some moves.</p>
<p><strong>You need not run these tests, because that would require implementing the class under test.</strong> Even though you cannot run them, writing tests is a good way to make sure your interface makes sense and to demonstrate that to the graders.</p>
<h1 id="deliverables"><span class="header-section-number">3</span> Deliverables</h1>
<ul>
<li><a href="../master/HW%204%20Explaining%20adding%20player/src/CoinGameModel.java"><code>CoinGameModel.java</code></li>
<li><a href="../maste/HW%204%20Explaining%20adding%20player/src/StrictCoinGameModel.java"><code>StrictCoinGameModel.java</code></li>
<li><a href="../master/HW%204%20Explaining%20adding%20player/src/StrictCoinGameModel.java"><code>StrictCoinGameModelTest.java</code></li>
</ul>
<h1 id="grading-standards"><span class="header-section-number">4</span> Grading Standards</h1>
<p>For this assignment, you will be graded on:</p>
<ul>
<li>the design of your <code>CoinGameModel</code> interface (primarily how well it supports the requisite functionality, though clarity and good style are important too),</li>
<li>the appropriateness of your representation choices for <code>StrictCoinGameModel</code>, and</li>
<li>the adequacy of its class invariants.</li>
</ul>
<ol>
<li id="fn1"><p>You don’t need to get fancy.<a href="#fnref1">↩</a></p></li>
</ol>
</section>
    </main>
