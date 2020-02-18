package vmware.rest.service.vim;

import com.vmware.vcenter.Cluster;
import com.vmware.vcenter.ClusterTypes;
import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vmware.common.authentication.VimAuthenticationHelper;
import vmware.common.helpers.VimUtil;

import java.util.ArrayList;
import java.util.List;

@Service("vmwareVIMService")
public class VMwareVIMService {
    @Autowired
    private VimAuthenticationHelper vimAuthenticationHelper;
    @Autowired
    private Cluster clusterservice;

    public void test() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg, NotFoundFaultMsg {
        VirtualMachineConfigOption virtualMachineConfigOption1 = this.queryConfigOption();
        VirtualMachineConfigOption virtualMachineConfigOption2 = this.queryConfigOptionEx();
        List<ManagedObjectReference> hosts = getHosts();
        List<ObjectContent> entityByName = getEntityByName();
        ManagedObjectReference m1 = new ManagedObjectReference();
        m1.setType("HostSystem");
        m1.setValue("host-9");

        ManagedObjectReference m2 = new ManagedObjectReference();
        m2.setType("EnvironmentBrowser");
        m2.setValue("envbrowser-7");

        VirtualMachineConfigOption virtualMachineConfigOption = vimAuthenticationHelper.getVimPort().queryConfigOption(m1, "vmx-14", m2);
        //ManagedObjectReference cluster = this.getCluster();
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
}