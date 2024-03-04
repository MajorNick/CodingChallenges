package com.majornick.sorttool.utils;

import java.util.ArrayList;
import java.util.List;

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

    public static void heapSort(List<String> lines) {

    }


    public static void radixSort(List<String> lines) {
    }

    public static <E extends Comparable<? super E>> void randomSort(List<E> list) {

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
