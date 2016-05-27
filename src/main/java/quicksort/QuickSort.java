package quicksort;

/**
 * Created by bdanglot on 26/05/16.
 */
public class QuickSort {
//
//    public static List<Integer> sort(List<Integer> arr) {
//        if (!(arr.isEmpty())) {
//            int pivot = arr.get(0);
//            List<Integer> less = new LinkedList<Integer>();
//            List<Integer> pivotList = new LinkedList<Integer>();
//            List<Integer> more = new LinkedList<Integer>();
//            for (int i : arr) {
//                if (i < pivot)
//                    less.add(i);
//                else if (i > pivot)
//                    more.add(i);
//                else
//                    pivotList.add(i);
//
//            }
//            less = QuickSort.sort(less);
//            more = QuickSort.sort(more);
//            less.addAll(pivotList);
//            less.addAll(more);
//            return less;
//        }
//        return arr;
//    }

    private int[] array;

    public QuickSort(int[] values) {
        this.array = values;
    }

//    public void sort(int beg, int end) {
//        int left = beg, right = end;
//        int pivot = this.array[beg + ((end - beg) / 2)];
//
//        while (left <= right) {
//
//            while (this.array[left] < pivot) {
//                left++;
//            }
//
//            while (this.array[right] > pivot) {
//                right--;
//            }
//
//            if (left <= right) {
//                swap(left, right);
//                left++;
//                right--;
//            }
//
//        }
//        if (beg < right)
//            sort(beg, right);
//        if (end > left)
//            sort(left, end);
//    }

    public void sort(int beg, int end) {
        int left = beg, right = end;
        int pivot = this.array[beg + ((end - beg) / 2)];

        while (left <= right) {

            while (this.array[left] < pivot) {
                left = left + 1;
            }

            while (this.array[right] > pivot) {
                right = right - 1;
            }

            if (left <= right) {
                swap(left, right);
                left = left + 1;
                right = right - 1;
            }

        }
        if (beg < right)
            sort(beg, right);
        if (end > left)
            sort(left, end);
    }

    public int[] getArray() {
        return this.array;
    }

    public void swap(int i, int j) {
        int x = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = x;
    }


}
