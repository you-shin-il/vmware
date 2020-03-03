package vmware.rest.service.vapi;

import com.vmware.content.library.item.File;
import com.vmware.content.library.item.FileTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vmwareFileService")
public class VMwareFileService {
    @Autowired
    private File fileservice;

    /**
     * 파일 목록 조회
     * @param libraryItemId
     * @return
     */
    public List<FileTypes.Info> list(String libraryItemId) {
        List<FileTypes.Info> list = fileservice.list(libraryItemId);
        return list;
    }

}