public class Test {
    public static void main(String[] args) {
        int cpuMhz = 2394;
        int core = 8;
        double total = ((double) cpuMhz * (double) core) / 1000d;

        //System.out.println(total);

        //System.out.println(Math.round(total * 100d) / 100d);

        long memoryTotal = 34359201792l;
        double l = (double)memoryTotal / 1024d / 1024d / 1024d;
        long memGb = Math.round(l);
        System.out.println(l);
        System.out.println(memGb);
    }
}