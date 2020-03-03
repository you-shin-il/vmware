package vmware.rest.controller;

import com.vmware.content.library.item.FileTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareFileService;

import java.util.List;

@RestController
public class VMwareFileController {
    @Autowired
    private VMwareFileService vmwareFileService;

    @GetMapping("/vmware/file/get.do")
    public List<FileTypes.Info> get(@RequestParam String libraryItemId) {
        List<FileTypes.Info> list = vmwareFileService.list(libraryItemId);
        return list;
    }

}