package AdvancedJava.b2_concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ForkJoinPool pool = new ForkJoinPool();

        SumTask sumTask = new SumTask(array, 0, array.length);
        int result = pool.invoke(sumTask);

        System.out.println("Toplam: " + result);
    }

    static class SumTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        int[] array;
        int start;
        int end;

        SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumTask task1 = new SumTask(array, start, mid);
                SumTask task2 = new SumTask(array, mid, end);
                task1.fork();
                task2.fork();
                return task1.join() + task2.join();
            }
        }
    }
}
