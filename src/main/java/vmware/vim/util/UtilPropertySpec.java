package vmware.vim.util;

import com.vmware.vim25.PropertySpec;

import java.util.List;

public class UtilPropertySpec {

    public enum Type {
        Datacenter, Folder, VirtualMachine, HostSystem
    }

/*    public enum Path {
        vmFolder, hostFolder, datastoreFolder, networkFolder, childEntity, name, guestHeartbeatStatus, overallStatus ,configStatus,
        parent, guestId, guestFullName, toolsStatus, hostName, ipAddress
    }*/

    public static final Boolean ALL_PATH_TRUE = Boolean.TRUE;
    public static final Boolean ALL_PATH_FALSE = Boolean.FALSE;

    public static PropertySpec createPropertySpec(Boolean all, Type type, List<String> paths) {
        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setType(type.toString());
        propertySpec.setAll(all);
        setPaths(propertySpec, paths);
        return propertySpec;
    }

    private static void setPaths(PropertySpec propertySpec, List<String> paths) {
        if(paths != null) {
            for (String path : paths) {
                propertySpec.getPathSet().add(path);
            }
        }
    }

}