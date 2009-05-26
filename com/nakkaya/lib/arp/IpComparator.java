package com.nakkaya.lib.arp;

import java.util.Comparator;
import com.nakkaya.lib.arp.Host;

public class IpComparator implements Comparator {

  public int compare(Object host, Object anotherHost) {

      String hostIp = ((Host)host).ipAddr.toUpperCase();
      String anotherHostIp = ((Host)anotherHost).ipAddr.toUpperCase();

      return hostIp.compareTo(anotherHostIp);
  }
}
