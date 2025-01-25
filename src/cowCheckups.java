import java.io.*;
import java.util.*;

public class cowCheckups {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[] a = new int[N];
        int[] b = new int[N];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }
        
        long totalSum = 0;
        
        for (int l = 0; l < N; l++) {
            for (int r = l; r < N; r++) {
                int matches = 0;
                for (int i = 0; i < N; i++) {
                    if (i < l || i > r) {
                        if (a[i] == b[i]) matches++;
                    } else {
                        if (a[l + r - i] == b[i]) matches++;
                    }
                }
                totalSum += matches;
            }
        }
        
        System.out.println(totalSum);
        br.close();
    }
}