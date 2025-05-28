public class Main {
    public static void main(String[] args) {
        CMSketch<String> sketch = new CMSketch<>(0.01,0.01);
        sketch.add("apple");
        sketch.add("apple");
        sketch.add("banana");

        System.out.println("apple: " + sketch.estimate("apple"));  // Expected: 2
        System.out.println("banana: " + sketch.estimate("banana"));  // Expected: 1
        System.out.println("orange: " + sketch.estimate("orange"));  // Expected: 0 or close

    }
}