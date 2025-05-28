

public class CMSketch<T> {
    private int[][] table;
    private int depth, width;
    private int[] hashSeeds;

    public CMSketch(double epsilon, double delta) {
        this.depth = (int) Math.ceil(Math.E/ epsilon);
        this.width = (int) Math.ceil(Math.log(1/delta));
        this.table = new int[depth][width];
        this.hashSeeds = PrimeGenerator.generatePrimes(depth);

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

    // merge in other table ( if the width and dimension is the same)
    public void merge(CMSketch<T> other) {
        if ( this.depth != other.depth || this.width != other.width) {
            throw new IllegalArgumentException("Sketch dimensions need to be same!");
        }

        for ( int i=0; i<depth; i++) {
            for (int j=0; j<width; j++) {
                this.table[i][j]+= other.table[i][j];
            }
        }
    }


}