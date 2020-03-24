package vmware.rest.service.vapi;


import com.vmware.vcenter.Network;
import com.vmware.vcenter.NetworkTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareNetworkService")
public class VMwareNetworkService {
    @Autowired
    private Network networkservice;

    public List<NetworkTypes.Summary> list(NetworkTypes.FilterSpec filterSpec) {
        List<NetworkTypes.Summary> list = networkservice.list(filterSpec);
        return list;
    }

}