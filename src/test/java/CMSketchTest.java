import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CMSketchTest {

    @Test
    public void testBasicInsertAndEstimate() {
        CMSketch<String> sketch = new CMSketch<>(0.01, 0.01);
        sketch.add("apple");
        sketch.add("apple");
        sketch.add("banana");

        assertTrue(sketch.estimate("apple") >= 2);
        assertTrue(sketch.estimate("banana") >= 1);
        assertEquals(0, sketch.estimate("orange"));
    }

    @Test
    public void testMerge() {
        CMSketch<String> a = new CMSketch<>(0.01, 0.01);
        CMSketch<String> b = new CMSketch<>(0.01, 0.01);
        a.add("x");
        a.add("x");
        b.add("x");
        b.add("y");

        a.merge(b);

        assertTrue(a.estimate("x") >= 3);
        assertTrue(a.estimate("y") >= 1);
    }
}
