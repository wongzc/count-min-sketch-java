package io.github.wongzc.cms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

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

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void saveToFile(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        }
    }

    public static <T> CMSketch<T> loadFromFile(String filename, Class<T> keyType) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            // NOTE: Type safety is best-effort here — for non-string keys, more work is needed.
            return gson.fromJson(reader, CMSketch.class);
        }
    }

}