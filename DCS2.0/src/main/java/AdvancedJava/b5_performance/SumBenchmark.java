package AdvancedJava.b5_performance;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SumBenchmark {

    private int[] data = new int[1_000_000];

    @Setup
    public void setup() {
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
    }

    @Benchmark
    public int sequentialSum() {
        int sum = 0;
        for (int i : data) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    public int parallelSum() {
        return java.util.Arrays.stream(data).parallel().sum();
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}