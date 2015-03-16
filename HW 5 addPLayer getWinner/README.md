<b>1 Purpose</b>

The goal of this assignment is to gain experience implementing your own interface and dealing with changing requirements.

<b>3 Your task</b>

<b>3.1 Extension</b>

Once you have reconciled your designs, it’s time to extend whichever design you choose in order to support the new requirement.

Modify the CoinGameModel interface to allow the client to select where in the order of play new players are placed.
Modify the representation in StrictCoinGameModel, if necessary, to represent the information required to implement the new interface. Update the documentation of its class invariants if they need to change as well.
Modify your tests in class StrictCoinGameModelTest to adequately exercise the new feature.

<b>3.2 Implementation</b>

Now is the moment you’ve all been waiting for: time to implement your API. If you find that any aspect of your design is impossible or too difficult to implement, you may change your design provided that you write a comment explaining the change, including its rationale. If for some reason your earlier decisions no longer appear to make sense, please explain why.

Complete your implementation of StrictCoinGameModel.
Be sure to run your tests against the complete implementation. You may find that some of your tests fail not because the model is wrong but because the tests are wrong. If so, that’s okay and you can fix the tests.

<b>3.3 Testing</b>

You should thoroughly test your code. In addition, I need some help testing it.

Because you are implementing an interface of your own design, I can’t test your code directly. Instead, I have defined a class that will enable you to code up several coin game scenarios—essentially, sequences of operations parameterized over the initial board—which I can then run with different initial boards in order to test.

Take a look at class CoinGameTestScenarios. It contains five static methods, each of which takes an initial board as a String, initializes a coin game model object model, performs several operations (such as moves and player insertions) on model, and then returns either the externalized board configuration (scenarios 1–4) or whether the game is over (scenario 5). Your job will be to fill in these methods in order to initialize model with the specified number of players, perform the operations on model, and in scenario 5 modify the return statement in order to return the desired boolean, all using the API that you designed.

Each place where you need to change the file is indicated with the comment /* fill in */. Nowhere else should be changed.

Fill in the testing scenarios in CoinGameTestScenarios.java.

<b>4 List of Deliverables</b>

  [CoinGameModel.java]

  [StrictCoinGameModel.java]

  [StrictCoinGameModelTest.java]

  NEW: [CoinGameTestScenarios.java]


  [CoinGameModel.java]:https://github.com/seanscal/OOD/blob/master/HW%205%20addPLayer%20getWinner/src/CoinGameModel.java
  [StrictCoinGameModel.java]:https://github.com/seanscal/OOD/blob/master/HW%205%20addPLayer%20getWinner/src/StrictCoinGameModel.java
  [StrictCoinGameModelTest.java]:https://github.com/seanscal/OOD/blob/master/HW%205%20addPLayer%20getWinner/src/StrictCoinGameModelTest.java
  [CoinGameTestScenarios.java]:https://github.com/seanscal/OOD/blob/master/HW%205%20addPLayer%20getWinner/src/CoinGameTestScenarios.java
