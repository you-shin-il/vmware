package vmware.vim.summary;

public class CpuMemoryStorageValue {
    private Integer cpu_cpuTotal = 0;
    private Integer cpu_overallCpuUsage = 0;
    private Short cpu_numCpuCores = 0;
    private Long mem_memorySize = new Long(0L);
    private Integer mem_overallMemoryUsage = 0;
    private Long storage_summaryCapacity = new Long(0L);
    private Long storage_summaryFreeSpace = new Long(0L);

    public Integer getCpu_cpuTotal() {
        return cpu_cpuTotal;
    }

    public void setCpu_cpuTotal(Integer cpu_cpuTotal) {
        this.cpu_cpuTotal = cpu_cpuTotal;
    }

    public Integer getCpu_overallCpuUsage() {
        return cpu_overallCpuUsage;
    }

    public void setCpu_overallCpuUsage(Integer cpu_overallCpuUsage) {
        this.cpu_overallCpuUsage = cpu_overallCpuUsage;
    }

    public Short getCpu_numCpuCores() {
        return cpu_numCpuCores;
    }

    public void setCpu_numCpuCores(Short cpu_numCpuCores) {
        this.cpu_numCpuCores = cpu_numCpuCores;
    }

    public Long getMem_memorySize() {
        return mem_memorySize;
    }

    public void setMem_memorySize(Long mem_memorySize) {
        this.mem_memorySize = mem_memorySize;
    }

    public Integer getMem_overallMemoryUsage() {
        return mem_overallMemoryUsage;
    }

    public void setMem_overallMemoryUsage(Integer mem_overallMemoryUsage) {
        this.mem_overallMemoryUsage = mem_overallMemoryUsage;
    }

    public Long getStorage_summaryCapacity() {
        return storage_summaryCapacity;
    }

    public void setStorage_summaryCapacity(Long storage_summaryCapacity) {
        this.storage_summaryCapacity = storage_summaryCapacity;
    }

    public Long getStorage_summaryFreeSpace() {
        return storage_summaryFreeSpace;
    }

    public void setStorage_summaryFreeSpace(Long storage_summaryFreeSpace) {
        this.storage_summaryFreeSpace = storage_summaryFreeSpace;
    }
}