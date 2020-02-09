package edu.brown.cs.student.stars;

import java.util.List;

public class Universe {
    //interface install
    //but stars command should populate a universe???????

    int x = 0;
    //KDTree here (populated from parser)

    public void installCommands(CommandManager cp) {

        cp.register("stars\\s.*", new StarsCommand());
        // TODO: Need to change to accept decimals and no negatives for first
        // Non-negative integer first and rest are real numbers
        cp.register("neighbors\\s(\\d+)(?:\\s(-?\\d+(\\.\\d+)?)){3}",
            new NeighborsCommand());
        cp.register("neighbors\\s\".*\"", new NeighborsCommand());
        // Non-negative real number first and rest are real numbers
        cp.register("radius\\s(\\d+(\\.\\d+)?)(?:\\s(-?\\d+(\\.\\d+)?)){3}", new RadiusCommand());
        cp.register("radius\\s\".*\"", new RadiusCommand());

    }

    class StarsCommand implements CommandManager.Command {

        @Override
        public void execute(List<String> tokens) {
            // calls parser here
            System.out.println("Read n stars from <filename>");
        }
    }

    class NeighborsCommand implements CommandManager.Command {

        @Override
        public void execute(List<String> tokens) {

            x++;
            System.out.println("Neighbors");
        }

    }

    class RadiusCommand implements CommandManager.Command {

        @Override
        public void execute(List<String> tokens) {

            System.out.println("Radius");
        }
    }


}
