package vmware.rest.service.vapi;

import com.vmware.vcenter.VM;
import com.vmware.vcenter.VMTypes;
import com.vmware.vcenter.VMTypes.CreateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareVMService")
public class VMwareVMService {
    @Autowired
    private VM vmservice;

    public List<VMTypes.Summary> list() {

        return vmservice.list(new VMTypes.FilterSpec.Builder().build());
    }

    public String create(CreateSpec createSpec) {
        return vmservice.create(createSpec);
    }

    public VMTypes.Info get(String vmId) {
        VMTypes.Info info = vmservice.get(vmId);
        return info;
    }
}