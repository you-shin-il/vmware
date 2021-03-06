package vmware.rest.service.vapi;

import com.vmware.vcenter.ResourcePool;
import com.vmware.vcenter.ResourcePoolTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareResourcePoolService")
public class VMwareResourcePoolService {
    @Autowired
    private ResourcePool resourcPoolservice;

    public List<ResourcePoolTypes.Summary> list(ResourcePoolTypes.FilterSpec spec) {
        return resourcPoolservice.list(spec);
    }

    public ResourcePoolTypes.Info info(String resourcePool) {
        ResourcePoolTypes.Info info = resourcPoolservice.get(resourcePool);
        return info;
    }
}