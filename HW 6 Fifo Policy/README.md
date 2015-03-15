1 Purpose
The goal of this assignment is to practice reading, understanding, and testing unfamiliar code.

2 Too Many Books
Ann has a lot of books. So many books, in fact, that she has to store them in several places:

Her desk has a mere 3 shelf-feet of book capacity.

In the living room, she can store an additional 18 shelf-feet of books.

Books that don’t fit on either bookshelf are relegated to the attic.

Like any good computer scientist, Ann is lazy, so she wants to minimize trips from her desk to the living room, and moreso minimize trips to the attic. Thus, Ann would like to keep the books that she’s most likely to use soon on her desk, with less likely books in the living room, and the least likely books of all in the attic.

When Ann wants a book that is already by her desk, she can access it quickly, but if the book is in the living room, she needs to go fetch it. To make room on her desk, she must return a book to the living room. (We won’t worry about the attic from here on out, though the situation is similar. And then there’s the storage unit…) And when this happens, Ann needs to figure out which book to evict from her desk to make room for the next book that she requires.

Optimally, she would always evict the book that she’ll need again furthest in the future, but that’s difficult to predict, so instead Ann is considering a number of different book management policies:

She might choose to evict the book that has been on her desk the longest. (She calls this the FIFO policy.)

Or, she might choose to evict the book that she has looked at least recently. (This is the LRU policy.)

She is also considering a variation of the FIFO policy where instead of evicting the oldest book, she gives it a second chance, and only evicts it if it reaches the head of the queue again without being used. (This is the Clock policy, and don’t worry if you don’t understand it just yet.)

To try out the different book caching policies, Ann coded them up as Java classes that implement a ReplacementPolicy interface. The interface is generic, with a type parameter K that stands for whatever kind of items it’s being used to manage—in this case, books (or some way to refer to them, such as titles or ISBNs). The interface declares only three methods:

int capacity() returns the capacity of the cache—that is, the number of books she can fit on her desk.1

int size() returns the size of the cache—that is, the number of books on her desk currently.

K require(K) is where all the action happens. It takes the name of an item—the book that Ann wants to read next, whether on her desk or in the living room—and returns the item to evict, if necessary, to make room. If the requested item is already in the cache, or if the cache isn’t full yet, then this method returns null.

Ann is rather impatient, so the require(K) method must run in worst-case (capacity()) time.

2.1 Disaster!
Ann implemented all three policies, as classes FifoPolicy, LruPolicy, and ClockPolicy, but then disaster struck. She lost the source code and tests for FifoPolicy, but not the compiled Java .class file. She lost the source code and .class file for LruPolicy, but not the tests. The ClockPolicy code, fortunately, is safe, and the Javadoc output for all three classes survived as well; both may be handy as a reference.

Now Ann needs your help. She’s currently working on reimplementing the FifoPolicy class. To speed things up, she’d like you to implement the LruPolicy class and to write tests for her FifoPolicy class. Since the FifoPolicy.class object file survived the disaster, you will be able to run your tests on that code (though like I said, the source isn’t available). Once you have a good number of tests, Ann will use your test suite to validate her new FifoPolicy implementation, and hopefully your tests will catch any bugs.

2.2 Your Task
Read and understand the ClockPolicy and ClockTest classes. You don’t need to write anything, but you may find the knowledge handy later. (Note that ClockPolicy is implemented using low-level arrays, but you are free to use classes from the Java Collections framework such as LinkedList and HashSet for your code.)
Download the pset06.jar JAR file containing the compiled FifoPolicy class and add it to your project’s classpath. (How to do this will depend on your IDE, and help will be available on Piazza.)
Implement the LruPolicy class, as documented here. The class should have one constructor, LruPolicy(int), which takes the capacity of the queue (i.e., how many items can fit at once). The minimum allowed capacity is 1, so any smaller argument should result in an IllegalArgumentException being thrown. LruPolicyTest contains an example, and you should add comprehensive tests there as you see fit.
Write tests for Ann’s implementation of FifoPolicy (as documented here) in a JUnit 4 test class FifoPolicyTest. Run your tests against the provided binary implementation of FifoPolicy—all of them should pass. Be sure to write sufficient tests that you will catch any plausible bugs that Ann might write. Your tests will be run against several buggy FifoPolicy implementations, and you will be graded on how many bugs your tests catch—but they also need to pass when class under testing is correct.
3 List of Deliverables
LruPolicy.java
LruPolicyTest.java
FifoPolicyTest.java
4 Grading Standards
For this assignment, you will be graded on

whether your LruPolicy code implements the specification (functional correctness),
the comprehensiveness and correctness of your test coverage in FifoPolicyTest, and
how well you follow the style guide.
Regarding the second point, your FifoPolicy tests will be run on a correct implementation of FifoPolicy (included in the JAR file) as well as several incorrect versions. All your tests must pass on the correct version in order to earn points for finding bugs in the incorrect version.

5 Submission
This assignment is to be completed with your partner from Problem Set 5. Your submissions will be linked, so only one of you needs to submit, and only one of you needs to do self evaluation. (It doesn’t have to be the same one.)

Please submit your homework to the CS 3500 submission server by 11:59 PM on Tuesday, Feb. 17. Then be sure to complete your self evaluation by 11:59 PM on Thursday, Feb. 19.

Yes, all books are the same size.↩
