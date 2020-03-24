package vmware.rest.controller;

import com.vmware.vcenter.ResourcePoolTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareResourcePoolService;

import java.util.List;

@RestController
public class VMwareResourcePoolController {
    @Autowired
    private VMwareResourcePoolService vmwareResourcePoolService;

    @GetMapping("/vmware/resourcePool/list.do")
    public List<ResourcePoolTypes.Summary> list(@RequestBody ResourcePoolTypes.FilterSpec spec) {
        List<ResourcePoolTypes.Summary> list = vmwareResourcePoolService.list(spec);
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

    @GetMapping("/vmware/resourcePool/info.do")
    public ResourcePoolTypes.Info info(String resourcePool) {
        ResourcePoolTypes.Info info = vmwareResourcePoolService.info(resourcePool);
        return info;
    }
}