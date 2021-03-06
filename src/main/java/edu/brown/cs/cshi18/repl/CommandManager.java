package edu.brown.cs.cshi18.repl;

import java.util.ArrayList;
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
  private List<String> guiOutput;

  /**
   * Access method to get the output for the gui.
   * @return the output for the gui.
   */
  public List<String> getGuiOutput() {
    return guiOutput;
  }

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
   * An interface which a class that install commands will implement.
   */
  public interface Install {

    /**
     * A method to install specific command patterns to this CommandManager.
     *
     * @param cp this CommandManager
     */
    void installCommands(CommandManager cp);
  }

  /**
   * Handles installation for commands.
   *
   * @param installer an object that implements the install method
   */
  public void install(Install installer) {
    installer.installCommands(this);
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
        String[] los = line.split("\\s(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        List<String> list = Arrays.asList(los);
        commandMap.get(elt).execute(list.subList(1, list.size()));
        foundCommand = true;
        // pass nothing to gui
        guiOutput = null;
      }
    }
    if (!foundCommand && !line.equals("")) {
      // Pass error to repl and gui
      REPL.errorPrint("ERROR: Incorrect command or formatting");
      guiOutput = new ArrayList<>(List.of("ERROR: Incorrect command or formatting"));
    }
  }
}
