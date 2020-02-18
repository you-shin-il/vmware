package vmware.rest.service.vapi;

import com.vmware.vcenter.Datastore;
import com.vmware.vcenter.DatastoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareDatastoreService")
public class VMwareDatastoreService {
    @Autowired
    private Datastore datasourceservice;

    public List<DatastoreTypes.Summary> list() {
        return datasourceservice.list(new DatastoreTypes.FilterSpec.Builder().build());
    }

}