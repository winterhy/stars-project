package edu.brown.cs.cshi18.gui;

import java.util.ArrayList;
import java.util.List;

public class GUIHelper {
  private List<String> result = new ArrayList<>();

  /**
   * Access method for result field.
   * @return returns the result.
   */
  public List<String> getResult() {
    return result;
  }

  public void setResult(String input) {
    result.add(input);
  }

}
