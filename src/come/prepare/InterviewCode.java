package come.prepare;

import java.sql.Array;
import java.util.*;

public class InterviewCode {

    // aabcdfgaghijklk
    // find longest substring without repetition
    public int findLongestSub(String str) {
        HashMap<Character, Integer> charMap = new HashMap();

        if(str.length() == 0) {
            return 0;
        }

        int start = 0;
        int max = 0;
        for (int i =0;i < str.length(); i++) {
            char ch = str.charAt(i);
            int chIndex = charMap.containsKey(ch) ? charMap.get(ch) : -1 ;
            if (chIndex >=start) {
                max = Math.max(i-start,max);
                start = chIndex + 1;
                charMap.put(ch, i);
            } else {
                charMap.put(ch, i);
            }
        }

        max = Math.max((str.length()-1) - start, max);

        return max;

    }




    // plan it well before you try
    // 333122245333

    // we can track via key value - count
    // lastToggled Index - remember - can get total fruits  between toggles

    // last index of fruit in map ....  ,

    public int maxFruitsCollected(int[] fruits) {
       int max =0 , windowStart = 0;

       HashMap<Integer, Integer> map = new HashMap<>();

       for(int i =0;i < fruits.length; i++) {
           map.put(fruits[i], i);
           if (map.size() > 2) {
               for(Integer key : map.keySet()) {
                   if(key != fruits[i] && key != fruits[i-1]) {
                       if (max < (i - windowStart)) {
                           max = i-windowStart;
                       }
                       windowStart = map.get(key) - map.get(fruits[i-1]);
                       map.remove(key);
                       break;
                   }
               }
           }
       }
       if (max < (fruits.length-windowStart)) {
           return (fruits.length - windowStart);
       }
       return max;
    }


    public String largestTimeFromDigits(int[] arr) {
                // latest time
                // 00 to 23 , 00 to 59
                // greater times 10s 2 , units

                // Get hour values
                // brute force 4 pass

                int[] time = new int[]{-1,-1,-1,-1};
                int leftout = 0;
                for (int i =0 ; i < 4 ; i ++) {
                    int tracker = -1;
                    for (int j =0 ; j < arr.length; j++) {
                        if (arr[j] == -1) continue;

                        if (i == 0) {
                            if (arr[j] > -1 && arr[j] < 3) {
                                if (time[i] < arr[j])  {
                                    time[i] = arr[j];
                                    if (j > -1) {
                                        arr[j] = -1;
                                    }
                                    continue;
                                }
                            }
                        }

                        if ( (time[0] ==2) && (i == 1) ) {
                            if ( time[i] < 4 && time[i] < arr[j]) {
                                time[i] = arr[j];
                                tracker = j;
                            }
                        } else if (i == 1 || i ==3) {

                            if (time[i] < arr[j]) {
                                time[i] = arr[j];
                                tracker = j;
                            }
                        }

                        if (i ==2) {
                            if ( (arr[j] < 6)  &&   (time[i] < arr[j]) )
                            {
                                time[i] = arr[j];
                                tracker = j;
                                continue;
                            }
                        }
                    }

                    if (tracker > -1) {
                        arr[tracker] = -1;
                    }

                }

                int breaker = 0;
                StringBuilder str = new StringBuilder();
                for (int jj : time) {
                    breaker = breaker + 1;
                    if (jj == -1) return "";
                    str.append(jj);
                    if (breaker == 2) {
                        str.append(":");
                    }
                }

                return str.toString();
            }



    // find max consequetive zeros and divide by 2
    public static int findMaxDistance(int[] seats) {
        int maxZeros = 0;
        int previousOne = 0;

        for (int i=0; i < seats.length; i++) {
            if (seats[i] != 0) {
                if ( (i - previousOne) > maxZeros) {
                    if (previousOne == 0)
                    {
                        maxZeros = i-previousOne;
                    } else {
                        maxZeros = (i-previousOne) / 2;
                    }
                }
                previousOne = i;
            }
        }

        if ( ( (seats.length-1) - previousOne) >= maxZeros) {
            maxZeros = ((seats.length-1) - previousOne);
        }

        return maxZeros;
    }

    public int[] gardenNoAdj(int n, int[][] paths) {
        //  [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
        // build adjacency matrix
        // do BFS and assign flowers such a way they dont have same

        List<List<Integer>>  adj = new ArrayList();

        HashMap<Integer, Integer> visited = new HashMap();

        for (int i =1 ; i <= paths.length; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for(int i = 1 ; i <= paths.length; i++) {
            adj.get(paths[i][0]).add(paths[i][1]);
            adj.get(paths[i][1]).add(paths[i][0]);
        }

        int gardens = 1;

        while(gardens < n) {

            if (visited.containsKey(gardens)) {
                n++;
                continue;
            }

            List<Integer> visitedGardens = new ArrayList<>();
            List<Integer> newGardens = new ArrayList<>();
            newGardens.add(gardens);

            for (Integer adjGardens : adj.get(gardens)) {
                if (visited.containsKey(adjGardens)) {
                    visitedGardens.add(adjGardens);
                } else {
                    newGardens.add(adjGardens);
                }
            }

            distributeFlowers(visited, visitedGardens, newGardens);

            gardens++;
        }
         return null ; //later
    }

    public void distributeFlowers(HashMap<Integer,Integer> visitedSet, List<Integer> visited, List<Integer> newList) {
        List<Integer> flowers = new ArrayList<Integer>();
        flowers.addAll(Arrays.asList(new Integer[]{1,2,3,4}));
        for (Integer already: visited) {
            flowers.remove(already);
        }
        for(Integer newGarden : newList) {
            visitedSet.put(newGarden, flowers.get(0));
        }
    }

    public void combinate(List<String> combinations, Character[] original,List<Character> combi,int nextPos) {

        if (combi.size() >= original.length-1) {
            combinations.remove(combi.toString());
            permute(combinations, combi.toArray(new Character[combi.size()]), 0); // adding permutations on the strings
            combinations.add(Arrays.toString(original));
            return;
        }

        if(combi.size() > 1 ) {
            combinations.remove(combi.toString());
            permute(combinations, combi.toArray(new Character[combi.size()]), 0); // adding permutations on the strings
        }

        for(int i= nextPos; i < original.length; i++) {
            List<Character> list = new ArrayList<>(combi);
            list.add(original[i]);
            combinations.add(list.toString());
            combinate(combinations, original, list, i+1);
        }
    }

    public void combinate(Set<String> combinations, Character[] original,List<Character> combi,int nextPos) {

        if (combi.size() >= original.length-1) {
            System.out.println("size end : " + combi);

            combinations.remove(combi.toString());
            permute(combinations, combi.toArray(new Character[combi.size()]), 0); // adding permutations on the strings
            //combinations.add(Arrays.toString(original));
            permute(combinations, original, 0); // adding permutations on the strings
            return;
        }

        if(combi.size() > 1 ) {
            System.out.println("size one : " + combi);

            permute(combinations, combi.toArray(new Character[combi.size()]), 0); // adding permutations on the strings
        }

        for(int i= nextPos; i < original.length; i++) {
            List<Character> list = new ArrayList<>(combi);
            list.add(original[i]);
            combinations.add(list.toString());
            combinate(combinations, original, list, i+1);
        }
    }

    public void permute(List<String> permutations,Character[] str, int start) {
        if (start >= str.length) {
            permutations.add(Arrays.toString(str));
            return;
        }

        for(int i =start ; i < str.length; i++) {
            Character[]  swapped = swap(str, start, i);
            permute(permutations, swapped, start+1);
        }
    }
    public void permute(Set<String> permutations,Character[] str, int start) {
        if (start >= str.length) {
            permutations.add(Arrays.toString(str));
            return;
        }

        for(int i =start ; i < str.length; i++) {
            Character[]  swapped = swap(str, start, i);
            permute(permutations, swapped, start+1);
        }
    }

    public Character[] swap(Character[] str,int pos1,int pos2) {
        Character[] swapped = str.clone();
        Character temp = swapped[pos1];
        swapped[pos1] = swapped[pos2];
        swapped[pos2] = temp;
        return swapped;
    }



        public int numSubmat(int[][] mat) {
            int rectangles = 0;
            for (int r=0; r < mat.length; r++) {
                for (int cc=r ; cc < mat[r].length; cc++) {
                    for (int c = cc; c < mat[r].length; c++) {
                        if (mat[r][c] == 0) {
                            continue;
                        }
                        if ( c-1 > -1 && (mat[r][c-1] == 1)) {
                            rectangles++;
                        }
                        if (r ==c) {
                            rectangles++;
                        }
                    }
                }
            }

            int c = 0 ;
            while (c < mat[0].length) {
                for (int rr = c; rr < mat.length; rr++) {
                    for (int r = rr; r < mat.length; r++) {
                        if (mat[r][c] == 0) {
                            continue;
                        }
                        if (r-1 > -1 && (mat[r-1][c] == 1)) {
                            rectangles++;
                        }
                    }
                }
                c++;
            }
            return rectangles;
        }


    public static void main(String[] str) throws InterruptedException {

          InterviewCode s = new InterviewCode();

          System.out.println("sdfsdf");
        //  s.findLongestSub("aabcdaeffghh");

        // assert 1 !=1 ;
         s.findLongestSub("aabcdaeffghh");
         s.findLongestSub("aaaaa");
         s.findLongestSub("abbbaefghijklmb");


/*

        int out =  s.numSubmat(new int[][]{
                  {1,0,1}, {1,1,0}, {1,1,0}
          });

         System.out.println(out);*/

/*
          StringBuilder str1 = new StringBuilder("()");
          System.out.println(str1.substring(1,str1.length()-1));
          int[] s = new int[1];
          int ss = str.length;


          String ss = "before+asdf";
          System.out.println(ss.indexOf('*'));
          ss = ss.substring(0, ss.indexOf('*'));

          System.out.println(ss);*/


/*

          Set<String> combinations = new HashSet<String>();
          s.combinate(aations, new Character[]{'A','A','B'}, new ArrayList<Character>(), 0 );
        for(String combi : combinations) {
            System.out.println(combi);
        }
        System.out.println(combinations.size());
*/

        //List<String> combinations = new ArrayList<>();
        //s.combinate(combinations, new Character[]{'A','B'}, new ArrayList<Character>(), 0 );

       /* Set<String> permutations = new HashSet<String>();
        s.permute(permutations, new Character[]{'A','B'}, 0);
          for(String combi : permutations) {
              System.out.println(combi);
          }
          System.out.println(permutations.size());
       */   //System.out.println(permutations);


/*
        HashSet s = new HashSet();
        s.addAll(Arrays.asList(new int[]{1,2,3,4}));



          Test s = new Test();

          System.out.println(s.findMaxDistance(new int[]{0,0,0,1,0,0,0,0,0,1,0}));
          System.out.println(s.findMaxDistance(new int[]{0,0,0,1,0,0,0,0,0,1,0,0,0,0,0}));
        System.out.println(s.findMaxDistance(new int[]{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0}));
        System.out.println(s.findMaxDistance(new int[]{0,0,0,1,0,0,0,0,0,1,0,0,0,0,0}));
          System.out.println(s.findMaxDistance(new int[]{0,1}));

          Queue s = new ArrayDeque()

                  List s = new ArrayList();
          s.remove()
*/


        //



        //System.out.println(s.findNumbers(9999));
        //System.out.println(s.findNumbersN(9999));



      /*  int[][] isConnected = new int[][] {{1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},{0,1,0,1,0,0,0,0,0,0,0,0,0,1,0},{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},{0,1,0,1,0,0,0,1,0,0,0,1,0,0,0},{0,0,0,0,1,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},{0,0,0,1,0,0,0,1,1,0,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},{0,0,0,0,1,0,0,0,0,0,0,0,1,0,0},{0,1,0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};

        DisJointSet uf = new DisJointSet(isConnected.length);


        for (int i =0; i < isConnected.length; i++) {
            for (int j=0; j < isConnected[i].length; j++) {

                if (i == j) continue;

                if (isConnected[i][j] == 1) { System.out.println("i : " + i + ", j :" + j);
                uf.union(i,j);
                }

            }
        }




        System.out.println(Arrays.toString(uf.root));

        HashSet<Integer> set = new HashSet<Integer>();
        for(int i =0;i < uf.root.length; i++) {
            set.add(uf.find(i));
        }

        System.out.println(Arrays.toString(uf.root));


*/

        //System.out.println(uf.connected(1, 5)); // true)

    /*    long s = (long) Math.pow(10,2);
        System.out.println(s);
        LinkedList<Integer> list = new LinkedList<Integer>();
        //System.out.println(summation(10));
      }

      public static int summation(int n) {
        System.out.println(n);
        if (n==0) {
            return n;
        }
        return (summation(n-1) + n);
       }
      public void app() throws InterruptedException {


          Application rn = new Application();
          rn.start();
          System.out.println("main thread started ... about to sleep for 10 secs  ");
          Thread.sleep(10000);
          System.out.println("main thread , waited 10 secons ... ");
          rn.stopApp();

      } */


    }


   public int findNumbersN(int n) {
        Map<Character,Integer> numTOCount = Map.of('2',1,'5',2,'6',3,'9',4);
        String numVal = String.valueOf(n);
        char[] numArray =  numVal.toCharArray();
        int occurence = 0;
        /// 1000 = 4 * 4 * 4
        int digit=1;
        System.out.println(numArray.length);
        for(int i = numArray.length-1; i >= 0; i--) {
           // System.out.println(numArray[i]);
            //System.out.println(numTOCount.containsKey(numArray[i]));

            if (!numTOCount.containsKey(numArray[i])) {
                return 0;
            }
            occurence = (numTOCount.get(numArray[i]) * digit) + occurence;
            digit = digit * 4;
        }
        return occurence;
   }

    public int findNumbers(int n) {
        Map<Character,Integer> numTOCount = Map.of('2',1,'5',2,'6',3,'9',4);
        String numVal = String.valueOf(n);
        char[] numArray =  numVal.toCharArray();
        int occurence = 0;
        /// 1000 = 4 * 4 * 4
        //int digit=1;
        // System.out.println(numArray.length);
        for(int j = 0; j <=n ; j ++) {
           String val = String.valueOf(j);
            for (int i = val.length() - 1; i >= 0; i--) {
                if (!numTOCount.containsKey(val.charAt(i))) {
                    break;
                }
                if (i ==0) {
                    occurence++;
                }
            }
        }
        return occurence;
    }


    public void changeOrder(int[][] connections, int n) {

        List<List<Integer>> adjacency = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<Integer>());
        }

        for(int i= 0; i< connections.length; i++) {
            adjacency.get(connections[i][0]).add(connections[i][1]);
            adjacency.get(connections[i][1]).add(-1 * connections[i][0]); // negative indicates the opposite connection
        }

        Queue<Integer> que = new ArrayDeque<Integer>();
        que.add(n);
        int count = 0;
        while(!que.isEmpty()) {
            Integer nodeNum = que.poll();

            List<Integer> nodeList = adjacency.get(nodeNum);

            for(Integer i : nodeList) {
                if ( i > 0) {
                    adjacency.get(nodeNum).remove(i);
                    adjacency.get(i).add(nodeNum);
                    que.add(i);
                    count++;
                } else {
                    que.add(i*-1);
                }
            }

        }

    }
}





class DisJointSet {

    public int rank[] ;
    public int root[] ;
    public DisJointSet(int n) {
        root = new int[n];
        rank = new int[n];
        for (int i  = 0;  i <n; i++ ) {
            rank[i] = 1;
            root[i] = i;
        }
    }

    public int find(int x) {
        if (x == root[x]) {
            return x;
        }
        return root[x] = find(root[x]);
    }

    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                root[rooty] = root[rootx];
            } else if (rank[rootx] < rank[rooty]) {
                root[rootx] = root[rooty];
            } else {
                root[rootx] = root[rooty];
                rank[rooty]++;
            }
        }
    }

    public boolean isconnected(int x, int y) {
        return find(x) == find(y);
    }

}


class UnionFind {
    // union find with path compresession and ranking.
   public int[] root;
   public int[] rank;

    UnionFind(int n) {
        root = new int[n] ;
        rank = new int[n];
        for (int i =0 ; i < n; i++) {
            root[i] = i;
            rank[i] = 1; // intial will be seperate
        }
    }

    public int find(int x) {
        if ( x == root[x]) {
            return x;
        }

        return root[x] = find(root[x]);
    }

    public void union(int x , int y) {
        int rootx = find(x);
        int rooty = find(y);

        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                root[rooty] = root[rootx];
            } else if (rank[rootx] < rank[y]) {
                root[rootx] = root[rooty];
            } else {
                root[rootx] = root[rooty];
                rank[rooty]++;
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}

class Application extends Thread {

    private boolean stop;

    public void stopApp(){
        this.stop = true;
    }

    public void run() {
        while(!stop) {
            try {
                System.out.println("App Running ...");
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}





