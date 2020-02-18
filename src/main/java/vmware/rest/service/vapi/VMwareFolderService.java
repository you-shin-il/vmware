package vmware.rest.service.vapi;

import com.vmware.vcenter.Folder;
import com.vmware.vcenter.FolderStub;
import com.vmware.vcenter.FolderTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareFolderService")
public class VMwareFolderService {
    @Autowired
    private Folder folderservice;

    public List<FolderTypes.Summary> list() {
        List<FolderTypes.Summary> list = folderservice.list(new FolderTypes.FilterSpec.Builder().build());
        list.stream().forEach(x -> {
            System.out.println(x.toString());
        });

        return list;
//        List<FolderTypes.Summary> list2 = folderStubservice.list(new FolderTypes.FilterSpec.Builder().build());
//        return list2;
    }

}