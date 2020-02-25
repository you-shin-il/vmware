package vmware.rest.service.vapi;

import com.vmware.vcenter.Host;
import com.vmware.vcenter.HostTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareHostService")
public class VMwareHostService {
    @Autowired
    private Host hostservice;

    public List<HostTypes.Summary> list(HostTypes.FilterSpec filterSpec) {
        List<HostTypes.Summary> list = hostservice.list(filterSpec);
        return list;
    }

}