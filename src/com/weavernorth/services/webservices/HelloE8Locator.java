/**
 * HelloE8Locator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.weavernorth.services.webservices;

public class HelloE8Locator extends org.apache.axis.client.Service implements HelloE8 {

    public HelloE8Locator() {
    }


    public HelloE8Locator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HelloE8Locator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HelloE8HttpPort
    private String HelloE8HttpPort_address = "http://192.168.1.110:8080//services/HelloE8";

    public String getHelloE8HttpPortAddress() {
        return HelloE8HttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String HelloE8HttpPortWSDDServiceName = "HelloE8HttpPort";

    public String getHelloE8HttpPortWSDDServiceName() {
        return HelloE8HttpPortWSDDServiceName;
    }

    public void setHelloE8HttpPortWSDDServiceName(String name) {
        HelloE8HttpPortWSDDServiceName = name;
    }

    public com.weavernorth.services.webservices.HelloE8PortType getHelloE8HttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloE8HttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloE8HttpPort(endpoint);
    }

    public com.weavernorth.services.webservices.HelloE8PortType getHelloE8HttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.weavernorth.services.webservices.HelloE8HttpBindingStub _stub = new com.weavernorth.services.webservices.HelloE8HttpBindingStub(portAddress, this);
            _stub.setPortName(getHelloE8HttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHelloE8HttpPortEndpointAddress(String address) {
        HelloE8HttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.weavernorth.services.webservices.HelloE8PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.weavernorth.services.webservices.HelloE8HttpBindingStub _stub = new com.weavernorth.services.webservices.HelloE8HttpBindingStub(new java.net.URL(HelloE8HttpPort_address), this);
                _stub.setPortName(getHelloE8HttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("HelloE8HttpPort".equals(inputPortName)) {
            return getHelloE8HttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("webservices.services.weavernorth.com", "HelloE8");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("webservices.services.weavernorth.com", "HelloE8HttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("HelloE8HttpPort".equals(portName)) {
            setHelloE8HttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
