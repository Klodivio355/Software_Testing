# Introduction
Welcome to the assignment for the "COM3529 Software Testing and Analysis" module at the University
of Sheffield! This repository contains my solution to the automation of testing on Java functions.
This assignment has not been realised as part of a team, I was the only student working on this project.

## Folder Presentation

I used the same folder as the one given by the lecturer as a starting point. Therefore, my solution can be found
in : uk/ac/shef/com3529/practicals.

I will thereby present all the files I included.

DataParser.java -> This file is the only runnable java class, it is the class that parses the java method and run testing.
Coverage.java -> Implements the main functions related to testing.
MethodTree.java -> This file has been kept to show my thinking process but is not used in my solution, this is a data structure that aimed at
replicating a branch coverage tree that was originally meant to store complex architecture (loops) for methods in a BST.

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




