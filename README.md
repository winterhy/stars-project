# README

## Stars
* Known bugs

  - None known
  
* Design Details

  - Package Structure: I made five packages for this project. 
    - The main package is self explanatory and only contains the main method which the program uses to
      run. 
    - The parser package contains a csv parser that parses the file into a
      list of list of strings, where each individual list of strings is from each
      row of the file that gets parsed into separate strings (by comma). The whole
      list contains the rows in order. 
    - The repl package contains the generic repl entity and the command manager.
    - The stars package contains classes unique to the stars program. The star
    class is mainly used to store the fields of a star: id, name, and coordinates.
    The universe class installs and execute all the commands unique to the stars.
    It also has a field of the KDtree.
    - The trees package contains all the relevant classes to the KDTree. The 
    classes than implements comparator are there to help with sort within
    the constructor of the KDTree and the search algorithms.
    
  - KDTree: I do not have a separate node class for KDTrees. The root of the 
  KDTree is any object that implements HasCoordinates. The left and right
  children of the KDTree are two smaller KDTrees. Each, of course, have their
  own roots and children. A children can be null if there are no object left in
  the corresponding sublist. A root with two null children is a "leaf" and is
  the end case for the recursion.

* Runtime/Space optimizations

    - I used a priority queue in my neighbors search algorithm with the furthest
    neighbor being the head of the queue. This way, I can remove it efficiently.
    If the queue size is equal to k, I can always insert the current node first,
    and then I can remove the furthest neighbor. If the current node is the
    furthest neighbor, it will be as if it was never there. If the current node
    isn't, then another neighbor that is the furthest will be removed.
    
    - I use a HashMap to set and find stars that have names

* How to run tests

  - For JUnit Tests, type `mvn package` or `mvn site` in the terminal with
   star-winterhy as the directory. For ta system tests, type
    `./cs32-test tests/ta/stars/*` in the terminal with star-winterhy as the 
    directory to test every ta system tests. For my own student system tests,
    type `./cs32-test tests/student/stars/*` to test all of them.
  
* How to run the program

  - Type `./run` to start the REPL without the GUI. Exit by Ctrl+D. Type
  `./run --gui --port 4567` to run with the GUI. It will exist in
  localhost:4567/stars
  - The repl during GUI unfortunately does not support Ctrl+D. Ctrl+C is needed.
  - Please first load the file in the terminal using the stars command
   `stars filename` with filename being the path to the file.
  
* Design Questions

  - Question: Suppose that in addition to "neighbors" and "radius" you wanted to support
10+ more commands. How would you change your code - particularly your repl
parsing - to do this? Don't worry about algorithmic details with the k-d
tree, we're interested in the design.

    - Answer: I would have to install more commands in classes that implement the "Install" 
interface. I would also have to implement the new commands. I do not have to
change my REPL or command manager.

  - Question: What are some problems you would run into if you wanted to use your k-d tree
to find points that are close to each other on the earth's surface? You do not
need to explain how to solve these problems.

    - The surface of the Earth is curved. The KDTree uses euclidean distance to
    compute closeness. However, that would not work on a curved surface.
    Another coordinate system will be needed.

  - Question: Your k-d tree supports most of the methods required by the Collection
interface. What would you have to add/change to make code
like `Collection<Star> db = new KDTree<Star>()` compile and work properly?

    - My k-d tree will need to support every method that are not optional 
    in the Collection interface and the Iterable interface. I will have to add all those methods 
    to my code. I will not list every single method, but some useful ones are 
    `size()` to find the number of elements in the tree. Or `contains(Object o)`
    which will use a search method similar to `neighbors` but expanded to the 
    entire tree.

* Explanation for Checkstyle Errors

  - No errors.

