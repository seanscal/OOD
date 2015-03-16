<main>
<h1 id="purpose"><span class="header-section-number">1</span> Purpose</h1>
<p>The primary goal of this assignment is to get you writing Java (again or for the first time). As a secondary goal, completing this assignment will help you ensure that you have a properly configured and working Java development environment and IDE, and that you can use the submission system (to be described elsewhere).</p>
<h1 id="description"><span class="header-section-number">2</span> Description</h1>
<p>You must write a Java class <code class="sourceCode java">Webpage</code> that implements the <code class="sourceCode java">Publication</code> interface from lecture, with the same functionality as web page objects created by the ISL+λ function <code class="sourceCode scheme">new-webpage</code>, as seen in the lecture notes. You must also write a Junit4 test class, <code class="sourceCode java">WebpageTest</code>, that tests both methods of <code class="sourceCode java">Webpage</code>.</p>
<p>In addition to the two methods required to implement <code class="sourceCode java">Publication</code>, your <code class="sourceCode java">Webpage</code> class must define a constructor:</p>
<pre class="sourceCode java"><code class="sourceCode java">    <span class="kw">public</span> <span class="fu">Webpage</span>(String title, String url, String retrieved)</code></pre>
<p>The signature of the construtor, including the order of the arguments, is important to enable automatic grading for functional correctness. Don’t forget to write a good JavaDoc comment for the constructor.</p>
<p>The interface file <code>Publication.java</code>, which defines the interface <code class="sourceCode java">Publication</code>, is available in a ZIP file, which also contains other Java code written in Lecture 2.</p>
<p>Coding style is important. For this class, we will follow <a href="https://google-styleguide.googlecode.com/svn/trunk/javaguide.html">Google’s Java style guide</a>. It’s comprehesive, but not very long—I suggest reading the whole thing and then referring to it as needed.</p>
<h1 id="list-of-deliverables"><span class="header-section-number">3</span> List of Deliverables</h1>
<ul>
<li><a href="HW%201%20Book:Publication/src/Book.java"><code>Book.java</code></a></li>
<li><a href="HW%201%20Book:Publication/src/Book.java"><code>BookTest.java</code></a></li>
</ul>
<h1 id="grading-standards"><span class="header-section-number">4</span> Grading Standards</h1>
<p>For this assignment, you will be graded on</p>
<ul>
<li>whether your code implements the specification (functional correctness),</li>
<li>whether you test every method that you write, and</li>
<li>how well you follow the <a href="https://google-styleguide.googlecode.com/svn/trunk/javaguide.html">style guide</a>.</li>
</ul>
