import java.util.*;

public class cheeseBlock {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int Q = sc.nextInt();

        int[][] emptyX = new int[N][N]; 
        int[][] emptyY = new int[N][N]; 
        int[][] emptyZ = new int[N][N]; 

        for (int i = 0; i < N; i++) {
            Arrays.fill(emptyX[i], N);
            Arrays.fill(emptyY[i], N);
            Arrays.fill(emptyZ[i], N);
        }

        HashSet<String> carved = new HashSet<>();

        int validConfigurations = 0;

        for (int i = 0; i < Q; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();

            String key = x + "," + y + "," + z;

            if (carved.contains(key)) {
                System.out.println(validConfigurations);
                continue;
            }

            carved.add(key);

            emptyX[y][z]--;
            if (emptyX[y][z] == 0) {
                validConfigurations++;
            }

            emptyY[x][z]--;
            if (emptyY[x][z] == 0) {
                validConfigurations++;
            }

            emptyZ[x][y]--;
            if (emptyZ[x][y] == 0) {
                validConfigurations++;
            }

            System.out.println(validConfigurations);
        }

        sc.close();
    }
}
