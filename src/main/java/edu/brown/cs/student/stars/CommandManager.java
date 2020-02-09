package edu.brown.cs.student.stars;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * A CommandManager for various classes.
 */
public class CommandManager {
  private HashMap<String, Command> commandMap = new HashMap<String,
      Command>();

  /**
   * An interface that the command classes will implement.
   */
  public interface Command {

    /**
     * An execute method for classes who implements
     * command.
     *
     * @param tokens list of arguments needed for the method
     */
    void execute(List<String> tokens);
  }

    /**
     * Takes in a pattern and a corresponding command class
     * to add to commandMap.
     *
     * @param pattern regex.Pattern format for input
     * @param c command classes from installCommands
     */
  public void register(String pattern, Command c) {
    // can map multiple patterns to one command
    commandMap.put(pattern, c);
  }

  /**
   * Takes in string input from the REPL, see if it
   * matches one of the patterns in the commandMap
   * key sets. If it does, call execute on the corresponding
   * Command class (value of the commandMap).
   *
   * @param line the input line from the REPL
   */
  public void process(String line) {
    // Creates a set of the map keys (aka accepted patterns)
    Set<String> patterns = commandMap.keySet();
    // Creates an iterator object
    Iterator<String> itr = patterns.iterator();
    // Loop through the iterator
    boolean foundCommand = false;
    while (itr.hasNext()) {
      // Picks an element from the iterator
      String elt = itr.next();
      // See if the input line follows the regexp
      if (Pattern.matches(elt, line)) {
        String[] los = line.split("\\s");
        List<String> list = Arrays.asList(los);
        commandMap.get(elt).execute(list.subList(1, list.size() - 1));
        foundCommand = true;
      }
    }
    if (!foundCommand) {
      System.out.println("ERROR: Incorrect command or formatting");
    }
  }
}
