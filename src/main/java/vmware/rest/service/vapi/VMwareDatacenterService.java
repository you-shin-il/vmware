package vmware.rest.service.vapi;

import com.vmware.vcenter.Datacenter;
import com.vmware.vcenter.DatacenterTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareDatacenterService")
public class VMwareDatacenterService {
    @Autowired
    private Datacenter datacenterservice;

    public List<DatacenterTypes.Summary> list(DatacenterTypes.FilterSpec filterSpec) {
        return datacenterservice.list(filterSpec);
    }

    public DatacenterTypes.Info get(String name) {
        return datacenterservice.get(name);
    }
}