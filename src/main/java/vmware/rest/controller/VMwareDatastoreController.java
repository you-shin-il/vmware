package vmware.rest.controller;

import com.vmware.vcenter.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareDatastoreService;

import java.util.List;

@RestController
public class VMwareDatastoreController {
    @Autowired
    private VMwareDatastoreService vmwareDatastoreService;

    @GetMapping("/vmware/datastore/list.do")
    public List<DatastoreTypes.Summary> list() {
        List<DatastoreTypes.Summary> list = vmwareDatastoreService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }
}