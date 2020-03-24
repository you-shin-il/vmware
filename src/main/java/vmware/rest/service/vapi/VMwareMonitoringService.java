package vmware.rest.service.vapi;

import com.vmware.appliance.Monitoring;
import com.vmware.appliance.MonitoringTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("vmwareMonitoringService")
public class VMwareMonitoringService {
    @Autowired
    private Monitoring monitoringservice;

    public List<MonitoringTypes.MonitoredItem> list() {
        List<MonitoringTypes.MonitoredItem> list = monitoringservice.list();
        return list;
    }

    public List<MonitoringTypes.MonitoredItemData> query() {
        MonitoringTypes.MonitoredItemDataRequest monitoredItemDataRequest = new MonitoringTypes.MonitoredItemDataRequest();
        monitoredItemDataRequest.setNames(Arrays.asList("cpu", "memory"));
        List<MonitoringTypes.MonitoredItemData> list = monitoringservice.query(monitoredItemDataRequest);
        return list;
    }

}