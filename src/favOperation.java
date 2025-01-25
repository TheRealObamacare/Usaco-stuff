import java.io.*;
import java.util.*;


public class favOperation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine());
        
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            
            st = new StringTokenizer(br.readLine());
            int[] rems = new int[N];
            
            if (M > 100000) {
                TreeSet<Integer> uniqueRems = new TreeSet<>();
                for (int i = 0; i < N; i++) {
                    long num = Long.parseLong(st.nextToken());
                    rems[i] = (int)((num % M + M) % M);
                    uniqueRems.add(rems[i]);
                }
                
                long minOperations = Long.MAX_VALUE;
                for (int base : uniqueRems) {
                    for (int offset = -1; offset <= 1; offset++) {
                        int target = ((base + offset) % M + M) % M;
                        long cost = 0;
                        for (int rem : rems) {
                            cost += Math.min((rem - target + M) % M, (target - rem + M) % M);
                            if (cost >= minOperations) break;
                        }
                        minOperations = Math.min(minOperations, cost);
                    }
                }
                out.println(minOperations);
                continue;
            }
            
            int[] freq = new int[M];
            int minRem = M, maxRem = 0;
            
            for (int i = 0; i < N; i++) {
                long num = Long.parseLong(st.nextToken());
                rems[i] = (int)((num % M + M) % M);
                freq[rems[i]]++;
                minRem = Math.min(minRem, rems[i]);
                maxRem = Math.max(maxRem, rems[i]);
            }
            
            long minOperations = Long.MAX_VALUE;
            int start = Math.max(0, minRem - 1);
            int end = Math.min(M - 1, maxRem + 1);
            
            for (int target = start; target <= end; target++) {
                long cost = 0;
                for (int i = 0; i < M; i++) {
                    if (freq[i] == 0) continue;
                    int clockwise = (i - target + M) % M;
                    int counterClockwise = (target - i + M) % M;
                    cost += (long)Math.min(clockwise, counterClockwise) * freq[i];
                    if (cost >= minOperations) break;
                }
                minOperations = Math.min(minOperations, cost);
            }
            
            if (minRem > 1) {
                for (int target : new int[]{0, 1}) {
                    long cost = 0;
                    for (int i = 0; i < M; i++) {
                        if (freq[i] == 0) continue;
                        int clockwise = (i - target + M) % M;
                        int counterClockwise = (target - i + M) % M;
                        cost += (long)Math.min(clockwise, counterClockwise) * freq[i];
                        if (cost >= minOperations) break;
                    }
                    minOperations = Math.min(minOperations, cost);
                }
            }
            if (maxRem < M - 2) {
                for (int target : new int[]{M-2, M-1}) {
                    long cost = 0;
                    for (int i = 0; i < M; i++) {
                        if (freq[i] == 0) continue;
                        int clockwise = (i - target + M) % M;
                        int counterClockwise = (target - i + M) % M;
                        cost += (long)Math.min(clockwise, counterClockwise) * freq[i];
                        if (cost >= minOperations) break;
                    }
                    minOperations = Math.min(minOperations, cost);
                }
            }
            
            out.println(minOperations);
        }
        out.flush();
        br.close();
    }
}