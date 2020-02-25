package vmware.rest.controller;

import com.vmware.vcenter.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareDatastoreService;

import java.util.List;

@RestController
public class VMwareDatastoreController {
    @Autowired
    private VMwareDatastoreService vmwareDatastoreService;

    @GetMapping("/vmware/datastore/list.do")
    public List<DatastoreTypes.Summary> list(@RequestBody DatastoreTypes.FilterSpec filterSpec) {
        List<DatastoreTypes.Summary> list = vmwareDatastoreService.list(filterSpec);
        return list;
    }

    @GetMapping("/vmware/datastore/info.do")
    public DatastoreTypes.Info info(@RequestParam String datastoreId) {
        DatastoreTypes.Info info = vmwareDatastoreService.info(datastoreId);
        return info;
    }

}