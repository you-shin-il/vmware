package vmware.vim.util;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;

import java.util.ArrayList;
import java.util.List;

public class VimRetrievePropertiesExUtil {
    private VimAuthenticationHelper vimAuthenticationHelper;
    private List<ManagedObjectReference> mors;
    private List<PropertyFilterSpec> fSpecList;

    public VimRetrievePropertiesExUtil() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public void setPropertyFilterSpec(String type, List<String> paths) {
        PropertySpec propertySpec = setPropertySpec(type, paths);
        fSpecList = new ArrayList<PropertyFilterSpec>();

        mors.stream().forEach(mor -> {
            ObjectSpec oSpec = new ObjectSpec();
            oSpec.setObj(mor);
            oSpec.setSkip(false);

            PropertyFilterSpec fSpec = new PropertyFilterSpec();
            fSpec.getObjectSet().add(oSpec);
            fSpec.getPropSet().add(propertySpec);

            fSpecList.add(fSpec);
        });
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

    public List<ObjectContent> retrievePropertiesEx() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        RetrieveResult retrieveResult = vimAuthenticationHelper.getVimPort().retrievePropertiesEx(vimAuthenticationHelper.getServiceContent().getPropertyCollector(), fSpecList, new RetrieveOptions());
        List<ObjectContent> objects = retrieveResult.getObjects();
        //vimAuthenticationHelper.logout();
        return objects;
    }

    public List<ManagedObjectReference> getMors() {
        return mors;
    }

    public void setMors(List<ManagedObjectReference> mors) {
        this.mors = mors;
    }

    public List<ObjectContent> retrieveProperties(ManagedObjectReference mor, List<PropertyFilterSpec> specs) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        List<ObjectContent> objectContents = vimAuthenticationHelper.getVimPort().retrieveProperties(mor, specs);
        return objectContents;
    }
}