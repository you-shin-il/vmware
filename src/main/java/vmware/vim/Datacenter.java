package vmware.vim;

import vmware.vim.summary.Clusters;
import vmware.vim.summary.Hosts;

import java.util.List;

public class Datacenter {
    private List<String> vmFolder;
    private List<String> hostFolder;
    private List<String> dataFolder;
    private List<String> networkFolder;
    private Hosts hosts;
    private Clusters clusters;

    public List<String> getVmFolder() {
        return vmFolder;
    }

    public void setVmFolder(List<String> vmFolder) {
        this.vmFolder = vmFolder;
    }

    public List<String> getHostFolder() {
        return hostFolder;
    }

    public void setHostFolder(List<String> hostFolder) {
        this.hostFolder = hostFolder;
    }

    public List<String> getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(List<String> dataFolder) {
        this.dataFolder = dataFolder;
    }

    public List<String> getNetworkFolder() {
        return networkFolder;
    }

    public void setNetworkFolder(List<String> networkFolder) {
        this.networkFolder = networkFolder;
    }
}