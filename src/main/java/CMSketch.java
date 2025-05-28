import java.util.Random;


public class CMSketch<T> {
    private int[][] table;
    private int depth, width;
    private int[] hashSeeds;

    public CMSketch(double epsilon, double delta) {
        this.depth = (int) Math.ceil(Math.E/ epsilon);
        this.width = (int) Math.ceil(Math.log(1/delta));
        this.table = new int[depth][width];
        this.hashSeeds = new int[depth];

        Random rand = new Random();
        for (int i = 0; i<depth; i++) {
            hashSeeds[i] = rand.nextInt();
        }
    }

    private int hash(T key, int seed) {
        int h = key.hashCode()^seed; // for multiple independent hash function
        h ^= (h>>>16); // int is 32 bit, we are XOR-ing the 1st 16 bit with 2nd 16 bit to intorduce more radomness 
        return Math.abs(h)%width;
    }

    public void add(T key) {
        for (int i=0; i<depth;i++) {
            int col = hash(key, hashSeeds[i]);
            table[i][col]++;
        }
    }

    public int estimate(T key) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i<depth; i++) {
            int col = hash(key, hashSeeds[i]);
            min= Math.min(min, table[i][col]);
        }
        return min;
    }


}