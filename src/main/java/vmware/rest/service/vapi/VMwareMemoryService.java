package vmware.rest.service.vapi;

import com.vmware.vcenter.VMTypes;
import com.vmware.vcenter.vm.hardware.Memory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareMemoryService")
public class VMwareMemoryService {
    @Autowired
    private Memory memoryservice;

    public List<VMTypes.Summary> list() {
/*        GuestOsDescriptor guestOsDescriptor = new GuestOsDescriptor();
        List<HostCpuIdInfo> cpuFeatureMask = guestOsDescriptor.getCpuFeatureMask();
        return null;*/
        return null;
    }

}