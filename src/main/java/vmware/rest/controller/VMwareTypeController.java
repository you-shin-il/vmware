package vmware.rest.controller;

import com.vmware.content.TypeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareTypeService;

import java.util.List;

@RestController
public class VMwareTypeController {
    @Autowired
    private VMwareTypeService vmwareTypeService;

    @GetMapping("/vmware/type/list.do")
    public List<TypeTypes.Info> list() {
        List<TypeTypes.Info> list = vmwareTypeService.list();
        return list;
    }

}