package vmware.rest.controller;

import com.vmware.appliance.MonitoringTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareMonitoringService;

import java.util.List;

@RestController
public class VMwareMonitoringController {
    @Autowired
    private VMwareMonitoringService vmwareMonitoringServiceService;

    /**
     *
     * @return
     */
    @GetMapping("/vmware/monitoring/list.do")
    public List<MonitoringTypes.MonitoredItem> list() {
        List<MonitoringTypes.MonitoredItem> list = vmwareMonitoringServiceService.list();
        return list;
    }

    /**
     *
     * @return
     */
    @GetMapping("/vmware/monitoring/query.do")
    public List<MonitoringTypes.MonitoredItemData> query() {
        List<MonitoringTypes.MonitoredItemData> query = vmwareMonitoringServiceService.query();
        return query;
    }

}