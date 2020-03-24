package vmware.vim.summary;

import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ObjectContent;

import java.util.ArrayList;
import java.util.List;

public class Datastore {
    private String datastoreId;
    private Storage storage;

    public Datastore(String datastoreId, Storage storage) {
        this.datastoreId = datastoreId;
        this.storage = storage;
    }

    public String getDatastoreId() {
        return datastoreId;
    }

    public void setDatastoreId(String datastoreId) {
        this.datastoreId = datastoreId;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public static List<Datastore> newDatastore(List<ObjectContent> objects) {
        List<Datastore> datastores = new ArrayList();

        for(ObjectContent objs : objects) {
            if(!objs.getPropSet().isEmpty()) {
                Storage storage = new Storage();

                for(DynamicProperty dynamicProperty : objs.getPropSet()) {
                    if (Storage.SUMMARY_CAPACITY.equals(dynamicProperty.getName())) {
                        storage.setStorage_summaryCapacity((Long) dynamicProperty.getVal());
                    } else if(Storage.SUMMARY_FREESPACE.equals(dynamicProperty.getName())) {
                        storage.setStorage_summaryFreeSpace((Long) dynamicProperty.getVal());
                    }
                }
                datastores.add(new Datastore(objs.getObj().getValue(), storage));
            }
        }
        return datastores;
    }
}