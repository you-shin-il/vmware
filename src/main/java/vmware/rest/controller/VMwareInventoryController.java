package vmware.rest.controller;

import com.vmware.vcenter.inventory.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareInventoryService;

import java.util.Map;

@RestController
public class VMwareInventoryController {
    @Autowired
    private VMwareInventoryService vmwareInventoryService;

    @GetMapping("/vmware/inventory/find.do")
    public Map<String, DatastoreTypes.Info> find() {
        Map<String, DatastoreTypes.Info> map = vmwareInventoryService.find();
        System.out.println("======");
        System.out.println(map.toString());
        System.out.println("======");

        return map;
    }
}