package vmware.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareSubscribedLibraryService;

import java.util.List;

@RestController
public class VMwareSubscribedLibraryController {
    @Autowired
    private VMwareSubscribedLibraryService vmwareSubscribedLibraryService;

    @GetMapping("/vmware/SubscribedLibrary/list.do")
    public List<String> list() {
        List<String> list = vmwareSubscribedLibraryService.list();
        return list;
    }

}