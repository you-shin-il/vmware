package vmware.rest.controller;

import com.vmware.content.library.ItemModel;
import com.vmware.content.library.ItemTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vmware.rest.service.vapi.VMwareItemService;

import java.util.List;

@RestController
public class VMwareItemController {
    @Autowired
    private VMwareItemService vmwareItemService;

    /**
     * libraryId 값으로 libraryItemId 목록 조회
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/item/itemIdList.do")
    public List<String> itemIdList(@RequestBody ItemTypes.FindSpec findSpec) {
        return vmwareItemService.getLibraryItemIds(findSpec);
    }

    /**
     * libraryId 값으로 libraryItemId 목록 조회
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/vmware/item/itemModelList.do")
    public List<ItemModel> itemModelList(@RequestBody ItemTypes.FindSpec findSpec) {
        return vmwareItemService.itemModelList(findSpec);
    }

}