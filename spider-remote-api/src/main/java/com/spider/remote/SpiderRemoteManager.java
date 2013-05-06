package com.spider.remote;

import com.wolf.framework.remote.FrameworkSessionBeanRemote;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author aladdin
 */
public class SpiderRemoteManager {

    public static FrameworkSessionBeanRemote getBrowserProxySessionBeanRemote() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.19.219");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "6837");
        FrameworkSessionBeanRemote remote;
        try {
            InitialContext ic = new InitialContext(props);
            remote = (FrameworkSessionBeanRemote) ic.lookup("com.wolf.framework.remote.FrameworkSessionBeanRemote");
        } catch (NamingException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
        return remote;
    }
}
