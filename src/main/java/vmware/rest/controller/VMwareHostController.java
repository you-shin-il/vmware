package vmware.rest.controller;

import com.vmware.vcenter.HostTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareHostService;

import java.util.List;

@RestController
public class VMwareHostController {
    @Autowired
    private VMwareHostService vmwareHostService;

    /**
     * libraryId 값으로 libraryItemId 목록 조회
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/host/list.do")
    public List<HostTypes.Summary> list(@RequestBody HostTypes.FilterSpec filterSpec) {
        return vmwareHostService.list(filterSpec);
    }

}