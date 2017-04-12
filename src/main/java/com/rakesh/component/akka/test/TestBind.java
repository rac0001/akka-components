package com.rakesh.component.akka.test;

import java.io.IOException;
import java.net.*;

/**
 * Created by ranantoju on 4/10/2017.
 */
public class TestBind {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress ia=null;
        String host="10.248.66.127";//"10.248.66.70";//"10.248.66.127";
        try {
            ia = InetAddress.getByName(host);
            scan(ia);
        }
        catch (UnknownHostException e) {
            System.out.println(e );
        }
        System.exit(0);
    }

    public static void scan(final InetAddress host) {


        int port=37719;//37719;//47576;//13782;
        String hostname = host.getHostName();
        Socket s = null;
        String reason = null ;
            try {
                s = new Socket();
                s.setReuseAddress(true);
                SocketAddress sa = new InetSocketAddress(host, port);
                s.connect(sa);
                System.out.println("Server is listening on port " + port+ " of " + hostname);
                s.close();
            }
            catch (IOException ex) {
                // The remote host is not listening on this port
                System.out.println("Server is not listening on port " + port+ " of " + hostname);
            }

    }
}
