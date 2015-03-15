<b>1. Purpose</b>

The goal of this assignment is to practice reading, understanding, and testing unfamiliar code.

<b>2.2 Your Task</b>

Read and understand the ClockPolicy and ClockTest classes. You don’t need to write anything, but you may find the knowledge handy later. (Note that ClockPolicy is implemented using low-level arrays, but you are free to use classes from the Java Collections framework such as LinkedList and HashSet for your code.)
Download the pset06.jar JAR file containing the compiled FifoPolicy class and add it to your project’s classpath. (How to do this will depend on your IDE, and help will be available on Piazza.)
Implement the LruPolicy class, as documented here. The class should have one constructor, LruPolicy(int), which takes the capacity of the queue (i.e., how many items can fit at once). The minimum allowed capacity is 1, so any smaller argument should result in an IllegalArgumentException being thrown. LruPolicyTest contains an example, and you should add comprehensive tests there as you see fit.
Write tests for Ann’s implementation of FifoPolicy (as documented here) in a JUnit 4 test class FifoPolicyTest. Run your tests against the provided binary implementation of FifoPolicy—all of them should pass. Be sure to write sufficient tests that you will catch any plausible bugs that Ann might write. Your tests will be run against several buggy FifoPolicy implementations, and you will be graded on how many bugs your tests catch—but they also need to pass when class under testing is correct.

<b>3 List of Deliverables</b>

	LruPolicy.java

	LruPolicyTest.java

	FifoPolicyTest.java


<b>4 Grading Standards</b>

For this assignment, you will be graded on whether your LruPolicy code implements the specification (functional correctness),
the comprehensiveness and correctness of your test coverage in FifoPolicyTest, and how well you follow the style guide.
Regarding the second point, your FifoPolicy tests will be run on a correct implementation of FifoPolicy (included in the JAR file) as well as several incorrect versions. All your tests must pass on the correct version in order to earn points for finding bugs in the incorrect version.


Yes, all books are the same size.↩
