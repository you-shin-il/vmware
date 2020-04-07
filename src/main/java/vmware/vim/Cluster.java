package vmware.vim;

import java.util.List;

public class Cluster {
    private List<VM> vms;

    public List<VM> getVms() {
        return vms;
    }

    public void setVms(List<VM> vms) {
        this.vms = vms;
    }
}