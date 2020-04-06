package vmware.rest.service.vapi;

import com.vmware.vcenter.vm.Hardware;
import com.vmware.vcenter.vm.HardwareTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vmwareHardwareService")
public class VMwareHardwareService {
    @Autowired
    private Hardware hardwareservice;

    public HardwareTypes.Info info(String vm) {
        HardwareTypes.Info info = hardwareservice.get(vm);
        return info;
    }

    public void update(String vm, HardwareTypes.UpdateSpec uSpec) {
        hardwareservice.update(vm, uSpec);
    }
}