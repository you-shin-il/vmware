package vmware.rest.controller;

import com.vmware.vcenter.VMTypes;
import com.vmware.vcenter.vm.GuestOSFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareVMService;

import java.util.List;

@RestController
public class VMwareVMController {
    @Autowired
    private VMwareVMService vmwareVMService;

    @GetMapping("/vmware/vm/list.do")
    public List<VMTypes.Summary> list() {
        List<VMTypes.Summary> list = vmwareVMService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

    @GetMapping("/vmware/vm/guestOSList.do")
    public void guestOSList() {
        GuestOSFamily[] values = GuestOSFamily.values();
        GuestOSFamily other = GuestOSFamily.OTHER;
        GuestOSFamily.Values enumValue = other.getEnumValue();
        System.out.println("=========");
    }

    public void guestOSVersionList() {

    }
}