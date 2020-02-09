package edu.brown.cs.student.stars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * REPL class that reads the input stream and
 * sends the stream to the CommandManager.
 */
public class REPL {
  /**
   * This method takes in a CommandManager and sends a string from
   * a BufferedReader to it.
   *
   * @param cp generic CommandManager to handle inputs from REPL
   */
  public void read(CommandManager cp) {
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in));
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        cp.process(line);
      }
    } catch (IOException e) {
      System.out.println("ERROR: Bad input.");
    }
  }
}
