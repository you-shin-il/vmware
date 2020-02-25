package provider;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.ServiceDelegate;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.w3c.dom.Element;

public class MyProvider extends Provider
{

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass)
    {
        try {
            return ((Provider) Class.forName("org.apache.cxf.jaxws.spi.ProviderImpl").newInstance()).createServiceDelegate(wsdlDocumentLocation, serviceName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Endpoint createEndpoint(String bindingId, Object implementor)
    {
        try {
            return ((Provider) Class.forName("com.sun.xml.internal.ws.spi.ProviderImpl").newInstance()).createEndpoint(bindingId, implementor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Endpoint createAndPublishEndpoint(String address, Object implementor)
    {
        try {
            return ((Provider) Class.forName("com.sun.xml.internal.ws.spi.ProviderImpl").newInstance()).createAndPublishEndpoint(address, implementor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EndpointReference readEndpointReference(Source eprInfoset)
    {
        try {
            return ((Provider) Class.forName("org.apache.cxf.jaxws.spi.ProviderImpl").newInstance()).readEndpointReference(eprInfoset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T getPort(EndpointReference endpointReference, Class<T> serviceEndpointInterface, WebServiceFeature... features)
    {
        try {
            return ((Provider) Class.forName("org.apache.cxf.jaxws.spi.ProviderImpl").newInstance()).getPort(endpointReference, serviceEndpointInterface, features);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public W3CEndpointReference createW3CEndpointReference(String address, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters)
    {
        try {
            return ((Provider) Class.forName("org.apache.cxf.jaxws.spi.ProviderImpl").newInstance()).createW3CEndpointReference(address, serviceName, portName, metadata, wsdlDocumentLocation,
                    referenceParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}