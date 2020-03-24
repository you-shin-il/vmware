package vmware.vim.summary;

import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ObjectContent;

import java.util.List;

public class Cpu {
    private Integer cpu_cpuMhz = 0;
    private Integer cpu_overallCpuUsage = 0;
    private Short cpu_numCpuCores = 0;
    private String model;

    public static final String SUMMARY_HARDWARE_CPUMODEL = "summary.hardware.cpuModel";
    public static final String SUMMARY_HARDWARE_CPUMHZ = "summary.hardware.cpuMhz";
    public static final String SUMMARY_HARDWARE_NUMCPUCORES = "summary.hardware.numCpuCores";
    public static final String SUMMARY_QUICKSTATS_OVERALLCPUUSAGE = "summary.quickStats.overallCpuUsage";
    //public static final Set<String> propertyNameSet;

/*    static {
        propertyNameSet = new HashSet<String>();
        propertyNameSet.add(SUMMARY_HARDWARE_CPUMHZ);
        propertyNameSet.add(SUMMARY_HARDWARE_NUMCPUCORES);
        propertyNameSet.add(SUMMARY_QUICKSTATS_OVERALLCPUUSAGE);
    }*/

    public Cpu(String model, Integer cpu_cpuMhz, Integer cpu_overallCpuUsage, Short cpu_numCpuCores) {
        this.model = model;
        this.cpu_cpuMhz = cpu_cpuMhz;
        this.cpu_overallCpuUsage = cpu_overallCpuUsage;
        this.cpu_numCpuCores = cpu_numCpuCores;
    }

    public Integer getCpu_cpuMhz() {
        return cpu_cpuMhz;
    }

    public void setCpu_cpuMhz(Integer cpu_cpuMhz) {
        this.cpu_cpuMhz = cpu_cpuMhz;
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

    public double getCpuGhz() {
        double cpuMultiply = (getCpu_cpuMhz() * getCpu_numCpuCores()) / 1000d;
        return Math.round(cpuMultiply * 100d) / 100d;
    }

    public double getAvailableCpu() {
        double convertGhz = (double)getCpu_overallCpuUsage() / 1000d;
        return Math.round((getCpuGhz() - convertGhz) * 100d) / 100d;
    }

    public static Cpu newCpu(List<ObjectContent> objects) {
        String cpuModel = "";
        int cpu_cpuMhz = 0;
        int cpu_overallCpuUsage = 0;
        short cpu_numCpuCores = 0;

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if (SUMMARY_HARDWARE_CPUMODEL.equals(dynamicProperty.getName())) {
                        cpuModel = (String) dynamicProperty.getVal();
                    } else if (SUMMARY_HARDWARE_CPUMHZ.equals(dynamicProperty.getName())) {
                        cpu_cpuMhz = (int) dynamicProperty.getVal();
                    } else if(SUMMARY_QUICKSTATS_OVERALLCPUUSAGE.equals(dynamicProperty.getName())) {
                        cpu_overallCpuUsage = (int) dynamicProperty.getVal();
                    } else if(SUMMARY_HARDWARE_NUMCPUCORES.equals(dynamicProperty.getName())) {
                        cpu_numCpuCores = (short) dynamicProperty.getVal();
                    }
                }
            }
        }
        return new Cpu(cpuModel, cpu_cpuMhz, cpu_overallCpuUsage, cpu_numCpuCores);
    }
}