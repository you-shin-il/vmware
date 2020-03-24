package vmware.rest.service.vapi;

import com.vmware.vcenter.ovf.LibraryItem;
import com.vmware.vcenter.ovf.LibraryItemTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vmwareLibraryItemService")
public class VMwareLibraryItemService {
    @Autowired
    private LibraryItem libraryItemservice;

    public LibraryItemTypes.OvfSummary filter(String ovfLibraryItemId, LibraryItemTypes.DeploymentTarget target) {
        LibraryItemTypes.OvfSummary filter = libraryItemservice.filter(ovfLibraryItemId, target);
        return filter;
    }

    public LibraryItemTypes.CreateResult create(String ovfLibraryItemId, LibraryItemTypes.DeploymentTarget target) {
        LibraryItemTypes.CreateResult createResult = libraryItemservice.create("", null, null, null);
        return createResult;
    }

}