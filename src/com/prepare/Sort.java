package com.prepare;

import java.util.Arrays;

/**
 * Bubble and quick sort
 */
public class Sort {

    // search is on sorted array
    public static int binarySearch(int[] arr, int x) {
        int l =0, r = arr.length-1 , m =0;

        while (l<=r) {
            m = (l + r)/2 ;

            if (arr[m] == x) {
                return x;
            }

            if (x > arr[m]) {
                l = m+1;
            } else {
                r = m-1;
            }

        }
        return -1;
    }


    public static void main(String[] str) {
        Sort sort = new Sort();
        int[] f  = {4,6,1,2,99,23, 5,33,23,87,32,12};
        sort.quickSort(f,0,f.length-1);


        System.out.println( binarySearch(f, 22));
    }

    /**
     * Recursively sort the values by partition stategy
     * 1. select a pivot
     * 2. left of pivot are smaller , right of pivot are greater .
     * 3. repeat (1 to 2) for divided chunks until there is no chunks at all
     *
     * @param values
     */
    private void quickSort(int[] values,int low,int high) {
        if (low < high) {
            int pivot = divideArrayWithPivot(values, low, high);
            quickSort(values, low, pivot-1);
            quickSort(values, pivot+1, high);
        }
    }


    /**
     *
     *
     * @param values
     * @param low
     * @param high
     * @return
     */
    private int divideArrayWithPivot(int[] values,int low,int high) {
        int pivotIndex = high;
        int pointer = low-1; // as it will be incremented in the loop
        for (int j=low; j < high; j++ ) {
            //System.out.println(j);
            if (values[j] < values[pivotIndex] ) {
               // pointer++; // to move on to next position on swap.
                if (j == ++pointer) {
                    continue;
                }

                    int temp = values[pointer];
                    values[pointer] = values[j];
                    values[j] = temp;

            }
        }
        pointer++;
        int temp = values[pointer];
        values[pointer] = values[pivotIndex];
        values[pivotIndex] = temp;
        //System.out.println(Arrays.toString(values));
        //System.out.println(pointer);
        return pointer;   // pivot pos
    }

    private void bubbleSort(int[] values) {
        boolean sorted = true;
        for (int i =0; i < values.length; i++) {
            for (int j = i+1; j < values.length;j++) {
                 System.out.println(i + "," + j);
                if (values[j] < values[i]) {
                    int temp = values[j];
                    values[j] = values[i];
                    values[i] = temp;
                    sorted = false;
                    System.out.println(Arrays.toString(values));
                }
            }
            if (sorted) {
                System.out.println("Sorted already");
                break;
            }
        }
        System.out.println(Arrays.toString(values));
    }
}
