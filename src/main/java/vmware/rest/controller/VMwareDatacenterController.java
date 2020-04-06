package vmware.rest.controller;

import com.vmware.vcenter.DatacenterTypes;
import com.vmware.vcenter.DatastoreTypes;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vmware.rest.service.vapi.VMwareDatacenterService;
import vmware.rest.service.vapi.VMwareDatastoreService;
import vmware.vim.VMwareVimVMService;

import java.util.List;

@RestController
public class VMwareDatacenterController {
    @Autowired
    private VMwareDatacenterService vmwareDatacenterService;

    @GetMapping("/vmware/datacenter/get.do")
    public DatacenterTypes.Info get(@RequestParam String name) {
        DatacenterTypes.Info info = vmwareDatacenterService.get(name);
        System.out.println("======");
        System.out.println(info.toString());
        System.out.println("======");

        return info;
    }

    @GetMapping("/vmware/datacenter/list.do")
    public List<DatacenterTypes.Summary> list(@RequestBody DatacenterTypes.FilterSpec filterSpec) {
        List<DatacenterTypes.Summary> list = vmwareDatacenterService.list(filterSpec);
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

    @GetMapping("/vmware/summary/datacenter/test.do")
    public void test() throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        VMwareVimVMService vmwareVimVMService = new VMwareVimVMService();
        vmwareVimVMService.datastoreFolderMorTest();
    }
}