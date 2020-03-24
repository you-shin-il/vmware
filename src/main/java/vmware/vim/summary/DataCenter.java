package vmware.vim.summary;

import java.util.List;
import java.util.stream.Collectors;

public class DataCenter {
    private List<Host> hosts;
    private Clusters clusters;
    private double cpuGhz;
    private double availableCpu;
    private int cpu_overallCpuUsage;
    private Memory memory;
    private Storage storage;

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
        this.mergeCpu();
        this.mergeMemory();
        this.mergeStorage();
    }

    public Clusters getClusters() {
        return clusters;
    }

    public void setClusters(Clusters clusters) {
        this.clusters = clusters;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public double getCpuGhz() {
        return cpuGhz;
    }

    public void setCpuGhz(double cpuGhz) {
        this.cpuGhz = cpuGhz;
    }

    public double getAvailableCpu() {
        return availableCpu;
    }

    public void setAvailableCpu(double availableCpu) {
        this.availableCpu = availableCpu;
    }

    public int getCpu_overallCpuUsage() {
        return cpu_overallCpuUsage;
    }

    public void setCpu_overallCpuUsage(int cpu_overallCpuUsage) {
        this.cpu_overallCpuUsage = cpu_overallCpuUsage;
    }

    private void mergeCpu() {
        List<Cpu> cpus = hosts.stream().map(Host::getCpu).collect(Collectors.toList());
        //Integer cpuGhz = cpus.stream().mapToInt(Cpu::getCpu_cpuMhz).sum();
        cpuGhz = cpus.stream().mapToDouble(Cpu::getCpuGhz).sum();
        availableCpu = cpus.stream().mapToDouble(Cpu::getAvailableCpu).sum();
        cpu_overallCpuUsage = cpus.stream().mapToInt(Cpu::getCpu_overallCpuUsage).sum();
        //Integer overallCpuUsage = cpus.stream().mapToInt(Cpu::getCpu_overallCpuUsage).sum();

        //this.cpu = new Cpu(cpuGhz, overallCpuUsage, (short)1);
    }

    private void mergeMemory() {
        List<Memory> memorys = hosts.stream().map(Host::getMemory).collect(Collectors.toList());
        long mem_memorySize = memorys.stream().mapToLong(Memory::getMem_memorySize).sum();
        int mem_overallMemoryUsage = memorys.stream().mapToInt(Memory::getMem_overallMemoryUsage).sum();

        this.memory = new Memory(mem_memorySize, mem_overallMemoryUsage);
    }

    private void mergeStorage() {
        List<Storage> storages = hosts.stream().map(Host::getDatastores).flatMap(x -> x.stream()).map(Datastore::getStorage).collect(Collectors.toList());
        long storage_summaryCapacity = storages.stream().mapToLong(Storage::getStorage_summaryCapacity).sum();
        long storage_summaryFreeSpace = storages.stream().mapToLong(Storage::getStorage_summaryCapacity).sum();

        this.storage = new Storage(storage_summaryCapacity, storage_summaryFreeSpace);
    }
}