package vmware.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.vim.VMTemplateCreate;

@RestController
public class VirtualMachineConfigSpecController {

    @PostMapping("/virtualMachineConfigSpec.do")
    public void virtualMachineConfigSpec(@RequestBody VMTemplateCreate template) {
        System.out.println("virtualMachineConfigSpec");
    }
}