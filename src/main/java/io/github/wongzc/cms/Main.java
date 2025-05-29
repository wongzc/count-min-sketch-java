package io.github.wongzc.cms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, CMSketch<String>> cmsMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to Count-Min Sketch CLI!");
        System.out.println("Commands:");
        System.out.println("  c <name> <epsilon> <delta>  : Create a new sketch");
        System.out.println("  a <name> <value>            : Add a value");
        System.out.println("  e <name> <value>            : Estimate count of value");
        System.out.println("  r <name>                    : Reset sketch");
        System.out.println("  m <name1> <name2>           : Merge name2 into name1");
        System.out.println("  x                           : Exit");

        while (true) {
            System.out.print("> ");
            command = scanner.next();

            if (command.equals("x")) {
                break;
            }

            String cms;
            CMSketch<String> sketch, sketch2;
            switch (command) {
                case "c":
                    cms = scanner.next();
                    double epsilon = scanner.nextDouble();
                    double delta = scanner.nextDouble();
                    cmsMap.put(cms, new CMSketch<>(epsilon, delta));
                    System.out.println("Created sketch: " + cms);
                    break;

                case "a":
                    cms = scanner.next();
                    String valueToAdd = scanner.next();
                    sketch = cmsMap.get(cms);
                    if (sketch == null) {
                        System.out.println("Sketch not found: " + cms);
                        break;
                    }
                    sketch.add(valueToAdd);
                    System.out.println("Added " + valueToAdd + " to " + cms);
                    break;

                case "e":
                    cms = scanner.next();
                    String valueToEstimate = scanner.next();
                    sketch = cmsMap.get(cms);
                    if (sketch == null) {
                        System.out.println("Sketch not found: " + cms);
                        break;
                    }
                    int estimate = sketch.estimate(valueToEstimate);
                    System.out.println("Estimate of '" + valueToEstimate + "' in " + cms + " = " + estimate);
                    break;

                case "r":
                    cms = scanner.next();
                    sketch = cmsMap.get(cms);
                    if (sketch == null) {
                        System.out.println("Sketch not found: " + cms);
                        break;
                    }
                    sketch.reset();
                    System.out.println("Sketch " + cms + " reset.");
                    break;

                case "m":
                    String target = scanner.next();
                    String source = scanner.next();
                    sketch = cmsMap.get(target);
                    sketch2 = cmsMap.get(source);
                    if (sketch == null || sketch2 == null) {
                        System.out.println("One or both sketches not found.");
                        break;
                    }
                    sketch.merge(sketch2);
                    System.out.println("Merged " + source + " into " + target);
                    break;

                default:
                    System.out.println("Unknown command: " + command);
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}
