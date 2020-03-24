package vmware.vim;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.common.helpers.VimUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VimTemplateDeploy {
    private VimAuthenticationHelper vimAuthenticationHelper;

    public VimTemplateDeploy() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public void deploy() {

    }

    public VirtualMachineCloneSpec getVirtualMachineCloneSpec() {
        ManagedObjectReference vmMor = new ManagedObjectReference();
        vmMor.setType("VirtualMachine");
        vmMor.setValue("vm-66");

        VirtualMachineCloneSpec virtualMachineCloneSpec = new VirtualMachineCloneSpec();
        virtualMachineCloneSpec.setTemplate(true);
        virtualMachineCloneSpec.setPowerOn(true);

        ManagedObjectReference hostMor = new ManagedObjectReference();
        hostMor.setType("HostSystem");
        hostMor.setValue("host-9");

        ManagedObjectReference resourceMor = new ManagedObjectReference();
        hostMor.setType("ResourcePool");
        hostMor.setValue("resgroup-8");

        List<String> typeList = Arrays.asList("sourceTests", "resourcePoolTests", "hostTests", "networkTests");

        VirtualMachineRelocateSpec virtualMachineRelocateSpec = new VirtualMachineRelocateSpec();
        virtualMachineRelocateSpec.setHost(hostMor);
        virtualMachineRelocateSpec.setPool(resourceMor);

        ManagedObjectReference vmProvisioningCheckerMor = vimAuthenticationHelper.getServiceContent().getVmProvisioningChecker();
        return null;
    }

    public void cloneVMTask() throws InsufficientResourcesFaultFaultMsg, MigrationFaultFaultMsg, TaskInProgressFaultMsg, InvalidStateFaultMsg, CustomizationFaultFaultMsg, FileFaultFaultMsg, InvalidDatastoreFaultMsg, VmConfigFaultFaultMsg, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference folderMor = new ManagedObjectReference();
        folderMor.setType("Folder");
        folderMor.setValue("group-v3");

        String name = "window template test 20200323";

        VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
        cloneSpec.setTemplate(false);
        cloneSpec.setPowerOn(true);

        cloneSpec.setLocation(getVirtualMachineRelocateSpec());
        cloneSpec.setConfig(newVirtualMachineConfigSpec());

        List<ObjectContent> templateSpec = this.getTemplateSpec();
        System.out.println("==cloneVMTask==");
        //ManagedObjectReference managedObjectReference = vimAuthenticationHelper.getVimPort().cloneVMTask(newMor("VirtualMachine", "vm-66"), folderMor, name, cloneSpec);
        //System.out.println("cloneVMTask : " + managedObjectReference.toString());
    }

    public VirtualMachineRelocateSpec getVirtualMachineRelocateSpec() {
        VirtualMachineRelocateSpec specLocation = new VirtualMachineRelocateSpec();
        VirtualMachineRelocateSpecDiskLocator virtualMachineRelocateSpecDiskLocator = new VirtualMachineRelocateSpecDiskLocator();
        virtualMachineRelocateSpecDiskLocator.setDatastore(newMor("Datastore", "datastore-10"));
        virtualMachineRelocateSpecDiskLocator.setDiskId(2000);
        specLocation.getDisk().add(virtualMachineRelocateSpecDiskLocator);

        specLocation.setDatastore(newMor("Datastore", "datastore-10"));
        specLocation.setHost(newMor("HostSystem", "host-9"));
        specLocation.setPool(newMor("ResourcePool", "resgroup-8"));

        return specLocation;
    }

    public VirtualMachineConfigSpec newVirtualMachineConfigSpec() {
        VirtualMachineConfigSpec specConfig = new VirtualMachineConfigSpec();
        specConfig.setMemoryMB(12288L);
        specConfig.setMemoryAllocation(newResourceAllocationInfo(122880, SharesLevel.NORMAL));
        specConfig.setNumCoresPerSocket(4);

        specConfig.setCpuAllocation(newResourceAllocationInfo(8000, SharesLevel.NORMAL));
        specConfig.setNumCPUs(8);

        VirtualDeviceConfigSpec virtualDeviceConfigSpec = new VirtualDeviceConfigSpec();
        VirtualDisk virtualDisk = new VirtualDisk();
        virtualDisk.setShares(newShareInfo(1000, SharesLevel.NORMAL));
        virtualDisk.setCapacityInBytes(42949672960L);
        virtualDeviceConfigSpec.setDevice(virtualDisk);

        StorageIOAllocationInfo storageIOAllocationInfo = new StorageIOAllocationInfo();
        storageIOAllocationInfo.setShares(newShareInfo(1000, SharesLevel.NORMAL));
        storageIOAllocationInfo.setLimit(-1L);
        storageIOAllocationInfo.setReservation(0);
        virtualDisk.setStorageIOAllocation(storageIOAllocationInfo);

        VirtualDiskFlatVer2BackingInfo virtualDiskFlatVer2BackingInfo = new VirtualDiskFlatVer2BackingInfo();
        virtualDiskFlatVer2BackingInfo.setBackingObjectId("");
        virtualDiskFlatVer2BackingInfo.setFileName("[datastore1] Windows 10 PRO(64bit)/Windows 10 PRO(64bit).vmdk");
        virtualDiskFlatVer2BackingInfo.setSplit(false);
        virtualDiskFlatVer2BackingInfo.setWriteThrough(false);
        virtualDiskFlatVer2BackingInfo.setDatastore(newMor("Datastore", "datastore-10"));
        virtualDiskFlatVer2BackingInfo.setContentId("ee9a222eacfb8b27e012e28b81ce0a09");
        virtualDiskFlatVer2BackingInfo.setThinProvisioned(false);
        virtualDiskFlatVer2BackingInfo.setDiskMode("persistent");
        virtualDiskFlatVer2BackingInfo.setDigestEnabled(false);
        virtualDiskFlatVer2BackingInfo.setSharing("sharingNone");
        virtualDiskFlatVer2BackingInfo.setUuid("6000C297-a8dc-a10f-cd8a-3b53df19281e");
        virtualDisk.setBacking(virtualDiskFlatVer2BackingInfo);

        virtualDisk.setControllerKey(1000);
        virtualDisk.setUnitNumber(0);
        virtualDisk.setNativeUnmanagedLinkedClone(false);
        virtualDisk.setCapacityInKB(41943040L);

        Description description = new Description();
        description.setSummary("33,554,432 KB");
        description.setLabel("Hard disk 1");
        virtualDisk.setDeviceInfo(description);

        virtualDisk.setDiskObjectId("56-2000");
        virtualDisk.setKey(2000);
        virtualDeviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.EDIT);

        VirtualMachineAffinityInfo virtualMachineAffinityInfo = new VirtualMachineAffinityInfo();
        virtualMachineAffinityInfo.getAffinitySet().add(0);
        //specConfig.getCpuFeatureMask().add(new VirtualMachineCpuIdInfoSpec());
        specConfig.getDeviceChange().add(virtualDeviceConfigSpec);

        return specConfig;
    }

    public ManagedObjectReference newMor(String type, String value) {
        ManagedObjectReference newMor = new ManagedObjectReference();
        newMor.setType(type);
        newMor.setValue(value);
        return newMor;
    }

    public ResourceAllocationInfo newResourceAllocationInfo(int shares, SharesLevel level) {
        ResourceAllocationInfo newResourceAllocationInfo = new ResourceAllocationInfo();
        newResourceAllocationInfo.setShares(newShareInfo(shares, level));
        return newResourceAllocationInfo;
    }

    public VirtualDeviceConfigSpec newVirtualDeviceConfigSpec() {
        VirtualDeviceConfigSpec newVirtualDeviceConfigSpec = new VirtualDeviceConfigSpec();
        newVirtualDeviceConfigSpec.setDevice(new VirtualDisk());
        return null;
    }

    public SharesInfo newShareInfo(int shares, SharesLevel level) {
        SharesInfo sharesInfo = new SharesInfo();
        sharesInfo.setShares(shares);
        sharesInfo.setLevel(level);
        return sharesInfo;
    }

    public VirtualDisk newVirtualDisk(int shares, SharesLevel level) {
        VirtualDisk virtualDisk = new VirtualDisk();
        virtualDisk.setShares(newShareInfo(1000, level));
        return virtualDisk;
    }

    public List<ObjectContent> getTemplateSpec() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        ObjectSpec objSpec = new ObjectSpec();
        objSpec.setObj(newMor("VirtualMachine", "vm-66"));

        ManagedObjectReference propCollectorRef = vimAuthenticationHelper.getServiceContent().getPropertyCollector();
        ManagedObjectReference rootFolderRef = vimAuthenticationHelper.getServiceContent().getRootFolder();

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setAll(Boolean.TRUE);
        propertySpec.setType("VirtualMachine");

        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(newMor("VirtualMachine", "vm-66"));
        objectSpec.setSkip(Boolean.FALSE);
        objectSpec.getSelectSet().add(tSpec);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getObjectSet().add(objectSpec);
        propertyFilterSpec.getPropSet().add(propertySpec);

        List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(1);
        listpfs.add(propertyFilterSpec);

        List<ObjectContent> objectContents = VimUtil.retrievePropertiesAllObjects(vimAuthenticationHelper.getVimPort(), propCollectorRef, listpfs);

        return objectContents;
    }
}