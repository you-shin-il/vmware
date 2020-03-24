package vmware.vim;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;

import java.util.ArrayList;
import java.util.List;

public class VimDatacenter {
    private VimAuthenticationHelper vimAuthenticationHelper;
    private List<ManagedObjectReference> mors;

    public VimDatacenter(List<ManagedObjectReference> mors) {
        this.mors = mors;
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public List<ObjectContent> info(List<String> paths) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        PropertySpec propertySpec = setPropertySpec("Datacenter", paths);
        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();

        mors.stream().forEach(mor -> {
            ObjectSpec oSpec = new ObjectSpec();
            oSpec.setObj(mor);
            oSpec.setSkip(false);

            PropertyFilterSpec fSpec = new PropertyFilterSpec();
            fSpec.getObjectSet().add(oSpec);
            fSpec.getPropSet().add(propertySpec);

            fSpecList.add(fSpec);
        });

        List<ObjectContent> objectContents = getObjectContents(fSpecList);
        return objectContents;
    }

    public PropertySpec setPropertySpec(String type, List<String> path) {
        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setType(type);

        if (path == null) {
            propertySpec.setAll(true);
        } else {
            propertySpec.getPathSet().addAll(path);
        }

        return propertySpec;
    }

    public List<ObjectContent> getObjectContents(List<PropertyFilterSpec> fSpecList) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(vimAuthenticationHelper.getServiceContent().getPropertyCollector(), fSpecList, new RetrieveOptions());
        List<ObjectContent> objects = retrieveResult.getObjects();
        return objects;
    }
}