package vmware.rest.controller;

import com.vmware.vcenter.NetworkTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareNetworkService;

import java.util.List;

@RestController
public class VMwareNetworkController {
    @Autowired
    private VMwareNetworkService vmwareNetworkService;

    @GetMapping("/vmware/network/list.do")
    public List<NetworkTypes.Summary> list(@RequestBody NetworkTypes.FilterSpec filterSpec) {
        List<NetworkTypes.Summary> list = vmwareNetworkService.list(filterSpec);
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

}