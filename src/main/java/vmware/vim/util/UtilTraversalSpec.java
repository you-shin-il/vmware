package vmware.vim.util;

import com.vmware.vim25.TraversalSpec;

public class UtilTraversalSpec {

    public enum Type {
        ContainerView, Datacenter, Folder
    }

    public enum Path {
        view, vmFolder, hostFolder, childEntity
    }

    public TraversalSpec getTraversalSpec(Type type, Path path, Boolean skip) {
        TraversalSpec getContainerView_view = new TraversalSpec();
        getContainerView_view.setType(type.toString());
        getContainerView_view.setPath(path.toString());
        getContainerView_view.setSkip(skip);
        return getContainerView_view;
    }
}