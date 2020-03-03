package vmware.rest.service.vapi;

import com.vmware.vcenter.inventory.Datastore;
import com.vmware.vcenter.inventory.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("vmwareDatastoreInventoryService")
public class VMwareDatastoreInventoryService {
    @Autowired
    private Datastore datastoreInventoryservice;

    public Map<String, DatastoreTypes.Info> list(List<String> datastores) {
        Map<String, DatastoreTypes.Info> stringInfoMap = datastoreInventoryservice.find(datastores);
        return stringInfoMap;
    }

}