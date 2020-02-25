package vmware.common.authentication;

import com.vmware.vim25.VimPortType;
import com.vmware.vim25.VimService;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.namespace.QName;
import javax.xml.ws.spi.ServiceDelegate;

public class VimServiceCustom extends VimService {
    @Qualifier
    private ServiceDelegate delegate;

    public VimServiceCustom() {

    }

    public <T> T getPort(QName portName, Class<T> serviceEndpointInterface) {
        return delegate.getPort(portName, serviceEndpointInterface);
    }

    public VimPortType getVimPort() {
        return (VimPortType)super.getPort(new QName("urn:vim25Service", "VimPort"), VimPortType.class);
    }

}