package vmware.rest.service.vapi;

import com.vmware.vcenter.HostTypes;
import com.vmware.vstats.Metrics;
import com.vmware.vstats.MetricsTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareMetricsService")
public class VMwareMetricsService {
    @Autowired
    private Metrics metricsservice;

    public List<MetricsTypes.Summary> list() {
        List<MetricsTypes.Summary> list = metricsservice.list();
        return list;
    }

}