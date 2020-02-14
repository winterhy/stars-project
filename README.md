# README

## Stars
* Known bugs

  - None so far
  
* Design Details

* Runtime/Space optimizations

* How to run tests

  - Type maven package in the command line with star-winterhy as the directory
  
* How to run the program

  - Type ./run in 
  
* 

1. Suppose that in addition to "neighbors" and "radius" you wanted to support
10+ more commands. How would you change your code - particularly your repl
parsing - to do this? Don't worry about algorithmic details with the k-d
tree, we're interested in the design.

  I would have to install more commands in classes that implement the "Install" 
interface. I would also have to implement the new commands. I do not have to
change my REPL or command manager.

2. What are some problems you would run into if you wanted to use your k-d tree
to find points that are close to each other on the earth's surface? You do not
need to explain how to solve these problems.

  Hi.

3. Your k-d tree supports most of the methods required by the Collection
interface. What would you have to add/change to make code
like `Collection<Star> db = new KDTree<Star>()` compile and work properly?

