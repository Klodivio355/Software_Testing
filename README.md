# Introduction
Welcome to the assignment for the "COM3529 Software Testing and Analysis" module at the University
of Sheffield! This repository contains my solution to the automation of testing on Java functions.
This assignment has not been realised as part of a team, I was the only student working on this project.

## Folder Presentation

I used the same folder as the one given by the lecturer as a starting point. Therefore, my solution can be found
in : uk/ac/shef/com3529/practicals.

I will thereby present all the files I included.

DataParser.java -> This file is the only runnable java class, it is the class that parses the java method and run testing. <br />
Coverage.java -> Implements the main functions related to testing. <br />
MethodTree.java -> This file has been kept to show my thinking process but is not used in my solution, this is a data structure that aimed at
replicating a branch coverage tree that was originally meant to store complex architecture (loops) for methods in a BST. <br />

Relation.java , LogicStatementNode.java, Statement.java, Criterion.java -> Java classes for objects and their respective constructors and toStrings.

BMICalculator.java, Classification.java -> Java methods to be tested by my solution.

## Solution Details

#### 1. Analysis of the Method Under Test

My solution provided an automated process for parsing the java method, however, it holds some limitations :
- 2 user-defined variables cannot be compared together (when it comes to testing)
- Loops are not taken care of here.

The java methods for parsing are all in DataParser.java, some print statements are commented out at key points or can be easily put here and there to see what each step does. 

#### 2. Test Requirement Generation

My coverage criterion that I used for this assignment was Restricted MCDC, and it is the only criterion implemented.
Moreover, this coverage criterion is hard-coded in the sense that as it is depending on whether the logic is linked by an AND or OR statements and the length, a node can be processed in the same way with respect to the truth table it has generated.

#### 4. Test Data Generation

My solution implements a Configurable Random Number Generation with lower and upper bounds, similar to the RandomlyTestTriangle class presented in the lectures. This section needs improvement but is also a major bit that I struggled to implement on my own. The main drawback is that my solution is only able to compare numerical values.

#### 5. Coverage Level Computation

Before testing is performed the truth table is printed per node (line where there is either IF/ELSE IF/ELSE), and then the MCDC coverage criterion table is being rendered. After that testing is performed (I will come back to it later) the criteria covered/uncovered are reported in the 'satisfied' field.

#### 6. Test Suite Output

All of that is being rendered on the command line per iteration of testing with useful print statements to inform what node is being processed, what random integer was generated and where it evaluated to true (where it has stopped), also if any new MCDC criterion has been fulfilled.

## Solution Run

The very first thing to provide to the system is what java method (what file) we want to process, this can be changed in the DataParse.java class on the variable 'path', the first line of the main method.

After that, it should be good to run DataParser.java and to watch what the command line outputs.

Now, let's take for example the method provided in the assignment's description pdf. The BMICalculator.java class.

https://ibb.co/DVnxRnH

On the image above, you should expect to have this showing on the command line. The first bine showed the lines that are gonna be processed by the parcer, then, it creates objects with those lines (Processing node : i), then it shows per node, what truth table is being computed, and what MCDC requirements are being kept. All these requirements are then summarised. 

After such processing, we can move on to perform testing on these nodes. The image below shows how the testing process is being carried out. 

https://ibb.co/34Ln1M8

On the image above, we can see that at each iteration of testing, it prompts the number of the iteration, then notifies what number was generated and its progression throughout the method and notifies accordingly whenever a new MCDC requirement has been satisfied or whether a branch has evaluated to true and therefore where it has stopped. This output is the one you have high probability to get on the BMICalculator.java with MIN = 10, MAX = 50 and ITERATIONS = 100.

## Example

I prepared another method, Classification.java, and I knowingly made it look bad and not intuitive. It classifies a mark (out of 20) into 4 categories. You can clearly notice that the function is quite weird in the way it's logic is implemented (switching variables in unconventional way). There is also an unreachable criterion, I wanted my system to be able to retrieve it. Let's find out!

If you run DataParser.java on this, you should expect to have this output with MIN = 0, MAX = 20 (assuming the method can only be given that), ITERATIONS = 100 (To be changed in Coverage.java)

https://ibb.co/2Mf3B4g

And indeed it manages to find it, as of course, if the value equals 10 it cannot be greater than 15. Both cannot evaluate to true, even though the predicate overall returns true as it is a OR operator

## Discussion

I understand that this solution is quite limited but I also think that it introduces some nice and extendable basis code to implement more advanced techniques of testing. I also wanted to point out that I have spent most of time trying to figure out how I could make my system work with complex codes involving loops and dependency on outer IF statements for instance but again, this feature could be implemented by tweaking the parser and maybe the objects so that it takes into consideration the dependency of even the tabs in the code analysis. Many more features could extended but requires a lot of work that I believe I cannot do in the indicative time given.

I truly enjoyed this assignment!

Thank you!

