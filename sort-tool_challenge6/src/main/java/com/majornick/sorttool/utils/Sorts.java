package com.majornick.sorttool.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Sorts {
    public static <E extends Comparable<? super E>> void mergeSort(List<E> list) {
        divideList(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<? super E>> void divideList(List<E> list, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            divideList(list, l, mid);
            divideList(list, mid + 1, r);
            mergeDividedList(list, l, r);
        }
    }

    private static <E extends Comparable<? super E>> void mergeDividedList(List<E> list, int l, int r) {
        int mid = (l + r) / 2;
        int s1 = mid - l + 1;
        int s2 = r - mid;

        List<E> tmpList1 = new ArrayList<>(s1);
        List<E> tmpList2 = new ArrayList<>(s2);
        for (int i = 0; i < s1; i++) {
            tmpList1.add(list.get(l + i));
        }
        for (int i = 0; i < s2; i++) {
            tmpList2.add(list.get(mid + i + 1));
        }
        int i = 0;
        int j = 0;
        int cur = l;
        while (i < s1 && j < s2) {
            if (tmpList1.get(i).compareTo(tmpList2.get(j)) >= 0) {
                list.set(cur, tmpList2.get(j));
                j++;
            } else {
                list.set(cur, tmpList1.get(i));
                i++;
            }
            cur++;
        }
        for (; i < s1; i++) {
            list.set(cur++, tmpList1.get(i));
        }
        for (; j < s2; j++) {
            list.set(cur++, tmpList2.get(j));
        }
    }

    public static <E extends Comparable<? super E>> void heapSort(List<E> list) {
        int n = list.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            heapify(list, i, 0);
        }
    }

    private static <E extends Comparable<? super E>> void heapify(List<E> list, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && list.get(l).compareTo(list.get(largest)) > 0) {
            largest = l;
        }
        if (r < n && list.get(r).compareTo(list.get(largest)) > 0) {
            largest = r;
        }

        if (largest != i) {
            swap(list, i, largest);
            heapify(list, n, largest);
        }
    }


    public static <E extends Comparable<? super E>> void radixSort(List<E> list) {

    }

    public static <E extends Comparable<? super E>> void randomSort(List<E> list) {
        list.sort((a, b) -> {
            RandomGenerator random = new SecureRandom();
            int hash1 = random.nextInt();
            int hash2 = random.nextInt();
            if (hash1 < hash2) {
                return -1;
            } else if (hash1 > hash2) {
                return 1;
            } else {
                return a.compareTo(b);
            }
        });
    }

    public static <E extends Comparable<? super E>> void quickSort(List<E> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<? super E>> void quickSort(List<E> list, int l, int r) {
        if (r > l) {
            int pi = partition(list, l, r);
            quickSort(list, l, pi - 1);
            quickSort(list, pi + 1, r);
        }
    }

    private static <E extends Comparable<? super E>> int partition(List<E> list, int l, int r) {
        E pivot = list.get(r);
        int i = l - 1;

        for (int j = l; j <= r - 1; j++) {

            if (pivot.compareTo(list.get(j)) > 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, r);
        return (i + 1);
    }

    private static <E extends Comparable<? super E>> void swap(List<E> list, int i, int j) {
        E tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

}
