import java.util.*;
import java.io.*;

public class RoundingComparison {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        
        for (int t = 0; t < T; t++) {
            long N = scanner.nextLong();
            System.out.println(countDiscrepancies(N));
        }
    }
    
    public static long countDiscrepancies(long N) {
        long discrepancies = 0;
        long powerOfTen = 10;
        
        while (powerOfTen <= N) {
            long nextPower = powerOfTen * 10;
            
            for (long x = powerOfTen; x < nextPower && x <= N; x++) {
                if (regularRound(x, nextPower) != chainRound(x, nextPower)) {
                    discrepancies++;
                }
            }
            
            powerOfTen = nextPower;
        }
        
        return discrepancies;
    }
    
    public static long regularRound(long x, long power) {
        long remainder = x % power;
        if (remainder >= power / 2) {
            return x - remainder + power;
        }
        return x - remainder;
    }
    
    public static long chainRound(long x, long power) {
        long current = x;
        long factor = 10;
        
        while (factor <= power) {
            current = regularRound(current, factor);
            factor *= 10;
        }
        
        return current;
    }

}