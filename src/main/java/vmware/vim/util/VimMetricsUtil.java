package vmware.vim.util;

import com.vmware.vim25.*;
import vmware.common.authentication.VimAuthenticationHelper;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class VimMetricsUtil {
    private VimAuthenticationHelper vimAuthenticationHelper;
    ManagedObjectReference perfMgr = null;
    ManagedObjectReference hostmor = null;
    List<PerfMetricId> pmidlist = null;
    List<PerfCounterInfo> pcinfolist = null;

    public VimMetricsUtil() {
        vimAuthenticationHelper = new VimAuthenticationHelper();
        vimAuthenticationHelper.loginByUsernameAndPassword("192.168.50.22", "administrator@vsphere.local", "admin123!Q");

        hostmor = new ManagedObjectReference();
        hostmor.setType("HostSystem");
        hostmor.setValue("host-9");

        perfMgr = vimAuthenticationHelper.getServiceContent().getPerfManager();
    }

    public void metricsTest() throws RuntimeFaultFaultMsg, DatatypeConfigurationException {

        PerfProviderSummary perfProviderSummary =
                vimAuthenticationHelper.getVimPort().queryPerfProviderSummary(perfMgr, hostmor);
        pmidlist =
                vimAuthenticationHelper.getVimPort().queryAvailablePerfMetric(perfMgr, hostmor, null, null,
                        perfProviderSummary.getRefreshRate());

        List<Integer> idslist = new ArrayList<Integer>();

        for (int i = 0; i != pmidlist.size(); ++i) {
            idslist.add(pmidlist.get(i).getCounterId());
        }

        pcinfolist =
                vimAuthenticationHelper.getVimPort().queryPerfCounter(perfMgr, idslist);
        System.out.println("Available real-time metrics for host ("
                + pmidlist.size() + "):");
        System.out.println("--------------------------");
        for (int i = 0; i != pmidlist.size(); ++i) {
            String label = pcinfolist.get(i).getNameInfo().getLabel();
            String instance = pmidlist.get(i).getInstance();
            System.out.print("   " + label);
            if (instance.length() != 0) {
                System.out.print(" [" + instance + "]");
            }
            System.out.println();
        }
        System.out.println("metricsTest");

        getQuerySummary(perfMgr, hostmor, vimAuthenticationHelper.getVimPort());
        getQueryAvailable(perfMgr, hostmor, vimAuthenticationHelper.getVimPort());
        List<PerfQuerySpec> perfQuerySpec = getPerfQuerySpec(pmidlist);
        List<PerfEntityMetricBase> perfEntityMetricBases = vimAuthenticationHelper.getVimPort().queryPerf(vimAuthenticationHelper.getServiceContent().getPerfManager(), perfQuerySpec);
        //List<PerfMetricId> perfMetricIds = vimAuthenticationHelper.getVimPort().queryAvailablePerfMetric(perfMgr, hostmor,null,null, null);

        //ManagedObjectReference perfManager = vimAuthenticationHelper.getServiceContent().getPerfManager();
        //PerfCompositeMetric metric = vimAuthenticationHelper.getVimPort().queryPerfComposite(perfManager, querySpec);
    }

    void displayBasics() throws RuntimeFaultFaultMsg, DatatypeConfigurationException {
    }

    void getQuerySummary(ManagedObjectReference perfMgr,
                         ManagedObjectReference hostmor, VimPortType service)
            throws RuntimeFaultFaultMsg {
        PerfProviderSummary summary =
                service.queryPerfProviderSummary(perfMgr, hostmor);
        System.out.println("Host perf capabilities:");
        System.out.println("----------------------");
        System.out
                .println("  Summary supported: " + summary.isSummarySupported());
        System.out
                .println("  Current supported: " + summary.isCurrentSupported());
        if (summary.isCurrentSupported()) {
            System.out.println("  Current refresh rate: "
                    + summary.getRefreshRate());
        }
        System.out.println();
    }

    void getQueryAvailable(ManagedObjectReference perfMgr,
                           ManagedObjectReference hostmor, VimPortType service)
            throws DatatypeConfigurationException, RuntimeFaultFaultMsg {

        PerfProviderSummary perfProviderSummary =
                service.queryPerfProviderSummary(perfMgr, hostmor);
        List<PerfMetricId> pmidlist =
                service.queryAvailablePerfMetric(perfMgr, hostmor, null, null,
                        perfProviderSummary.getRefreshRate());

        List<Integer> idslist = new ArrayList<Integer>();

        for (int i = 0; i != pmidlist.size(); ++i) {
            idslist.add(pmidlist.get(i).getCounterId());
        }

        List<PerfCounterInfo> pcinfolist =
                service.queryPerfCounter(perfMgr, idslist);
        System.out.println("Available real-time metrics for host ("
                + pmidlist.size() + "):");
        System.out.println("--------------------------");
        for (int i = 0; i != pmidlist.size(); ++i) {
            String label = pcinfolist.get(i).getNameInfo().getLabel();
            String instance = pmidlist.get(i).getInstance();
            System.out.print("   " + label);
            if (instance.length() != 0) {
                System.out.print(" [" + instance + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    void getIntervals(ManagedObjectReference perfMgr, VimPortType service) throws InvalidPropertyFaultMsg,
            RuntimeFaultFaultMsg {
/*        Object property = getMOREFs.entityProps(perfMgr, new String[] { "historicalInterval" })
                .get("historicalInterval");
        ArrayOfPerfInterval arrayInterval = (ArrayOfPerfInterval) property;
        List<PerfInterval> intervals = arrayInterval.getPerfInterval();
        System.out.println("Performance intervals (" + intervals.size() + "):");
        System.out.println("---------------------");

        int count = 0;
        for (PerfInterval interval : intervals) {
            System.out.print((++count) + ": " + interval.getName());
            System.out.print(" -- period = " + interval.getSamplingPeriod());
            System.out.println(", length = " + interval.getLength());
        }
        System.out.println();*/
    }

    public List<PerfQuerySpec> getPerfQuerySpec(List<PerfMetricId> pmidlist) {
        List<PerfQuerySpec> list = new ArrayList<>();

        for (PerfMetricId pmid : pmidlist) {
            PerfMetricId temp = new PerfMetricId();
            pmid.setInstance("*");
            pmid.setCounterId(pmid.getCounterId());

            PerfQuerySpec pqSpec = new PerfQuerySpec();
            pqSpec.setIntervalId(20);
            pqSpec.setMaxSample(1);
            pqSpec.getMetricId().add(pmid);

            pqSpec.setFormat(PerfFormat.CSV.value());

            pqSpec.setEntity(hostmor);
            list.add(pqSpec);
        }
        return list;
    }

}