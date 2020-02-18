package vmware.rest.controller;

import com.vmware.vcenter.guest.CustomizationSpecsTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareCustomizationSpecService;

import java.util.List;

@RestController
public class VMwareCustomizationSpecController {
    @Autowired
    private VMwareCustomizationSpecService vmwareCustomizationSpecService;

    @GetMapping("/vmware/customizationSpec/list.do")
    public List<CustomizationSpecsTypes.Summary> list() {
        List<CustomizationSpecsTypes.Summary> list = vmwareCustomizationSpecService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }
}