/**
 * HelloE8.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.weavernorth.services.webservices;

public interface HelloE8 extends javax.xml.rpc.Service {
    public String getHelloE8HttpPortAddress();

    public com.weavernorth.services.webservices.HelloE8PortType getHelloE8HttpPort() throws javax.xml.rpc.ServiceException;

    public com.weavernorth.services.webservices.HelloE8PortType getHelloE8HttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
