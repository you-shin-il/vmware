package vmware.vim.summary;

import java.util.List;

public class Cluster {
    List<Host> hostList;

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }
}