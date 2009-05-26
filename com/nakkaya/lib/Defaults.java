package com.nakkaya.lib;

public class Defaults{
    static public String mocha_operatingSystem = "GENERIC";
    public static final boolean mocha_notify_firewall  = true;
    public static final boolean mocha_suppress_incomplete = true;
    public static final boolean mocha_suppress_newhost = true;

    public static final String mocha_arp_command="arp";
    public static final int mocha_arp_interval = 5;

    public static final int mocha_log_size = 100;


    public static final boolean mocha_notify_mail = false;
    public static final String mocha_smtp_server = "";
    public static final int mocha_smtp_port = 465;
    public static final String mocha_smtp_username = "";
    public static final String mocha_smtp_password = "";
    public static final String mocha_smtp_email = "";

    public static final String mocha_firewall_log = "";
}
