package vmware.vim.summary;

import java.util.List;
import java.util.stream.Collectors;

public class Hosts {
    private List<Host> hostList;
    private Cpu cpu;
    private Memory memory;
    private Storage storage;

    public Hosts(List<Host> hostList) {
        this.hostList = hostList;
        this.mergeCpu();
        this.mergeMemory();
        this.mergeStorage();
/*        List<Memory> memorys = hostList.stream().map(Host::getMemory).collect(Collectors.toList());
        List<Storage> storages = hostList.stream().map(Host::getDatastores).flatMap(x -> x.stream()).map(Datastore::getStorage).collect(Collectors.toList());*/
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
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

    private void mergeCpu() {
        List<Cpu> cpus = hostList.stream().map(Host::getCpu).collect(Collectors.toList());
        Integer cpuGhz = cpus.stream().mapToInt(Cpu::getCpu_cpuMhz).sum();
        Integer overallCpuUsage = cpus.stream().mapToInt(Cpu::getCpu_overallCpuUsage).sum();

        //this.cpu = new Cpu(cpuGhz, overallCpuUsage, (short)1);
    }

    private void mergeMemory() {
        List<Memory> memorys = hostList.stream().map(Host::getMemory).collect(Collectors.toList());
        long mem_memorySize = memorys.stream().mapToLong(Memory::getMem_memorySize).sum();
        int mem_overallMemoryUsage = memorys.stream().mapToInt(Memory::getMem_overallMemoryUsage).sum();

        this.memory = new Memory(mem_memorySize, mem_overallMemoryUsage);
    }

    private void mergeStorage() {
        List<Storage> storages = hostList.stream().map(Host::getDatastores).flatMap(x -> x.stream()).map(Datastore::getStorage).collect(Collectors.toList());
        long storage_summaryCapacity = storages.stream().mapToLong(Storage::getStorage_summaryCapacity).sum();
        long storage_summaryFreeSpace = storages.stream().mapToLong(Storage::getStorage_summaryCapacity).sum();

        this.storage = new Storage(storage_summaryCapacity, storage_summaryFreeSpace);
    }
}