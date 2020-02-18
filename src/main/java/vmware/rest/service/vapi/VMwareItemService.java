package vmware.rest.service.vapi;

import com.vmware.content.library.Item;
import com.vmware.content.library.ItemModel;
import com.vmware.content.library.ItemTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("vmwareItemService")
public class VMwareItemService {
    @Autowired
    private Item item;


    public List<String> getLibraryItemIds(ItemTypes.FindSpec findSpec) {
        return item.find(findSpec);
    }

    public ItemModel get(String libraryItemId) {
        return item.get(libraryItemId);
    }

    public List<ItemModel> itemModelList(ItemTypes.FindSpec findSpec)  {
        List<String> libraryItemIds = this.getLibraryItemIds(findSpec);
        return libraryItemIds.stream().map(id -> this.get(id)).collect(Collectors.toList());
    }

}