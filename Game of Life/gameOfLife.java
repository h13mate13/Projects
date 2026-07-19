
import java.util.*;

public class gameOfLife {
    static int colms = 45;
    static int rows = 135;
    static int[][] grid;
    static Random rand = new Random();

    static final String CLEAR = "\u001B[H\u001B[2J"; // cursor home + clear screen

    public static void main(String[] args) throws InterruptedException {

        //creates an empty grid
        grid = grid(colms, rows);

        //goes through every part of the 2D grid and fills it up with 0s or 1s
         for (int i = 0; i < colms; i++) {
             for (int j = 0; j < rows; j++) {
                 grid[i][j] = rand.nextInt(2);
             }
         }


        System.out.print("\u001B[2J");
        System.out.flush();

        while(true) {
             printGrid(grid);             //draws
             grid = nextGeneration(grid); //updates

             Thread.sleep(200);
         }
    }

    public static int[][] grid(int colms, int rows){

        // creates empty 2-D array
        int[][] grid = new int[colms][];

        for (int i = 0; i < grid.length; i++) {
            grid[i] = new int[rows];
        }

        return grid;
    }

    public static void printGrid(int[][] grid){


        StringBuilder sb = new StringBuilder();
        sb.append("\u001B[H");

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                sb.append(grid[i][j] == 1 ? "■ " : "  ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
        System.out.flush();
    }



    public static int[][] nextGeneration(int[][] grid){
        int [][] next = new int[grid.length][grid[0].length];

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                int neighbours = countNeighbors(grid, i, j);

                if(grid[i][j] == 1){
                    next[i][j] = (neighbours == 2 || neighbours == 3)?1:0;
                }
                else{
                    next[i][j] = (neighbours == 3) ? 1 : 0;
                }
            }
        }
        return next;
    }

    public static int countNeighbors(int[][] grid, int i, int j){
        int count = 0;

        for(int dx = -1; dx <= 1; dx++){
            for(int dy = -1; dy <= 1; dy++){
                if(dx == 0 && dy == 0)continue;

                int nx = i + dx;
                int ny = j + dy;

                if(nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length){
                    count += grid[nx][ny];
                }
            }
        }
        return count;
    }



}

