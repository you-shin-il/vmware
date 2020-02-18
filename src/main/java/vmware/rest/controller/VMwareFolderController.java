package vmware.rest.controller;

import com.vmware.vcenter.FolderTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareFolderService;

import java.util.List;

@RestController
public class VMwareFolderController {
    @Autowired
    private VMwareFolderService vmwareFolderService;

    @GetMapping("/vmware/folder/list.do")
    public List<FolderTypes.Summary> list() {
        List<FolderTypes.Summary> list = vmwareFolderService.list();
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }
}