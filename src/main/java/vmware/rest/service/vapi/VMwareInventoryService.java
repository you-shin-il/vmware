package vmware.rest.service.vapi;

import com.vmware.vcenter.inventory.Datastore;
import com.vmware.vcenter.inventory.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service("vmwareInventoryService")
public class VMwareInventoryService {
    @Autowired
    private Datastore inventoryservice;

    public Map<String, DatastoreTypes.Info> find() {
        return inventoryservice.find(Arrays.asList("datastore-10"));
    }

}