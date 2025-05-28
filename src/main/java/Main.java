public class Main {
    public static void main(String[] args) {
        CMSketch<String> sketch = new CMSketch<>(0.01,0.01);
        sketch.add("apple");
        sketch.add("apple");
        sketch.add("banana");

        System.out.println("Sketch 1:");
        System.out.println("apple: " + sketch.estimate("apple"));  
        System.out.println("banana: " + sketch.estimate("banana"));  
        System.out.println("orange: " + sketch.estimate("orange"));  

        CMSketch<String> sketch2 = new CMSketch<>(0.01,0.01);
        sketch2.add("apple");
        sketch2.add("orange");

        System.out.println("Sketch 2:");
        System.out.println("apple: " + sketch2.estimate("apple"));  
        System.out.println("banana: " + sketch2.estimate("banana"));  
        System.out.println("orange: " + sketch2.estimate("orange"));

        sketch.merge(sketch2);

        System.out.println("After merge:");
        System.out.println("apple: " + sketch.estimate("apple"));  
        System.out.println("banana: " + sketch.estimate("banana"));  
        System.out.println("orange: " + sketch.estimate("orange"));


    }
}