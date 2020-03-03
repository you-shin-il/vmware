package vmware.rest.controller;

import com.vmware.vcenter.ovf.LibraryItemTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareLibraryItemService;

@RestController
public class VMwareLibraryItemController {
    @Autowired
    private VMwareLibraryItemService vmwareLibraryItemService;

    @GetMapping("/vmware/libraryItem/filter.do")
    public LibraryItemTypes.OvfSummary filter(String ovfLibraryItemId, LibraryItemTypes.DeploymentTarget target) {
        LibraryItemTypes.OvfSummary filter = vmwareLibraryItemService.filter(ovfLibraryItemId, target);
        return filter;
    }

}