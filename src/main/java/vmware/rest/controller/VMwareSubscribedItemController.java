package vmware.rest.controller;

import com.vmware.content.TypeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareSubscribeItemService;

import java.util.List;

@RestController
public class VMwareSubscribedItemController {
    @Autowired
    private VMwareSubscribeItemService vmwareSubscribeItemService;

    @GetMapping("/vmware/subscribeItem/list.do")
    public List<TypeTypes.Info> list() {
        List<TypeTypes.Info> list = vmwareSubscribeItemService.list();
        return list;
    }

}