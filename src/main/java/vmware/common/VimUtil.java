package vmware.common;

import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;

import java.util.ArrayList;
import java.util.List;

public class VimUtil {
    public static List<ManagedObjectReference> filterManagedObjectReference(List<ObjectContent> objectContents) {
        List<ManagedObjectReference> managedObjectReferences = new ArrayList<ManagedObjectReference>();

        if (objectContents.isEmpty()) {
            return null;
        } else {
            for (ObjectContent objectContent : objectContents) {
                List<DynamicProperty> propSet = objectContent.getPropSet();
                managedObjectReferences.addAll(filterDynamicProperty(propSet));
            }
        }

        return managedObjectReferences;
    }

    public static List<ManagedObjectReference> filterDynamicProperty(List<DynamicProperty> dynamicPropertys) {
        List<ManagedObjectReference> managedObjectReferences = new ArrayList<ManagedObjectReference>();

        if (dynamicPropertys.isEmpty()) {
            return null;
        } else {
            for (DynamicProperty dynamicProperty : dynamicPropertys) {
                if (dynamicProperty.getVal() instanceof  ManagedObjectReference) {
                    managedObjectReferences.add((ManagedObjectReference)dynamicProperty.getVal());
                } else if (dynamicProperty.getVal() instanceof ArrayOfManagedObjectReference) {
                    ArrayOfManagedObjectReference arrayOfManagedObjectReference = (ArrayOfManagedObjectReference)dynamicProperty.getVal();
                    managedObjectReferences.addAll(arrayOfManagedObjectReference.getManagedObjectReference());
                }
            }
        }

        return managedObjectReferences;
    }
}