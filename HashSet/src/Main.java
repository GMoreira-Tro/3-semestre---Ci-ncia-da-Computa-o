import java.io.*;
import java.util.*;

public class Main {

    // Complete the missingNumbers function below.
    static Object[] missingNumbers(int[] arr, int[] brr) {
        Set<Integer> hash = new HashSet<Integer>();
        
        for (int i : arr) {
			System.out.print(i + " ");
		}
        
        System.out.println();
        
        for (int i : brr) {
			System.out.print(i + " ");
		}
        
        System.out.println();
        
        for (int i = 0, j = 0; j < brr.length; j++) {
            try {
                if (arr[i] != brr[j]) {
                    hash.add(brr[j]);
                }
                else
                    i++;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                hash.add(brr[j]);
            }
        }
        
        List<Integer> returnedList = new ArrayList<Integer>();
        
        for (int i : hash) {
        	returnedList.add(i);
		}
        returnedList.sort(null);
        
        return returnedList.toArray();

    }

    public static void main(String[] args) throws IOException {

        int m = (int)(Math.random()*15 + 1);

        int[] brr = new int[m];

        for (int i = 0; i < m; i++) {
            brr[i] = (int)(Math.random()*100);
        }
        
        List<Integer> list = new ArrayList<Integer>();
        for (Integer integer : brr) {
			list.add(integer);
		}
        
        int removeQuantity = (int)(Math.random()*list.size() + 1);
        for (int i = 0; i < removeQuantity; i++) {
        	list.remove((int)(Math.random()*list.size()));
        }
        
        int[] arr = new int[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}

        Object[] result = missingNumbers(arr, brr);

        for (int i = 0; i < result.length; i++) {
            System.out.print((String.valueOf(result[i])) + " ");
        }
    }
}
