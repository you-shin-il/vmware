package vmware.rest.controller;

import com.vmware.vcenter.ResourcePoolTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareResourcePoolService;

import java.util.List;

@RestController
public class VMwareResourcePoolController {
    @Autowired
    private VMwareResourcePoolService vmwareResourcePoolService;

    @GetMapping("/vmware/resourcePool/list.do")
    public List<ResourcePoolTypes.Summary> list() {
        List<ResourcePoolTypes.Summary> list = vmwareResourcePoolService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }
}