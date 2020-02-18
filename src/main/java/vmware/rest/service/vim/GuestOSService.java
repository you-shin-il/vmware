package vmware.rest.service.vim;

import com.vmware.vcenter.HostTypes;
import com.vmware.vim25.*;

import java.util.List;

public class GuestOSService {
    private VimPortType vimPortType;
    private ServiceContent serviceContent;

    public GuestOSService(VimPortType vimPortType, ServiceContent serviceContent) {
        this.vimPortType = vimPortType;
        this.serviceContent = serviceContent;
    }

    public List<GuestOsDescriptor> getGuestOsDescriptor() throws RuntimeFaultFaultMsg {
        ManagedObjectReference mor1 = new ManagedObjectReference();
        mor1.setType(HostTypes.RESOURCE_TYPE);
        mor1.setValue("host-9");

        VirtualMachineConfigOption mor = vimPortType.queryConfigOption(mor1, "vmx-14", null);
        return null;
    }
}