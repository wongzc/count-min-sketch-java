import java.util.Random;


public class CMSketch {
    private int[][] table;
    private int depth, width;
    private int[] hashSeeds;

    public CMSketch(int depth, int width) {
        this.depth = depth;
        this.width = width;
        this.table = new int[depth][width];
        this.hashSeeds = new int[depth];

        Random rand = new Random();
        for (int i = 0; i<depth; i++) {
            hashSeeds[i] = rand.nextInt();
        }
    }

    private int hash(String key, int seed) {
        return Math.abs((key+seed).hashCode())%width;
    }

    public void add(String key) {
        for (int i=0; i<depth;i++) {
            int col = hash(key, hashSeeds[i]);
            table[i][col]++;
        }
    }

    public int estimate(String key) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i<depth; i++) {
            int col = hash(key, hashSeeds[i]);
            min= Math.min(min, table[i][col]);
        }
        return min;
    }


}