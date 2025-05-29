# Count-Min Sketch in Java
[![Java Version](https://img.shields.io/badge/Java-23%2B-blue)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

This project implements a [Count-Min Sketch](https://en.wikipedia.org/wiki/Count%E2%80%93min_sketch), a probabilistic data structure that provides approximate frequency counts for streamed items using sublinear space.

## Installation

Clone this repository and either use Maven or compile manually.

## What is a Count-Min Sketch?

A Count-Min Sketch is a space-efficient data structure that estimates the frequency of elements in a data stream. It trades off accuracy for memory and computation efficiency.

It works by using multiple hash functions to increment counters in a 2D array upon each insertion, and estimates counts by querying the minimum value from all relevant counters.

## Advantages

- Sublinear space complexity
- Fast insertion and query time (O(d) where d = number of hash functions)
- Mergable sketches
- Easy to serialize and deserialize

## Theory

### Initialization Parameters

- `ε`: error bound — maximum overestimation of frequency
- `δ`: confidence bound — probability that an error exceeds ε

### Width and Depth

$$
\text{width} = \lceil \frac{e}{\varepsilon} \rceil,\quad \text{depth} = \lceil \ln \left( \frac{1}{\delta} \right) \rceil
$$

- `width`: number of columns in the sketch table
- `depth`: number of hash functions / rows

### Update

When inserting key `x`, compute:
- `hash_i(x)` for each row `i`
- Increment counter at `table[i][hash_i(x)]`

### Estimate

To estimate frequency of key `x`, compute:
- `hash_i(x)` for each row `i`
- Return: `min(table[i][hash_i(x)])` across all rows

## Example Usage

```java
CMSketch<String> sketch = new CMSketch<>(0.01, 0.01);
sketch.add("apple");
sketch.add("apple");
sketch.add("banana");

System.out.println(sketch.estimate("apple"));   // ~2
System.out.println(sketch.estimate("banana"));  // ~1
System.out.println(sketch.estimate("orange"));  // ~0
```

### Serialization
```Java
sketch.saveToFile("cms.json");                   // Save to file
CMSketch<String> loaded = CMSketch.loadFromFile("cms.json", String.class);
System.out.println(loaded.estimate("apple"));    /
```

## Compile & Run

### With Maven

#### Compile:
```bash
mvn compile
```

#### Run
```bash
mvn exec:java -Dexec.mainClass=Main
```
- `exec:java`: use `exec-maven-plugin` to run Java code
- `-Dexec.mainClass=Main`: set main class to run
- need to make sure Main is:
    1. in `src/main/java`: 
        - standard & recommended
        - if not in, need to move to there
    2. in default package
        - else need to specify package, eg: `-Dexec.mainClass=com.example.cms.Main`

#### Test
```bash
mvn test
```

### Without Maven

#### Manual Setup
1. Download Gson and place it in the lib/ folder.
2. Compile:
```bash
javac -cp ".;lib/gson-2.10.1.jar" src/main/java/*.java
```
- `-cp`: classpath — tells Java where to find external classes/libraries.
- This command compiles all `.java` files in `src/main/java/`.

#### Run
``` bash
java -cp ".;lib/gson-2.10.1.jar;src/main/java" Main
```
- `.;lib/gson-2.10.1.jar;src/main/java`: Java looks in current dir `.`, the `lib/` folder, and your Java source directory.
- `Main`: class containing `public static void main(String[] args)`


## About Maven

### Maven standard dir layout
1. Production code: `src/main/java`
3. Test code:   	`src/test/java`
4. Resources:   	`src/main/resources`
5. Test resources:	`src/test/resources`

- else, need to specify in pom.xml ( but not recommended)
```xml
<build>
  <sourceDirectory>customsrc/</sourceDirectory>
  <testSourceDirectory>customtest/</testSourceDirectory>
</build>
```