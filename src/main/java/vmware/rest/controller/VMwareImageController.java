package vmware.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareImageService;

@RestController
public class VMwareImageController {
    @Autowired
    private VMwareImageService vmwareImageService;

    @GetMapping("/vmware/image/mount.do")
    public String mount(String libraryItem, String vm) {
        String mount = vmwareImageService.mount(libraryItem, vm);
        return mount;
    }

}