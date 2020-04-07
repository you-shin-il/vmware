package vmware.vim.util;

import com.vmware.vim25.TraversalSpec;

public class UtilTraversalSpec {

    public enum Type {
        ContainerView, Datacenter, Folder, ClusterComputeResource, HostSystem, VirtualMachine
    }

    public enum Path {
        view, vmFolder, hostFolder, childEntity, host, vm
    }

    public static TraversalSpec createTraversalSpec(Boolean skip, Type type, Path path) {
        TraversalSpec traversalSpec = new TraversalSpec();
        traversalSpec.setType(type.toString());
        traversalSpec.setPath(path.toString());
        traversalSpec.setSkip(skip);
        return traversalSpec;
    }
}