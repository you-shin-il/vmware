package vmware.rest.service.vapi;

import com.vmware.vcenter.Host;
import com.vmware.vcenter.HostTypes;
import com.vmware.vcenter.vm.Hardware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareHardwareService")
public class VMwareHardwareService {
    @Autowired
    private Hardware hardwareservice;

}