package vmware.vim;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;

import java.util.*;

public class GetPerfStats {
    private static final ManagedObjectReference ROOT_FOLDER;
    private Map<Integer, PerfCounterInfo> counterInfoMap = new HashMap<>();
    private Map<String, Integer> counters = new HashMap<>();
    private VimAuthenticationHelper vimAuthenticationHelper;

    static {
        ROOT_FOLDER = new ManagedObjectReference();
        ROOT_FOLDER.setType("Folder");
        ROOT_FOLDER.setValue("group-v3");
    }

    public GetPerfStats() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");
    }

    public void run() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        String entityName = "Windows10 PRO template";
        String perfCounter = "20";
        int intervalId = 20;

        ManagedObjectReference entityMor = null;
        if (true) {
            entityMor = getVMByName(entityName);
        } else {
            //entityMor = getHostByName(entityName);
        }

        getPertCounters();

        PerfProviderSummary perfProviderSummary = getPerfProviderSummary(entityMor);

        PerfQuerySpec pqs = getPerfQuerySpec(entityMor, perfCounter, intervalId);

        List<PerfEntityMetricBase> perfStats = getPerfStats(Arrays.asList(pqs));
    }

    public ManagedObjectReference getVMByName(String vmName) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        ManagedObjectReference retVal = null;
        TraversalSpec tSpec = getVMTraversalSpec();

        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setAll(Boolean.FALSE);
        propertySpec.getPathSet().add("name");
        propertySpec.setType("VirtualMachine");
        PropertySpec[] propertySpecs = new PropertySpec[] {propertySpec};

        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(ROOT_FOLDER);
        objectSpec.setSkip(true);
        objectSpec.getSelectSet().add(tSpec);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getPropSet().add(propertySpec);
        propertyFilterSpec.getObjectSet().add(objectSpec);

        List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<>();
        propertyFilterSpecs.add(propertyFilterSpec);

        List<ObjectContent> oCont = vimAuthenticationHelper.getVimPort().retrieveProperties(vimAuthenticationHelper.getServiceContent().getPropertyCollector(), propertyFilterSpecs);
        if (oCont != null) {
            for (ObjectContent oc : oCont) {
                ManagedObjectReference mr = oc.getObj();
                String vmnm = null;
                List<DynamicProperty> dps = oc.getPropSet();
                if (dps != null) {
                    for (DynamicProperty dp : dps) {
                        vmnm = (String) dp.getVal();
                    }
                }

                if (vmnm != null && vmnm.equals(vmName)) {
                    retVal = mr;
                    System.out.println("MOR Type : " + mr.getType());
                    System.out.println("VM Name : " + vmnm);
                    break;
                }
            }
        }
        System.out.println("getVMByName end");
        return retVal;
    }

    private void getPertCounters() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        PropertySpec propertySpec = new PropertySpec();
        propertySpec.setAll(Boolean.FALSE);
        propertySpec.getPathSet().add("perfCounter");
        propertySpec.setType("PerformanceManager");

        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(vimAuthenticationHelper.getServiceContent().getPerfManager());

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getPropSet().add(propertySpec);
        propertyFilterSpec.getObjectSet().add(objectSpec);

        List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<>();
        propertyFilterSpecs.add(propertyFilterSpec);

        List<ObjectContent> oCont = vimAuthenticationHelper.getVimPort().retrieveProperties(vimAuthenticationHelper.getServiceContent().getPropertyCollector(), propertyFilterSpecs);
        if (oCont != null) {
            for (ObjectContent oc : oCont) {
                List<DynamicProperty> dps = oc.getPropSet();
                if (dps != null) {
                    for (DynamicProperty dp : dps) {
                        List<PerfCounterInfo> pciArr = ((ArrayOfPerfCounterInfo) dp.getVal()).getPerfCounterInfo();
                        for(PerfCounterInfo pci : pciArr) {
                            String fullCounter = pci.getGroupInfo().getKey() + " ," + pci.getNameInfo().getKey() + " ," + pci.getRollupType().value();
                            counterInfoMap.put(pci.getKey(), pci);
                            counters.put(fullCounter, pci.getKey());
                        }
                    }
                }
            }
        }
        System.out.println("getPertCounters end");
    }

    public PerfProviderSummary getPerfProviderSummary(ManagedObjectReference entityMor) throws RuntimeFaultFaultMsg {
        PerfProviderSummary retVal = null;
        retVal = vimAuthenticationHelper.getVimPort().queryPerfProviderSummary(vimAuthenticationHelper.getServiceContent().getPerfManager(), entityMor);
        return retVal;
    }

    public List<PerfEntityMetricBase> getPerfStats(List<PerfQuerySpec> pqsArr) throws RuntimeFaultFaultMsg {
        List<PerfEntityMetricBase> perfEntityMetricBases = vimAuthenticationHelper.getVimPort().queryPerf(vimAuthenticationHelper.getServiceContent().getPerfManager() ,pqsArr);
        return perfEntityMetricBases;
    }

    public PerfQuerySpec getPerfQuerySpec() {
        return null;
    }

    public static TraversalSpec getVMTraversalSpec() {
        // Create a traversal spec that starts from the 'root' objects
        // and traverses the inventory tree to get to the VirtualMachines.
        // Build the traversal specs bottoms up

        // Traversal to get to the vmFolder from DataCenter
        TraversalSpec dataCenterToVMFolder = new TraversalSpec();
        dataCenterToVMFolder.setName("DataCenterToVMFolder");
        dataCenterToVMFolder.setType("Datacenter");
        dataCenterToVMFolder.setPath("vmFolder");
        dataCenterToVMFolder.setSkip(false);
        SelectionSpec sSpec = new SelectionSpec();
        sSpec.setName("VisitFolders");
        dataCenterToVMFolder.getSelectSet().add(sSpec);

        // TraversalSpec to get to the DataCenter from rootFolder
        TraversalSpec traversalSpec = new TraversalSpec();
        traversalSpec.setName("VisitFolders");
        traversalSpec.setType("Folder");
        traversalSpec.setPath("childEntity");
        traversalSpec.setSkip(false);
        traversalSpec.getSelectSet().add(sSpec);
        traversalSpec.getSelectSet().add(dataCenterToVMFolder);

        return traversalSpec;
    }

    public PerfQuerySpec getPerfQuerySpec(ManagedObjectReference entityMor, String perfCounter, int intervalId) {
        PerfQuerySpec retVal = null;
        PerfMetricId pmid = new PerfMetricId();
        pmid.setInstance("*");
        pmid.setCounterId(intervalId);

        PerfQuerySpec pqSpec = new PerfQuerySpec();
        pqSpec.setIntervalId(Integer.valueOf(perfCounter));
        pqSpec.setMaxSample(1);
        pqSpec.getMetricId().add(pmid);

        pqSpec.setFormat(PerfFormat.CSV.value());

        pqSpec.setEntity(entityMor);

        return pqSpec;
    }
}