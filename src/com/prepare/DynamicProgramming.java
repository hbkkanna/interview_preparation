package com.prepare;

import java.util.*;

public class DynamicProgramming {


    private static final HashMap<Integer, Integer> memo = new HashMap<Integer, Integer>();

    static boolean solution(double[] prices, String[] notes, double x) {
        // find the x and check it with x
        double sensitivity = 0;
        for (int i = 0; i < prices.length; i++) {
            // find the difference
            String note = notes[i];
            String[] tokens = note.split(" ");
            double percentage = 0;
            double realPrice = 0;

            if (note.indexOf("higher") > -1) {
                percentage = Double.valueOf(tokens[0].substring(0, tokens[0].length() - 1));
                realPrice = (prices[i] * 100) / (100 + percentage);
                sensitivity += prices[i] - realPrice;
            }

            if (note.indexOf("lower") > -1) {
                percentage = Double.valueOf(tokens[0].substring(0, tokens[0].length() - 1));
                realPrice = (prices[i] * 100) / (100 - percentage);
                sensitivity += prices[i] - realPrice;
            }
        }


        System.out.println(sensitivity);
        System.out.println(x);
        return (sensitivity == x);

    }

    public static void main(String[] str) {

    }

    public static int longestCommonSubsequence(String text1, String text2) {

        // Make a grid of 0's with text2.length() + 1 columns
        // and text1.length() + 1 rows.
        int[][] dpGrid = new int[text1.length() + 1][text2.length() + 1];

        // Iterate up each column, starting from the last one.
        for (int col = text2.length() - 1; col >= 0; col--) {
            for (int row = text1.length() - 1; row >= 0; row--) {
                // If the corresponding characters for this cell are the same...
                if (text1.charAt(row) == text2.charAt(col)) {
                    dpGrid[row][col] = 1 + dpGrid[row + 1][col + 1];
                } else {
                    dpGrid[row][col] = Math.max(dpGrid[row + 1][col], dpGrid[row][col + 1]);
                }
            }
        }

        // The original problem's answer is in dp_grid[0][0]. Return it.
        return dpGrid[0][0];
    }

    public static int minimumCostClimbing(int[] stairs) {
            /*
                Top Down approach

                Find the relation ;
                can reach to the top stair from Nth or N-1th stair .
                mincost = Min( cost(Nth) , cost(N-1))

                mincost (i) = min( cost(i-1) + mincost[i-1] , cost(i-2) + mincost(i-2) )

                can land in 1st or 2nd stair

             */


        return mincost(stairs.length, stairs);


    }

    public static int mincost(int i, int[] stairs) {

        if (i < 2) {
            return 0;
        }

        if (memo.containsKey(i)) {
            return memo.get(i);
        }

        int downOne = stairs[i - 1] + mincost(i - 1, stairs);

        int downTwo = stairs[i - 2] + mincost(i - 2, stairs);

        memo.put(i, Math.min(downTwo, downOne));

        return memo.get(i);

    }

    public static int minCostIterative(int[] stairs) {

        int downOne = 0;
        int downTwo = 0;

        for (int i = 2; i < stairs.length + 1; i++) {
            int temp = downOne;
            downOne = Math.min(downOne + stairs[i - 1], downTwo + stairs[i - 2]); // since the stairs iterated till the
            // end it downONe will be the end result
            downTwo = downOne;
        }

        return downOne;


    }

    public static int tribonacci(int n) {

        if (n == 0) {
            return 0;
        }

        if (n < 3) {
            return 1;
        }
        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);

    }

    public static int tribonacciLoop(int n) {
        int trib1, trib2, trib3;
        trib1 = 0;
        trib2 = trib3 = 1;
        if (n < 1) {
            return trib1;
        }
        if (n < 3) {
            return trib3;
        }
        int current = 2;
        while (current < n) {
            int temp = trib3;
            trib3 = trib1 + trib2 + trib3;
            trib1 = trib2;
            trib2 = temp;
            current++;
        }
        return trib3;
    }


    public static int[] findJumps(int[] arr) {

        Deque<Integer> st = new ArrayDeque();

        int[] result = new int[arr.length];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                result[i - 1] = 1;
                if (!st.isEmpty()) {
                    while (!st.isEmpty() && arr[i] > arr[st.peek()]) {
                        int pos = st.pop();
                        result[pos] = i - pos;
                    }
                }

            } else {
                st.push(i - 1);
            }
        }
        while (!st.isEmpty()) {
            result[st.pop()] = 0;
        }

        return result;
    }


    public static int rottenOranges(int[][] arr) {
        // get the rotten and fresh details

        if (arr.length == 0) {
            return -1;
        }

        int freshCount = 0;
        Deque<List<List<Integer>>> stack = new ArrayDeque();

        List<List<Integer>> coordinates = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 2) {
                    addToList(coordinates, i, j);
                }
                if (arr[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        if (!coordinates.isEmpty()) {
            stack.push(coordinates);
        } else {
            return -1;
        }

        int minutes = 0;
        while (!stack.isEmpty()) {

            List<List<Integer>> list = stack.pop();
            List<List<Integer>> nextList = new ArrayList<>();
            for (List<Integer> cooridnates : list) {

                int x = cooridnates.get(0);
                int y = cooridnates.get(1);
                if ((x + 1 < arr.length) && (arr[x + 1][y] == 1)) {
                    arr[x + 1][y] = 2;
                    addToList(nextList, x + 1, y);
                    freshCount--;
                }

                if (y + 1 < arr.length) {
                    if (arr[x][y + 1] == 1) {
                        arr[x][y + 1] = 2;
                        addToList(nextList, x, y + 1);
                        freshCount--;

                    }
                }

                if (x - 1 > -1) {
                    if (arr[x - 1][y] == 1) {
                        arr[x - 1][y] = 2;
                        addToList(nextList, x - 1, y);
                        freshCount--;

                    }
                }

                if (y - 1 > -1) {
                    if (arr[x][y - 1] == 1) {
                        arr[x][y - 1] = 2;
                        addToList(nextList, x, y - 1);
                        freshCount--;

                    }
                }
            }
            if (!nextList.isEmpty()) {
                stack.push(nextList);
                minutes++;
            }

        }

        if (freshCount != 0) {
            return -1;
        }

        return minutes;


    }

    public static void addToList(List<List<Integer>> list, int x, int y) {
        List<Integer> coordinates = new ArrayList<Integer>();
        coordinates.add(x);
        coordinates.add(y);
        list.add(coordinates);
    }


}

