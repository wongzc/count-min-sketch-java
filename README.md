count-min-sketch
- probabilistic data structure
- use multiple hash function
- for each input increments counter in 2D array
- estimate the count of query item as the minimum value from all hashed count

`ε` (epsilon)
- error in estimate of frequencies

`δ` (delta)
- probability that error exceed epsilon

array
- `w` = e/ε
- `d` = ln(1/δ)


- to compile:
    - with mvn: `mvn compile`
    - without mvn:
        - manual download and add in gson
        - `javac -cp ".;lib/gson-2.10.1.jar" src/main/java/*.java`
            - `-cp`: classpath: where to find external class/libraries
            - and compile all .java in `src/main/java/`

- to run:
    - with mvn: `mvn exec:java -Dexec.mainClass=Main`
    - without mvn:
        - `java -cp ".;lib/gson-2.10.1.jar;src/main/java" Main`
            - `.;lib/gson-2.10.1.jar;src/main/java`: java look into current dir `.`, `lib/...` and `src/main/java`
            - `Main`: the class with `public static void main(String[] args)`