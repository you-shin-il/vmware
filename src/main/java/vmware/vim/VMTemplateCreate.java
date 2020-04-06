package vmware.vim;

import com.vmware.vim25.*;

import java.util.List;

public class VMTemplateCreate {
    private List<VirtualDisk> disks;
    private List<VirtualCdrom> cdroms;
    private VirtualMachineConfigSpec virtualMachineConfigSpec;
    private VirtualMachineCloneSpec virtualMachineCloneSpec;
    private VirtualMachineRelocateSpec virtualMachineRelocateSpec;

    public VMTemplateCreate(List<VirtualDisk> disks, List<VirtualCdrom> cdroms) {
        this.disks = disks;
        this.cdroms = cdroms;
    }

    public void setVirtualMachineConfigSpec() {
        virtualMachineConfigSpec = new VirtualMachineConfigSpec();
        List<VirtualDeviceConfigSpec> deviceChange = virtualMachineConfigSpec.getDeviceChange();
        VirtualDeviceConfigSpec virtualDeviceConfigSpec = new VirtualDeviceConfigSpec();
    }

    public void setVirtualMachineCloneSpec() {

    }

    public void setVirtualMachineRelocateSpec() {

    }

    public void setVirtualDeviceConfigSpec() {

    }
}