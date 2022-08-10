package edu.neu.coe.huskySort.sort.simple;

import edu.neu.coe.huskySort.sort.ComparisonSortHelper;
import edu.neu.coe.huskySort.sort.HelperFactory;
import edu.neu.coe.huskySort.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickSort_Basic<X extends Comparable<X>> extends QuickSort<X> {

    public static final String DESCRIPTION = "QuickSort basic";

    public QuickSort_Basic(String description, int N, Config config) {
        super(description, N, config);
        setPartitioner(createPartitioner());
    }

    /**
     * Constructor for QuickSort_Basic
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public QuickSort_Basic(ComparisonSortHelper<X> helper) {
        super(helper);
        setPartitioner(createPartitioner());
    }

    /**
     * Constructor for QuickSort_Basic
     *
     * @param N the number elements we expect to sort.
     * @param config the configuration.
     */
    public QuickSort_Basic(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    /**
     * Constructor for QuickSort_Basic
     *
     * @param config the configuration.
     */
    public QuickSort_Basic(Config config) {
        this(0, config);
    }

    @Override
    public Partitioner<X> createPartitioner() {
        return new Partitioner_Basic(getHelper());
    }

    public class Partitioner_Basic implements Partitioner<X> {

        public Partitioner_Basic(ComparisonSortHelper<X> helper) {
            this.helper = helper;
        }

        /**
         * Method to partition the given partition into smaller partitions.
         *
         * @param partition the partition to divide up.
         * @return an array of partitions, whose length depends on the sorting
         * method being used.
         */
        public List<Partition<X>> partition(Partition<X> partition) {

            final X[] xs = partition.xs;
            final int from = partition.from;
            final int to = partition.to;
            final int hi = to - 1;
            X v = xs[from];
            int i = from;
            int j = to;

            if (helper.instrumented()) {
                while (true) {
                    while (i < hi && !helper.inverted(xs[++i], v)) {}
                    while (j > from && !helper.inverted(v, xs[--j])) {}
                    if (i >= j) break;
                    helper.swap(xs, i, j);
                }
                helper.swap(xs, from, j);
            } else {
                while (true) {
                    while (i < hi && xs[++i].compareTo(v) < 0) {}
                    while (j > from && xs[--j].compareTo(v) > 0) {}
                    if (i >= j) break;
                    swap(xs, i, j);
                }
                swap(xs, from, j);
            }

            List<Partition<X>> partitions = new ArrayList<>();
            partitions.add(new Partition<>(xs, from, j));
            partitions.add(new Partition<>(xs, j + 1, to));
            return partitions;
        }

        private void swap(X[] ys, int i, int j) {
            X temp = ys[i];
            ys[i] = ys[j];
            ys[j] = temp;
        }

        private final ComparisonSortHelper<X> helper;
    }

    public static void main(String[] args) throws IOException {
        Integer[] sizes = new Integer[17];
        if (args.length !=0 &&  args[0].equals("test")) {
            for (int i = 1; i <= sizes.length ; i++) {
                sizes[i-1] = i*10;
            }
        } else {
            int startSize = 16;
            for (int i = 0; i < 17; i++) {
                sizes[i] = startSize;
                startSize *= 2;
            }
        }
        Integer numberOfRuns = 1000;
        System.out.println("Averaging Benchmarks across " + numberOfRuns + " runs");
        for (Integer n : sizes) {
            double swapSum = 0.0;
            double compareSum = 0.0;
            if (n > 8000) {
                numberOfRuns = 100;
            }
            if(n > 64000) {
                numberOfRuns = 3;
            }
            for (int t = 0; t < numberOfRuns; t++) {

                final Config config2 = Config.setupConfig("true", "", "", "", "");
                final ComparisonSortHelper<Integer> helper = HelperFactory.create("quick sort basic", n, config2);

                final Integer[] unsortedArr = helper.random(Integer.class, r -> r.nextInt(n));

                QuickSort_Basic<Integer> s = new QuickSort_Basic(helper);
                s.init(n);
                helper.preProcess(unsortedArr);
                Integer[] ys = s.sort(unsortedArr);
                helper.postProcess(ys);

                final PrivateMethodInvoker privateMethodTester = new PrivateMethodInvoker(helper);
                final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
                swapSum += statPack.getStatistics(Instrumenter.SWAPS).mean();
                compareSum += statPack.getStatistics(Instrumenter.COMPARES).mean();
            }

            double avgSwap = swapSum / numberOfRuns;
            double avgCompare = compareSum / numberOfRuns;
            double ratio = avgSwap / avgCompare;

            System.out.println("--------------------------------------------------------");
            System.out.println("Runs: " + numberOfRuns);
            System.out.println("Array Size: " + n);
            System.out.println("Avg Swap Count: " + avgSwap);
            System.out.println("Avg Compare Count: " + avgCompare);
            System.out.println("Ratio: " + ratio);
        }
    }
}
