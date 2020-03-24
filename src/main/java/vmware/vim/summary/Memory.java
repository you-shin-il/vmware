package vmware.vim.summary;

import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ObjectContent;

import java.util.List;

public class Memory {
    private Long mem_memorySize = new Long(0L);
    private Integer mem_overallMemoryUsage = 0;
    public static final String HARDWARE_MEMORYSIZE = "hardware.memorySize";
    public static final String SUMMARY_QUICKSTATS_OVERALLMEMORYUSAGE = "summary.quickStats.overallMemoryUsage";

    public Memory(Long mem_memorySize, Integer mem_overallMemoryUsage) {
        this.mem_memorySize = mem_memorySize;
        this.mem_overallMemoryUsage = mem_overallMemoryUsage;
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

    public long getMemoryGB() {
        double convertGB = (double) mem_memorySize / 1024d / 1024d / 1024d;
        return Math.round(convertGB);
    }

    public double getMemoryUse() {
        double memoryGb = (double) getMem_overallMemoryUsage() / 1024L;
        return Math.round(memoryGb * 100d) / 100d;
    }

    public double getAvailableMemory() {
        return (double)getMemoryGB() - getMemoryUse();
    }

    public static Memory newMemory(List<ObjectContent> objects) {
        long mem_memorySize = 0L;
        int mem_overallMemoryUsage = 0;

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if (HARDWARE_MEMORYSIZE.equals(dynamicProperty.getName())) {
                        mem_memorySize = (Long) dynamicProperty.getVal();
                    } else if(SUMMARY_QUICKSTATS_OVERALLMEMORYUSAGE.equals(dynamicProperty.getName())) {
                        mem_overallMemoryUsage = (Integer) dynamicProperty.getVal();
                    }
                }
            }
        }

        return new Memory(mem_memorySize, mem_overallMemoryUsage);
    }
}