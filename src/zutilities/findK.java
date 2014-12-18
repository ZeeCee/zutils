package zutilities;

import java.util.Arrays;

/**
 * @author Z
 * Question: 
 * Given two sorted arrays A, B of size m and n respectively. Find the k-th smallest element in the union of A and B. You can assume that there are no duplicate elements.
 */

public class findK {

    public int find(int k, int[] a, int[] b) {
	int m = a.length, n = b.length;
	int[] f = new int[k];

	int i = 0, j = 0;
	for (int c = 0; c < k; c++) {
	    if (c >= m) {
		f[k - 1] = b[k - m - 1];
		break;
	    } else if (c >= m) {
		f[k - 1] = b[k - n - 1];
		break;
	    } else if (a[i] < b[j]) {
		f[c] = a[i++];

	    } else if (a[i] >= b[j]) {
		f[c] = b[j++];

	    }
	}
	System.out.format("The %dth smallest number is %d", k - 1, f[k - 1]);
	return f[k - 1];

    }

    public static void main(String[] args) {
	int[] a = new int[8];
	int[] b = new int[10];
	int k = 6;

	findK fin = new findK();

	for (int i = 0; i < 8; i++) {
	    a[i] = i + i;
	}

	for (int i = 0; i < 10; i++) {
	    b[i] = 5 + i;
	}

	System.out.println(Arrays.toString(a) + Arrays.toString(b));

	fin.find(k, a, b);

	assert fin.find(k = 1, a, b) == 0;
	assert fin.find(k = 5, a, b) == 6;
	assert fin.find(k = 10, a, b) == 9;
	assert fin.find(k = 18, a, b) == 14;

    }

}
