import java.util.*;

public class MooDetector {
    private int N;
    private int F;
    private String text;
    private Set<String> possibleMoos;
    private int[][] prefixCount;

    public MooDetector(int N, int F, String text) {
        this.N = N;
        this.F = F;
        this.text = text;
        this.possibleMoos = new TreeSet<>();
        this.prefixCount = new int[26][N + 1];  
        preprocessCounts();
    }

    private void preprocessCounts() {
        for (int i = 0; i < N; i++) {
            char c = text.charAt(i);
            for (int j = 0; j < 26; j++) {
                prefixCount[j][i + 1] = prefixCount[j][i];
            }
            prefixCount[c - 'a'][i + 1]++;
        }
    }

    private List<Integer> findPatternPositions(char c1, char c2) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < N - 2; i++) {
            if (text.charAt(i) == c1 && 
                text.charAt(i + 1) == c2 && 
                text.charAt(i + 2) == c2) {
                positions.add(i);
            }
        }
        return positions;
    }

    // Check if changing one character can make pattern appear F times
    private boolean canMakePattern(char c1, char c2) {
        // First check if pattern already appears F times
        List<Integer> currentPositions = findPatternPositions(c1, c2);
        if (currentPositions.size() >= F) 
            return true;
        char[] chars = text.toCharArray();
        boolean[] usedInPattern = new boolean[N];

        for (int pos : currentPositions) {
            usedInPattern[pos] = true;
            usedInPattern[pos + 1] = true;
            usedInPattern[pos + 2] = true;
        }

        for (int i = 0; i < N; i++) {
            if (usedInPattern[i]) continue;

            char original = chars[i];
            if (i <= N - 3) {  
                if (chars[i] != c1 && 
                    chars[i + 1] == c2 && 
                    chars[i + 2] == c2) {
                    if (currentPositions.size() + 1 >= F) return true;
                }
            }
            if (i >= 1 && i <= N - 2) { 
                if (chars[i - 1] == c1 && 
                    chars[i] != c2 && 
                    chars[i + 1] == c2) {
                    if (currentPositions.size() + 1 >= F) return true;
                }
            }
            if (i >= 2) {
                if (chars[i - 2] == c1 && 
                    chars[i - 1] == c2 && 
                    chars[i] != c2) {
                    if (currentPositions.size() + 1 >= F) return true;
                }
            }
        }
        return false;
    }

    public List<String> findMoos() {
        for (char c1 = 'a'; c1 <= 'z'; c1++) {
            for (char c2 = 'a'; c2 <= 'z'; c2++) {
                if (c1 == c2) continue;
                
                if (canMakePattern(c1, c2)) {
                    possibleMoos.add("" + c1 + c2 + c2);
                }
            }
        }
        return new ArrayList<>(possibleMoos);
    }

    public void printResults() {
        List<String> results = findMoos();
        System.out.println(results.size());
        for (String moo : results) {
            System.out.println(moo);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int F = sc.nextInt();
        sc.nextLine();  
        String text = sc.nextLine();

        MooDetector detector = new MooDetector(N, F, text);
        detector.printResults();
        
        sc.close();
    }
}