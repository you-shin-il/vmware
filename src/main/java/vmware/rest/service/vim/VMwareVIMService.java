package vmware.rest.service.vim;

import com.vmware.vcenter.Cluster;
import com.vmware.vcenter.ClusterTypes;
import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.common.helpers.VimUtil;
import vmware.common.helpers.WaitForValues;
import vmware.common.vcha.helpers.TaskHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("vmwareVIMService")
public class VMwareVIMService {
    @Autowired
    private VimAuthenticationHelper vimAuthenticationHelper;
    @Autowired
    private Cluster clusterservice;

    public void test() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, NotFoundFaultMsg, HostConfigFaultFaultMsg, InvalidDatastoreFaultMsg, FileFaultFaultMsg, InvalidCollectorVersionFaultMsg {
        //VirtualMachineConfigOption virtualMachineConfigOption = test1();
        //RetrieveResult environmentBrowser = this.getEnvironmentBrowser();
        //List<ObjectContent> datastore = this.getDatastore();
        //RetrieveResult browser = this.getBrowser();
        //List<VirtualMachineConfigOptionDescriptor> virtualMachineConfigOptionDescriptors = this.queryConfigOptionDescriptor();
//        VirtualMachineConfigOption virtualMachineConfigOption1 = this.queryConfigOption();
//        VirtualMachineConfigOption virtualMachineConfigOption2 = this.queryConfigOptionEx();
//        List<ManagedObjectReference> hosts = getHosts();
//        List<ObjectContent> entityByName = getEntityByName();
//        ManagedObjectReference m1 = new ManagedObjectReference();
//        m1.setType("HostSystem");
//        m1.setValue("host-9");
//
//        ManagedObjectReference m2 = new ManagedObjectReference();
//        m2.setType("EnvironmentBrowser");
//        m2.setValue("envbrowser-7");
//
//        VirtualMachineConfigOption virtualMachineConfigOption = vimAuthenticationHelper.getVimPort().queryConfigOption(m1, "vmx-14", m2);
        //ManagedObjectReference cluster = this.getCluster();
        ManagedObjectReference datastoreSubFoldersTask = this.getDatastoreSubFoldersTask();
        System.out.println("=============");
    }

    private VirtualMachineConfigOption queryConfigOption() throws RuntimeFaultFaultMsg {
        ManagedObjectReference morHost = new ManagedObjectReference();
        morHost.setType("HostSystem");
        morHost.setValue("host-9");

        ManagedObjectReference morEnv = new ManagedObjectReference();
        morEnv.setType("EnvironmentBrowser");
        morEnv.setValue("envbrowser-7");

        VirtualMachineConfigOption virtualMachineConfigOption = vimAuthenticationHelper.getVimPort().queryConfigOption(morEnv, "vmx-14", morHost);
        return virtualMachineConfigOption;
    }

    private VirtualMachineConfigOption queryConfigOptionEx() throws RuntimeFaultFaultMsg {
        ManagedObjectReference morHost = new ManagedObjectReference();
        morHost.setType("HostSystem");
        morHost.setValue("host-9");

        ManagedObjectReference morEnv = new ManagedObjectReference();
        morEnv.setType("EnvironmentBrowser");
        morEnv.setValue("envbrowser-7");

        EnvironmentBrowserConfigOptionQuerySpec environmentBrowserConfigOptionQuerySpec = new EnvironmentBrowserConfigOptionQuerySpec();

        environmentBrowserConfigOptionQuerySpec.setHost(morHost);
        environmentBrowserConfigOptionQuerySpec.getGuestId().add("rhel8_64Guest");
        environmentBrowserConfigOptionQuerySpec.setKey("vmx-14");

        VirtualMachineConfigOption virtualMachineConfigOption = vimAuthenticationHelper.getVimPort().queryConfigOptionEx(morEnv, environmentBrowserConfigOptionQuerySpec);

        return virtualMachineConfigOption;
    }

    private ManagedObjectReference getCluster() throws InvalidPropertyFaultMsg, NotFoundFaultMsg, RuntimeFaultFaultMsg {
        List<ClusterTypes.Summary> list = clusterservice.list(new ClusterTypes.FilterSpec());
        ManagedObjectReference cluster = VimUtil.getCluster(vimAuthenticationHelper.getVimPort(), vimAuthenticationHelper.getServiceContent(), "cluster");
        return cluster;
    }

    private ServiceContent getServiceContext() throws InvalidPropertyFaultMsg, NotFoundFaultMsg, RuntimeFaultFaultMsg {
        ServiceContent serviceContent = VimUtil.getServiceContent(vimAuthenticationHelper.getVimPort());
        return serviceContent;
    }

    private List<ManagedObjectReference> getHosts() throws InvalidPropertyFaultMsg, NotFoundFaultMsg, RuntimeFaultFaultMsg {
        List<ManagedObjectReference> hosts = VimUtil.getHosts(vimAuthenticationHelper.getVimPort(), getServiceContext(), getCluster());
        return hosts;
    }

    private List<VirtualMachineConfigOptionDescriptor> queryConfigOptionDescriptor() throws RuntimeFaultFaultMsg {
        ManagedObjectReference mor = new ManagedObjectReference();
        //mor.setType("EnvironmentBrowser");
        //mor.setValue("envbrowser-7");
        List<VirtualMachineConfigOptionDescriptor> virtualMachineConfigOptionDescriptors = vimAuthenticationHelper.getVimPort().queryConfigOptionDescriptor(mor);

        return virtualMachineConfigOptionDescriptors;
    }
    private List<ObjectContent> getEntityByName() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ServiceContent serviceContent = VimUtil.getServiceContent(vimAuthenticationHelper.getVimPort());
        //ManagedObjectReference rootFolderRef = serviceContent.getRootFolder();
        ManagedObjectReference morHost = new ManagedObjectReference();
        morHost.setType("HostSystem");
        morHost.setValue("host-9");

        ManagedObjectReference propertyCollector = serviceContent.getPropertyCollector();

        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setType("ClusterComputeResource");
        propertySpec.setAll(Boolean.TRUE);

        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(morHost);
        objectSpec.setSkip(Boolean.FALSE);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getPropSet().add(propertySpec);
        propertyFilterSpec.getObjectSet().add(objectSpec);

        List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(1);
        listpfs.add(propertyFilterSpec);

        List<ObjectContent> objectContents = VimUtil.retrievePropertiesAllObjects(vimAuthenticationHelper.getVimPort(), propertyCollector, listpfs);
        return objectContents;
    }

    public RetrieveResult getDatastore() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        // Get references to the ViewManager and PropertyCollector
        ManagedObjectReference viewMgrRef = vimAuthenticationHelper.getServiceContent().getViewManager();
        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        // use a container view for virtual machines to define the traversal
        // - invoke the VimPortType method createContainerView (corresponds
        // to the ViewManager method) - pass the ViewManager MOR and
        // the other parameters required for the method invocation
        // - createContainerView takes a string[] for the type parameter;
        // declare an arraylist and add the type string to it
        List<String> vmList = new ArrayList<String>();
        vmList.add("VirtualMachine");

        ManagedObjectReference cViewRef = vimAuthenticationHelper.getVimPort().createContainerView(viewMgrRef,
                vimAuthenticationHelper.getServiceContent().getRootFolder(),
                vmList,
                true);

        // create an object spec to define the beginning of the traversal;
        // container view is the root object for this traversal
        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(cViewRef);
        oSpec.setSkip(true);

        // create a traversal spec to select all objects in the view
        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        // add the traversal spec to the object spec;
        // the accessor method (getSelectSet) returns a reference
        // to the mapped XML representation of the list; using this
        // reference to add the spec will update the list
        oSpec.getSelectSet().add(tSpec);

        // specify the property for retrieval (virtual machine name)
        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("VirtualMachine");
        pSpec.getPathSet().add("name");
        pSpec.getPathSet().add("datastoreBrowser");

        // create a PropertyFilterSpec and add the object and
        // property specs to it; use the getter method to reference
        // the mapped XML representation of the lists and add the specs
        // directly to the list
        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec);

        // Create a list for the filters and add the spec to it
        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        // get the data from the server
        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult props = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl,fSpecList,ro);

        return props;
    }
/*    private void getEnviromentBrowser() {
        RetrieveOptions propObjectRetrieveOpts = new RetrieveOptions();
        List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();
        ManagedObjectReference propertyCollector = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        RetrieveResult rslts = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propertyCollector,
                listpfs, propObjectRetrieveOpts);
        if (rslts != null && rslts.getObjects() != null
                && !rslts.getObjects().isEmpty()) {
            listobjcontent.addAll(rslts.getObjects());
        }
        String token = null;
        if (rslts != null && rslts.getToken() != null) {
            token = rslts.getToken();
        }
        while (token != null && !token.isEmpty()) {
            rslts = vimAuthenticationHelper.getVimPort().continueRetrievePropertiesEx(propCollectorRef,
                    token);
            token = null;
            if (rslts != null) {
                token = rslts.getToken();
                if (rslts.getObjects() != null
                        && !rslts.getObjects().isEmpty()) {
                    listobjcontent.addAll(rslts.getObjects());
                }
            }
        }
    }*/

    public RetrieveResult getBrowser() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference viewMgrRef = vimAuthenticationHelper.getServiceContent().getViewManager();
        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

        List<String> vmList = new ArrayList<String>();
        //vmList.add("VirtualMachine");
        vmList.add("HostSystem");

        ManagedObjectReference cViewRef =
                vimAuthenticationHelper.getVimPort().createContainerView(viewMgrRef,
                        vimAuthenticationHelper.getServiceContent().getRootFolder(),
                        vmList,
                        true);

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(cViewRef);
        oSpec.setSkip(true);

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        oSpec.getSelectSet().add(tSpec);

        TraversalSpec tSpecVmN = new TraversalSpec();
        tSpecVmN.setType("HostSystem");
        tSpecVmN.setPath("datastore");
        tSpecVmN.setSkip(false);

        TraversalSpec tSpecVmRp = new TraversalSpec();
        tSpecVmRp.setType("HostSystem");
        tSpecVmRp.setPath("network");
        tSpecVmRp.setSkip(false);

        tSpec.getSelectSet().add(tSpecVmN);
        tSpec.getSelectSet().add(tSpecVmRp);

        PropertySpec pSpec1 = new PropertySpec();
        pSpec1.setType("HostSystem");
        pSpec1.setAll(Boolean.TRUE);

        PropertySpec pSpec2 = new PropertySpec();
        pSpec2.setType("VirtualMachine");
        pSpec2.setAll(Boolean.TRUE);
        //pSpec.getPathSet().add("name");

        PropertySpec pSpecNs = new PropertySpec();
        pSpecNs.setType("Network");
        pSpecNs.getPathSet().add("summary.accessible");

//        PropertySpec pSpecRPr = new PropertySpec();
//        pSpecRPr.setType("ResourcePool");
//        pSpecRPr.getPathSet().add("runtime.cpu.maxUsage");
//        pSpecRPr.getPathSet().add("runtime.memory.maxUsage");
//        pSpecRPr.getPathSet().add("runtime.overallStatus");


        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec1);
        fSpec.getPropSet().add(pSpec2);
        //fSpec.getPropSet().add(pSpecNs);
        //fSpec.getPropSet().add(pSpecRPr);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult props = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl,fSpecList,ro);
        List<ObjectContent> objectContents = vimAuthenticationHelper.getVimPort().retrieveProperties(propColl, fSpecList);

        return props;
    }

    public RetrieveResult getHostDatastore(ManagedObjectReference mor) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference viewMgrRef = vimAuthenticationHelper.getServiceContent().getViewManager();
        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

//        ManagedObjectReference mor = new ManagedObjectReference();
//        mor.setType("HostSystem");
//        mor.setValue(hostId);

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(mor);
        oSpec.setSkip(false);

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        oSpec.getSelectSet().add(tSpec);

        TraversalSpec tSpecVmN = new TraversalSpec();
        tSpecVmN.setType("Datastore");
        tSpecVmN.setPath("name");
        tSpecVmN.setSkip(false);

        PropertySpec pSpec1 = new PropertySpec();
        pSpec1.setType("HostSystem");
        pSpec1.setAll(Boolean.TRUE);

        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec1);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl, fSpecList, ro);

        return retrieveResult;
    }

    public List<VirtualMachineConfigOptionDescriptor> queryConfigOptionDescriptor(ManagedObjectReference mor) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        ManagedObjectReference propColl = vimAuthenticationHelper.getServiceContent().getPropertyCollector();

/*        ManagedObjectReference mor = new ManagedObjectReference();
        mor.setType("ComputeResource");
        mor.setValue("domain-s7");*/

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(mor);
        oSpec.setSkip(false);

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        oSpec.getSelectSet().add(tSpec);

        PropertySpec pSpec1 = new PropertySpec();
        pSpec1.setType("ComputeResource");
        pSpec1.getPathSet().add("environmentBrowser");
        //pSpec1.setAll(Boolean.TRUE);

        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec1);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(propColl, fSpecList, ro);

        List<ObjectContent> objects = retrieveResult.getObjects();
        Optional<ObjectContent> first = objects.stream().findFirst();
        Optional<DynamicProperty> first1 = first.get().getPropSet().stream().findFirst();
        ManagedObjectReference managed = (ManagedObjectReference) first1.get().getVal();

        List<VirtualMachineConfigOptionDescriptor> virtualMachineConfigOptionDescriptors = vimAuthenticationHelper.getVimPort().queryConfigOptionDescriptor(managed);
        return virtualMachineConfigOptionDescriptors;
    }

    public VirtualMachineConfigOption getGuestMachineConfigOption(ManagedObjectReference morEnv, ManagedObjectReference morHost, String vmxId) throws RuntimeFaultFaultMsg {
/*        ManagedObjectReference mor1 = new ManagedObjectReference();
        mor1.setType("EnvironmentBrowser");
        mor1.setValue("envbrowser-7");

        ManagedObjectReference mor2 = new ManagedObjectReference();
        mor2.setType("HostSystem");
        mor2.setValue("host-9");*/
        VirtualMachineConfigOption virtualMachineConfigOption = vimAuthenticationHelper.getVimPort().queryConfigOption(morEnv, vmxId, morHost);

        return virtualMachineConfigOption;
    }

    public ManagedObjectReference getDatastoreSubFoldersTask() throws FileFaultFaultMsg, InvalidDatastoreFaultMsg, RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, InvalidCollectorVersionFaultMsg {
        ManagedObjectReference mor = new ManagedObjectReference();
        mor.setType("HostDatastoreBrowser");
        mor.setValue("datastoreBrowser-datastore-10");

        ManagedObjectReference managedObjectReference = vimAuthenticationHelper.getVimPort().searchDatastoreSubFoldersTask(mor, "[datastore1]", getHostDatastoreBrowserSearchSpec());
        ManagedObjectReference morTask = vimAuthenticationHelper.getVimPort().searchDatastoreTask(mor, "[datastore1]", getHostDatastoreBrowserSearchSpec());
        ServiceContent serviceContent = VimUtil.getServiceContent(vimAuthenticationHelper.getVimPort());

        Boolean aBoolean = TaskHelper.waitForTask(vimAuthenticationHelper, morTask.getValue());

        WaitForValues waitForValues = new WaitForValues(vimAuthenticationHelper.getVimPort(), serviceContent);
        boolean taskResultAfterDone = waitForValues.getTaskResultAfterDone(morTask);
        //HostDatastoreBrowserSearchResults
        return managedObjectReference;
    }

    public HostDatastoreBrowserSearchSpec getHostDatastoreBrowserSearchSpec() {
        VmDiskFileQueryFilter vdiskFilter = new VmDiskFileQueryFilter();
        VmDiskFileQuery fQuery = new VmDiskFileQuery();
        fQuery.setFilter(vdiskFilter);

        HostDatastoreBrowserSearchSpec searchSpec = new HostDatastoreBrowserSearchSpec();
        searchSpec.getQuery().add(fQuery);
        FileQueryFlags flag = new FileQueryFlags();
        flag.setFileOwner(true);
        flag.setFileSize(true);
        flag.setFileType(true);
        flag.setModification(true);
        searchSpec.setDetails(flag);
        //searchSpec.getMatchPattern().add(diskName + ".vmdk");

        return searchSpec;
    }
}