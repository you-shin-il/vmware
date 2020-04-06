package vmware.rest.controller;

import com.vmware.vcenter.vm.HardwareTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareHardwareService;

@RestController
public class VMwareHardwareController {
    @Autowired
    private VMwareHardwareService vmwareHardwareService;

    @GetMapping("/vmware/hardware/info.do")
    public HardwareTypes.Info info(@RequestParam String vmId) {
        HardwareTypes.Info info = vmwareHardwareService.info(vmId);
        System.out.println("======");
        System.out.println(info.toString());
        System.out.println("======");

        return info;
    }

}