package vmware.rest.controller;

import com.vmware.content.library.item.StorageTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareStorageService;

import java.util.List;

@RestController
public class VMwareStorageController {
    @Autowired
    private VMwareStorageService vmwareStorageService;

    @GetMapping("/vmware/content/library/item/storage/list.do")
    public List<StorageTypes.Info> list(String name) {
        List<StorageTypes.Info> list = vmwareStorageService.list(name);
        System.out.println("======");
        System.out.println(list.toString());
        System.out.println("======");

        return list;
    }

}