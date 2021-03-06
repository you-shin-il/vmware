package vmware.vim;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.vim.util.UtilPropertySpec;
import vmware.vim.util.UtilTraversalSpec;

import java.util.*;
import java.util.stream.Collectors;

public class VMwareVimVMService {
    private VimAuthenticationHelper vimAuthenticationHelper;

    public VMwareVimVMService() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public void list(String name) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference virtualMachine = vimAuthenticationHelper.getVimPort().createContainerView(
                vimAuthenticationHelper.getServiceContent().getViewManager(),
                vimAuthenticationHelper.getServiceContent().getRootFolder(),
                Arrays.asList("VirtualMachine"), true);

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(virtualMachine);
        oSpec.setSkip(true);

//        TraversalSpec ts = new TraversalSpec();
//        ts.setName("view");
//        ts.setPath("view");
//        ts.setSkip(false);
//        ts.setType("ContainerView");

        TraversalSpec vmNetworkSpec = new TraversalSpec();
        //tSpec.setName("traverseEntities");
        vmNetworkSpec.setType("VirtualMachine");
        vmNetworkSpec.setPath("network");

        TraversalSpec vmResourcePoolSpec = new TraversalSpec();
        //tSpec.setName("traverseEntities");
        vmResourcePoolSpec.setType("VirtualMachine");
        vmResourcePoolSpec.setPath("resourcePool");

        TraversalSpec tSpec = new TraversalSpec();
        //tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");
        tSpec.getSelectSet().add(vmNetworkSpec);
        tSpec.getSelectSet().add(vmResourcePoolSpec);

        oSpec.getSelectSet().add(tSpec);

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("VirtualMachine");
        //pSpec.getPathSet().add("");
        pSpec.setAll(true);

        PropertySpec pSpec2 = new PropertySpec();
        pSpec.setType("Network");
        //pSpec.getPathSet().add("");
        pSpec.setAll(true);

        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        RetrieveOptions retrieveOptions = new RetrieveOptions();

        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl, fSpecList, retrieveOptions);
//        return isTemplateFilter(retrieveResult.getObjects());
        }

    public void test(String name) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference viewManager = vimAuthenticationHelper.getServiceContent().getViewManager();

        ManagedObjectReference virtualMachine = vimAuthenticationHelper.getVimPort().createContainerView(
                vimAuthenticationHelper.getServiceContent().getViewManager(),
                vimAuthenticationHelper.getServiceContent().getRootFolder(),
                Arrays.asList("VirtualMachine"), true);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(virtualMachine);
        objectSpec.setSkip(Boolean.FALSE);

        TraversalSpec containerTraversalSpec = new TraversalSpec();
        containerTraversalSpec.setType("ContainerView");
        containerTraversalSpec.setPath("view");
        containerTraversalSpec.setSkip(Boolean.FALSE);

        TraversalSpec vmTraversalSpec = new TraversalSpec();
        vmTraversalSpec.setType("VirtualMachine");
        vmTraversalSpec.setPath("resourcePool");
        vmTraversalSpec.setSkip(Boolean.FALSE);

        TraversalSpec networkTraversalSpec = new TraversalSpec();
        networkTraversalSpec.setType("VirtualMachine");
        networkTraversalSpec.setPath("network");
        networkTraversalSpec.setSkip(Boolean.FALSE);

        containerTraversalSpec.getSelectSet().add(vmTraversalSpec);
        containerTraversalSpec.getSelectSet().add(networkTraversalSpec);

        objectSpec.getSelectSet().add(containerTraversalSpec);

//        objectSpec.getSelectSet().add(containerTraversalSpec);
//        objectSpec.getSelectSet().add(vmTraversalSpec);
//        objectSpec.getSelectSet().add(networkTraversalSpec);
        propertyFilterSpec.getObjectSet().add(objectSpec);

        PropertySpec vmPropertySpec = new PropertySpec();
        vmPropertySpec.setType("VirtualMachine");
        vmPropertySpec.getPathSet().add("name");
        vmPropertySpec.getPathSet().add("network");
        vmPropertySpec.getPathSet().add("resourcePool");

        PropertySpec networkPropertySpec = new PropertySpec();
        networkPropertySpec.setType("Network");
        networkPropertySpec.setAll(Boolean.TRUE);
        //networkPropertySpec.getPathSet().add("summary.accessible");

        PropertySpec resourcePoolPropertySpec = new PropertySpec();
        resourcePoolPropertySpec.setType("ResourcePool");
        resourcePoolPropertySpec.getPathSet().add("runtime.cpu.maxUsage");
        resourcePoolPropertySpec.getPathSet().add("runtime.memory.maxUsage");

        propertyFilterSpec.getPropSet().add(vmPropertySpec);
        propertyFilterSpec.getPropSet().add(networkPropertySpec);
        propertyFilterSpec.getPropSet().add(resourcePoolPropertySpec);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(propertyFilterSpec);

        RetrieveOptions retrieveOptions = new RetrieveOptions();

        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl, fSpecList, retrieveOptions);
        Set<String> typeSet = new HashSet<String>();
        typeSet.add("VirtualMachine");
        typeSet.add("ResourcePool");
        typeSet.add("Network");

        typeFilter(retrieveResult, typeSet);
    }

    /* 데이터스토어 폴더 Mor 조회 테스트 */
    public void datastoreFolderMorTest() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference viewManager = vimAuthenticationHelper.getServiceContent().getViewManager();

        ManagedObjectReference datacenter = vimAuthenticationHelper.getVimPort().createContainerView(
                vimAuthenticationHelper.getServiceContent().getViewManager(),
                vimAuthenticationHelper.getServiceContent().getRootFolder(),
                Arrays.asList("Datacenter"), true);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(datacenter);
        objectSpec.setSkip(Boolean.FALSE);

        objectSpec.getSelectSet().add(getTraversalSpec());

        propertyFilterSpec.getObjectSet().add(objectSpec);

        PropertySpec datacenterPropertySpec = UtilPropertySpec.createPropertySpec(UtilPropertySpec.ALL_PATH_FALSE, UtilPropertySpec.Type.Datacenter, Arrays.asList("vmFolder", "hostFolder", "datastoreFolder", "networkFolder"));

//        datacenterPropertySpec.setType("Datacenter");
//        datacenterPropertySpec.getPathSet().addAll(Arrays.asList("vmFolder", "hostFolder", "datastoreFolder", "networkFolder"));

        PropertySpec folderPropertySpec
                = UtilPropertySpec.createPropertySpec(UtilPropertySpec.ALL_PATH_FALSE, UtilPropertySpec.Type.Folder, Arrays.asList("childEntity"));
//        folderPropertySpec.setType("Folder");
//        folderPropertySpec.setAll(Boolean.FALSE);
//        folderPropertySpec.getPathSet().addAll(Arrays.asList("childEntity"));

        PropertySpec vmPropertySpec
                = UtilPropertySpec.createPropertySpec(UtilPropertySpec.ALL_PATH_TRUE,
                                                      UtilPropertySpec.Type.VirtualMachine,
                                                      Arrays.asList("name", "guestHeartbeatStatus", "overallStatus", "configStatus", "parent",
                                                                    "summary.guest.guestId", "summary.guest.guestFullName", "summary.guest.toolsStatus",
                                                                    "summary.guest.hostName", "summary.guest.ipAddress", "summary.storage.committed",
                                                                    "summary.quickStats.overallCpuUsage", "summary.quickStats.guestMemoryUsage", "runtime.host",
                                                                    "runtime.powerState", "runtime.bootTime")
                                                     );

//        vmPropertySpec.setType("VirtualMachine");
//        vmPropertySpec.getPathSet().addAll(Arrays.asList("name"));

        PropertySpec clusterPropertySpec = new PropertySpec();
        clusterPropertySpec.setType("ClusterComputeResource");
        clusterPropertySpec.setAll(Boolean.TRUE);

        PropertySpec hostPropertySpec
                = UtilPropertySpec.createPropertySpec(UtilPropertySpec.ALL_PATH_TRUE, UtilPropertySpec.Type.HostSystem, null);

        propertyFilterSpec.getPropSet().add(folderPropertySpec);
        propertyFilterSpec.getPropSet().add(datacenterPropertySpec);
        propertyFilterSpec.getPropSet().add(vmPropertySpec);
        propertyFilterSpec.getPropSet().add(clusterPropertySpec);
        propertyFilterSpec.getPropSet().add(hostPropertySpec);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(propertyFilterSpec);

        RetrieveOptions retrieveOptions = new RetrieveOptions();

        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl, fSpecList, retrieveOptions);
        datacenterInit(retrieveResult);
    }

    public HashMap<String, List<ObjectContent>> typeFilter(RetrieveResult retrieveResult, Set<String> typeSet) {
        Map<String, List<ObjectContent>> morTypeMap = retrieveResult.getObjects().stream().
                filter(mor -> typeSet.contains(mor.getObj().getType())).
                collect(Collectors.groupingBy(x -> x.getObj().getType()));

        List<ObjectContent> networkObjectContents = morTypeMap.get("Network");
        List<ObjectContent> virtualMachineObjectContents = morTypeMap.get("VirtualMachine");

        return null;
    }

    public TraversalSpec getTraversalSpec() {
        TraversalSpec containerTraversalSpec = UtilTraversalSpec.createTraversalSpec(Boolean.FALSE, UtilTraversalSpec.Type.ContainerView, UtilTraversalSpec.Path.view);
//        containerTraversalSpec.setType("ContainerView");
//        containerTraversalSpec.setPath("view");
//        containerTraversalSpec.setSkip(Boolean.FALSE);

        TraversalSpec folderChildEntityTraversalSpec = UtilTraversalSpec.createTraversalSpec(Boolean.FALSE, UtilTraversalSpec.Type.Folder, UtilTraversalSpec.Path.childEntity);
//        folderChildEntityTraversalSpec.setType("Folder");
//        folderChildEntityTraversalSpec.setPath("childEntity");
//        folderChildEntityTraversalSpec.setSkip(Boolean.FALSE);

//        TraversalSpec vmFolderTraversalSpec = UtilTraversalSpec.getTraversalSpec(UtilTraversalSpec.Path.vmFolder, UtilTraversalSpec.Type.Datacenter, Boolean.FALSE);
//        vmFolderTraversalSpec.setType("Datacenter");//datastoreTraversalSpec.setPath("resourcePool");
//        vmFolderTraversalSpec.setPath("vmFolder");
//        vmFolderTraversalSpec.setSkip(Boolean.FALSE);
//        containerTraversalSpec.getSelectSet().add(vmFolderTraversalSpec);

        TraversalSpec hostFolderTraversalSpec = UtilTraversalSpec.createTraversalSpec(Boolean.FALSE, UtilTraversalSpec.Type.Datacenter, UtilTraversalSpec.Path.hostFolder);
//        hostFolderTraversalSpec.setType("Datacenter");//datastoreTraversalSpec.setPath("resourcePool");
//        hostFolderTraversalSpec.setPath("hostFolder");
//        hostFolderTraversalSpec.setSkip(Boolean.FALSE);
        containerTraversalSpec.getSelectSet().add(hostFolderTraversalSpec);

        TraversalSpec clusterComputeResourceTraversalSpec = UtilTraversalSpec.createTraversalSpec(Boolean.FALSE, UtilTraversalSpec.Type.ClusterComputeResource, UtilTraversalSpec.Path.host);
        folderChildEntityTraversalSpec.getSelectSet().add(clusterComputeResourceTraversalSpec);

        //vmFolderTraversalSpec.getSelectSet().add(folderChildEntityTraversalSpec);
        hostFolderTraversalSpec.getSelectSet().add(folderChildEntityTraversalSpec);
        //folderChildEntityTraversalSpec.getSelectSet().add(clusterComputeResourceTraversalSpec);

        TraversalSpec hostTraversalSpec = UtilTraversalSpec.createTraversalSpec(Boolean.FALSE, UtilTraversalSpec.Type.HostSystem, UtilTraversalSpec.Path.vm);
        clusterComputeResourceTraversalSpec.getSelectSet().add(hostTraversalSpec);

        //vmFolderTraversalSpec.getSelectSet().add(folderChildEntityTraversalSpec);
        //hostFolderTraversalSpec.getSelectSet().add(folderChildEntityTraversalSpec);

/*        TraversalSpec hostFolderTraversalSpec = new TraversalSpec();
        hostFolderTraversalSpec.setType("Datacenter");//datastoreTraversalSpec.setPath("resourcePool");
        hostFolderTraversalSpec.setSkip(Boolean.FALSE);
        hostFolderTraversalSpec.setPath("hostFolder");
        containerTraversalSpec.getSelectSet().add(hostFolderTraversalSpec);

        TraversalSpec datastoreFolderTraversalSpec = new TraversalSpec();
        datastoreFolderTraversalSpec.setType("Datacenter");//datastoreTraversalSpec.setPath("resourcePool");
        datastoreFolderTraversalSpec.setSkip(Boolean.FALSE);
        datastoreFolderTraversalSpec.setPath("datastoreFolder");
        containerTraversalSpec.getSelectSet().add(datastoreFolderTraversalSpec);

        TraversalSpec networkFolderTraversalSpec = new TraversalSpec();
        networkFolderTraversalSpec.setType("Datacenter");//datastoreTraversalSpec.setPath("resourcePool");
        networkFolderTraversalSpec.setSkip(Boolean.FALSE);
        networkFolderTraversalSpec.setPath("networkFolder");
        containerTraversalSpec.getSelectSet().add(networkFolderTraversalSpec);*/

        return containerTraversalSpec;
    }

    private Datacenter datacenterInit(RetrieveResult retrieveResult) {
        List<ObjectContent> objects = retrieveResult.getObjects();
        List<ObjectContent> vmObjectContent = objects.stream().filter(objectContent -> "VirtualMachine".equals(objectContent.getObj().getType())).collect(Collectors.toList());
        List<VM> collect = vmObjectContent.stream().map(objectContent -> new VM(objectContent)).collect(Collectors.toList());
        return null;
    }

}