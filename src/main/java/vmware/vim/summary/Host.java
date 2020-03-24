package vmware.vim.summary;

import java.util.List;

public class Host {
    //private String hostId;
    private Cpu cpu;
    private Memory memory;
    private List<Datastore> datastores;

    public Host() { }

    public Host(/*String hostId, */Cpu cpu, Memory memory, List<Datastore> datastore) {
        //this.hostId = hostId;
        this.cpu = cpu;
        this.memory = memory;
        this.datastores = datastore;
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

/*    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }*/

    public List<Datastore> getDatastores() {
        return datastores;
    }

    public void setDatastores(List<Datastore> datastores) {
        this.datastores = datastores;
    }

    public Storage getAllHostStorage() {
        long capacity = datastores.stream().map(Datastore::getStorage).mapToLong(Storage::getStorage_summaryCapacity).sum();
        long freeSpace = datastores.stream().map(Datastore::getStorage).mapToLong(Storage::getStorage_summaryFreeSpace).sum();
        return new Storage(capacity, freeSpace);
    }

}