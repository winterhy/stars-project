package edu.brown.cs.cshi18.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.cshi18.repl.CommandManager;
import edu.brown.cs.cshi18.repl.REPL;
import edu.brown.cs.cshi18.stars.Universe;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  private REPL repl = new REPL();
  private CommandManager manager = new CommandManager();
  private Universe universe = new Universe();

  /**
   * The initial method called when execution begins.
   *
   * @param args
   *          An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();

  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
    .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // Installs the commands in programs
    manager.install(universe);
    // The repl starts reading
    repl.read(manager);
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.post("/neighbors", new NeighborsSubmitHandler(), freeMarker);
    Spark.post("/radius", new RadiusSubmitHandler(), freeMarker);
  }

  /**
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars",
          "message", "Welcome to Stars.",
          "neighbors", "",
          "radius", "");
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   *  A handler to print out suggestions once form is submitted.
   *  @return ModelAndView to render.
   *  (stars.ftl).
   */
  private class NeighborsSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String inputFromNeighbors = qm.value("neighbors");

      String neighborCommand = String.join(" ", List.of("neighbors", inputFromNeighbors));
      manager.process(neighborCommand);
      if (manager.getGuiOutput() != null) {
        String joinedError = String.join("", manager.getGuiOutput());
        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars",
            "message", "Welcome to Stars.",
            "neighbors", joinedError,
            "radius", "");
        return new ModelAndView(variables, "query.ftl");
      }

      List<String> neighborsOutput = universe.getNeighborsOutput();
      String joinedNeighbors = String.join("\n", neighborsOutput);
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars",
          "message", "Welcome to Stars.",
          "neighbors", joinedNeighbors,
          "radius", "");
      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   *  A handler to print out suggestions once form is submitted.
   *  @return ModelAndView to render.
   *  (stars.ftl).
   */
  private class RadiusSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String inputFromRadius = qm.value("radius");

      String radiusCommand = String.join(" ", List.of("radius", inputFromRadius));
      manager.process(radiusCommand);
      if (manager.getGuiOutput() != null) {
        String joinedError = String.join("", manager.getGuiOutput());
        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars",
            "message", "Welcome to Stars.",
            "neighbors", "",
            "radius", joinedError);
        return new ModelAndView(variables, "query.ftl");
      }

      List<String> radiusOutput = universe.getRadiusOutput();
      String joinedRadius = String.join("\n", radiusOutput);
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars",
          "message", "Welcome to Stars.",
          "neighbors", "",
          "radius", joinedRadius);
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}