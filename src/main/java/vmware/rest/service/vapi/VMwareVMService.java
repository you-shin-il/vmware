package vmware.rest.service.vapi;

import com.vmware.vcenter.VM;
import com.vmware.vcenter.VMTypes;
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

}