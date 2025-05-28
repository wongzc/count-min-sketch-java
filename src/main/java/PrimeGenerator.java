// simple prime number generator

public class PrimeGenerator {
    public static int[] generatePrimes(int count) {
        int[] primes = new int[count];
        int prime=31;
        for (int i=0;i<count;i++) {
            prime=nextPrime(prime);
            primes[i]=prime;
            prime++; // to move to next
        }
        return primes;
    }

    private static int nextPrime(int start) {
        while (!isPrime(start)) {
            start++;
        }

        return start;
    }

    private static boolean isPrime(int n) {
        for (int i=2;i*i<=n;i++) {
            if(n%i==0) return false;
        }
        return true;
    }
}