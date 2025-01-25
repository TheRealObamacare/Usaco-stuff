import java.io.*;
import java.util.*;

public class RestoreTable {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[][] table = new int[N][N];
        int[] rowMins = new int[N];
        Arrays.fill(rowMins, Integer.MAX_VALUE);
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                table[i][j] = Integer.parseInt(st.nextToken());
                rowMins[i] = Math.min(rowMins[i], table[i][j]);
            }
        }
        
        int[][] sortedRows = new int[N][N];
        for (int i = 0; i < N; i++) {
            sortedRows[i] = table[i].clone();
            Arrays.sort(sortedRows[i]);
        }
        
        int[][] bestResult = null;
        for (int firstRow = 0; firstRow < N; firstRow++) {
            int[][] current = new int[N][N];
            boolean[] usedRows = new boolean[N];
            current[0] = table[firstRow].clone();
            usedRows[firstRow] = true;
            
            boolean valid = true;
            for (int i = 1; i < N && valid; i++) {
                valid = false;
                int[] prevSorted = sortedRows[firstRow].clone();
                for (int k = 1; k < i; k++) {
                    for (int j = 0; j < N; j++) {
                        prevSorted[j]++;
                    }
                }
                
                for (int j = 0; j < N; j++) {
                    if (usedRows[j]) continue;
                    
                    boolean isValid = true;
                    for (int k = 0; k < N; k++) {
                        if (sortedRows[j][k] != prevSorted[k] + 1) {
                            isValid = false;
                            break;
                        }
                    }
                    
                    if (isValid) {
                        current[i] = table[j].clone();
                        usedRows[j] = true;
                        valid = true;
                        break;
                    }
                }
            }
            
            if (valid) {
                int[][] sorted = sortColumns(current, N);
                if (bestResult == null || isLexicographicallySmaller(sorted, bestResult, N)) {
                    bestResult = sorted;
                }
            }
        }
        
        if (bestResult != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    sb.append(bestResult[i][j]);
                    if (j < N-1) sb.append(" ");
                }
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }
    
    private static int[][] sortColumns(int[][] table, int N) {
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++) {
            result[i] = table[i].clone();
        }
        
        Integer[] colOrder = new Integer[N];
        for (int i = 0; i < N; i++) colOrder[i] = i;
        
        Arrays.sort(colOrder, (a, b) -> {
            for (int i = 0; i < N; i++) {
                if (result[i][a] != result[i][b]) {
                    return Integer.compare(result[i][a], result[i][b]);
                }
            }
            return 0;
        });
        
        int[][] sorted = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sorted[i][j] = result[i][colOrder[j]];
            }
        }
        return sorted;
    }
    
    private static boolean isLexicographicallySmaller(int[][] a, int[][] b, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] != b[i][j]) {
                    return a[i][j] < b[i][j];
                }
            }
        }
        return false;
    }
}