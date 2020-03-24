package vmware.rest.controller;

import com.vmware.vstats.MetricsTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareMetricsService;

import java.util.List;

@RestController
public class VMwareMetricsController {
    @Autowired
    private VMwareMetricsService vmwareMetricsServiceService;

    /**
     *
     * @return
     */
    @GetMapping("/vmware/metrics/list.do")
    public List<MetricsTypes.Summary> list() {
        List<MetricsTypes.Summary> list = vmwareMetricsServiceService.list();
        return list;
    }

}