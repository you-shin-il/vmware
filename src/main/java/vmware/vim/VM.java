package vmware.vim;

import com.vmware.vim25.*;

public class VM {
    private ManagedObjectReference obj;
    private Boolean alarmActionsEnabled;
    private ArrayOfCustomFieldDef availableField;
    private VirtualMachineCapability capability;
    private VirtualMachineConfigInfo config;
    private ArrayOfEvent configIssue;
    private ManagedEntityStatus configStatus;
    private ArrayOfCustomFieldValue customValue;
    private ArrayOfManagedObjectReference datastore;
    private ArrayOfAlarmState declaredAlarmState;
    private ArrayOfString disabledMethod;
    private ArrayOfInt effectiveRole;
    private ManagedObjectReference environmentBrowser;
    private GuestInfo guest;
    private ManagedEntityStatus guestHeartbeatStatus;
    private VirtualMachineFileLayout layout;
    private VirtualMachineFileLayoutEx layoutEx;
    private String name;
    private ArrayOfManagedObjectReference network;
    private ManagedEntityStatus overallStatus;
    private ManagedObjectReference parent;
    private ArrayOfPermission permission;
    private ArrayOfManagedObjectReference recentTask;
    private ResourceConfigSpec resourceConfig;
    private ManagedObjectReference resourcePool;
    private ArrayOfManagedObjectReference rootSnapshot;
    private VirtualMachineRuntimeInfo runtime;
    private VirtualMachineStorageInfo storage;
    private VirtualMachineSummary summary;
    private ArrayOfTag tag;
    private ArrayOfAlarmState triggeredAlarmState;
    private ArrayOfCustomFieldValue value;

    public VM() {}

    public VM(ObjectContent vmObjectContent) {
        this.obj = vmObjectContent.getObj();

        for(DynamicProperty dynamicProperty : vmObjectContent.getPropSet()) {
            if ("alarmActionsEnabled".equals(dynamicProperty.getName())) {
                alarmActionsEnabled = (Boolean)dynamicProperty.getVal();
            } else if("availableField".equals(dynamicProperty.getName())) {
                availableField = (ArrayOfCustomFieldDef) dynamicProperty.getVal();
            } else if("capability".equals(dynamicProperty.getName())) {
                capability = (VirtualMachineCapability) dynamicProperty.getVal();
            } else if("config".equals(dynamicProperty.getName())) {
                config = (VirtualMachineConfigInfo) dynamicProperty.getVal();
            } else if("configIssue".equals(dynamicProperty.getName())) {
                configIssue = (ArrayOfEvent) dynamicProperty.getVal();
            } else if("customValue".equals(dynamicProperty.getName())) {
                customValue = (ArrayOfCustomFieldValue) dynamicProperty.getVal();
            } else if("datastore".equals(dynamicProperty.getName())) {
                datastore = (ArrayOfManagedObjectReference) dynamicProperty.getVal();
            } else if("declaredAlarmState".equals(dynamicProperty.getName())) {
                declaredAlarmState = (ArrayOfAlarmState) dynamicProperty.getVal();
            } else if("disabledMethod".equals(dynamicProperty.getName())) {
                disabledMethod = (ArrayOfString) dynamicProperty.getVal();
            } else if("effectiveRole".equals(dynamicProperty.getName())) {
                effectiveRole = (ArrayOfInt) dynamicProperty.getVal();
            } else if("environmentBrowser".equals(dynamicProperty.getName())) {
                environmentBrowser = (ManagedObjectReference) dynamicProperty.getVal();
            } else if("guest".equals(dynamicProperty.getName())) {
                guest = (GuestInfo) dynamicProperty.getVal();
            } else if("guestHeartbeatStatus".equals(dynamicProperty.getName())) {
                guestHeartbeatStatus = (ManagedEntityStatus) dynamicProperty.getVal();
            } else if("layout".equals(dynamicProperty.getName())) {
                layout = (VirtualMachineFileLayout) dynamicProperty.getVal();
            } else if("layoutEx".equals(dynamicProperty.getName())) {
                layoutEx = (VirtualMachineFileLayoutEx) dynamicProperty.getVal();
            } else if("capability".equals(dynamicProperty.getName())) {
                capability = (VirtualMachineCapability) dynamicProperty.getVal();
            } else if("name".equals(dynamicProperty.getName())) {
                name = (String) dynamicProperty.getVal();
            } else if("network".equals(dynamicProperty.getName())) {
                network = (ArrayOfManagedObjectReference) dynamicProperty.getVal();
            } else if("overallStatus".equals(dynamicProperty.getName())) {
                overallStatus = (ManagedEntityStatus) dynamicProperty.getVal();
            } else if("parent".equals(dynamicProperty.getName())) {
                parent = (ManagedObjectReference) dynamicProperty.getVal();
            } else if("permission".equals(dynamicProperty.getName())) {
                permission = (ArrayOfPermission) dynamicProperty.getVal();
            } else if("recentTask".equals(dynamicProperty.getName())) {
                recentTask = (ArrayOfManagedObjectReference) dynamicProperty.getVal();
            } else if("resourceConfig".equals(dynamicProperty.getName())) {
                resourceConfig = (ResourceConfigSpec) dynamicProperty.getVal();
            } else if("resourcePool".equals(dynamicProperty.getName())) {
                resourcePool = (ManagedObjectReference) dynamicProperty.getVal();
            } else if("rootSnapshot".equals(dynamicProperty.getName())) {
                rootSnapshot = (ArrayOfManagedObjectReference) dynamicProperty.getVal();
            } else if("runtime".equals(dynamicProperty.getName())) {
                runtime = (VirtualMachineRuntimeInfo) dynamicProperty.getVal();
            } else if("storage".equals(dynamicProperty.getName())) {
                storage = (VirtualMachineStorageInfo) dynamicProperty.getVal();
            } else if("summary".equals(dynamicProperty.getName())) {
                summary = (VirtualMachineSummary) dynamicProperty.getVal();
            } else if("tag".equals(dynamicProperty.getName())) {
                tag = (ArrayOfTag) dynamicProperty.getVal();
            } else if("triggeredAlarmState".equals(dynamicProperty.getName())) {
                triggeredAlarmState = (ArrayOfAlarmState) dynamicProperty.getVal();
            } else if("value".equals(dynamicProperty.getName())) {
                value = (ArrayOfCustomFieldValue) dynamicProperty.getVal();
            }
        }
    }

    public ManagedObjectReference getObj() {
        return obj;
    }

    public Boolean getAlarmActionsEnabled() {
        return alarmActionsEnabled;
    }

    public ArrayOfCustomFieldDef getAvailableField() {
        return availableField;
    }

    public VirtualMachineCapability getCapability() {
        return capability;
    }

    public VirtualMachineConfigInfo getConfig() {
        return config;
    }

    public ArrayOfEvent getConfigIssue() {
        return configIssue;
    }

    public ManagedEntityStatus getConfigStatus() {
        return configStatus;
    }

    public ArrayOfCustomFieldValue getCustomValue() {
        return customValue;
    }

    public ArrayOfManagedObjectReference getDatastore() {
        return datastore;
    }

    public ArrayOfAlarmState getDeclaredAlarmState() {
        return declaredAlarmState;
    }

    public ArrayOfString getDisabledMethod() {
        return disabledMethod;
    }

    public ArrayOfInt getEffectiveRole() {
        return effectiveRole;
    }

    public ManagedObjectReference getEnvironmentBrowser() {
        return environmentBrowser;
    }

    public GuestInfo getGuest() {
        return guest;
    }

    public ManagedEntityStatus getGuestHeartbeatStatus() {
        return guestHeartbeatStatus;
    }

    public VirtualMachineFileLayout getLayout() {
        return layout;
    }

    public VirtualMachineFileLayoutEx getLayoutEx() {
        return layoutEx;
    }

    public String getName() {
        return name;
    }

    public ArrayOfManagedObjectReference getNetwork() {
        return network;
    }

    public ManagedEntityStatus getOverallStatus() {
        return overallStatus;
    }

    public ManagedObjectReference getParent() {
        return parent;
    }

    public ArrayOfPermission getPermission() {
        return permission;
    }

    public ArrayOfManagedObjectReference getRecentTask() {
        return recentTask;
    }

    public ResourceConfigSpec getResourceConfig() {
        return resourceConfig;
    }

    public ManagedObjectReference getResourcePool() {
        return resourcePool;
    }

    public ArrayOfManagedObjectReference getRootSnapshot() {
        return rootSnapshot;
    }

    public VirtualMachineRuntimeInfo getRuntime() {
        return runtime;
    }

    public VirtualMachineStorageInfo getStorage() {
        return storage;
    }

    public VirtualMachineSummary getSummary() {
        return summary;
    }

    public ArrayOfTag getTag() {
        return tag;
    }

    public ArrayOfAlarmState getTriggeredAlarmState() {
        return triggeredAlarmState;
    }

    public ArrayOfCustomFieldValue getValue() {
        return value;
    }
}