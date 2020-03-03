package vmware.rest.controller;

import com.vmware.vcenter.inventory.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareDatastoreInventoryService;

import java.util.List;
import java.util.Map;

@RestController
public class VMwareDatastoreInventoryController {
    @Autowired
    private VMwareDatastoreInventoryService vmwareDatastoreInventoryService;

    @PostMapping("/vmware/datastore/inventory/list.do")
    public Map<String, DatastoreTypes.Info> list(@RequestBody List<String> datastore) {
        Map<String, com.vmware.vcenter.inventory.DatastoreTypes.Info> list = vmwareDatastoreInventoryService.list(datastore);
        return list;
    }

}