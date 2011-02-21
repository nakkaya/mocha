// Copyright 2011 Nurullah Akkaya

// Mocha is free software: you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the
// Free Software Foundation, either version 3 of the License, or (at your
// option) any later version.

// Mocha is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
// for more details.

// You should have received a copy of the GNU General Public License
// along with Mocha. If not, see http://www.gnu.org/licenses/.

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
    public static final boolean mocha_notify_mail_use_SSL = true;
    public static final String mocha_smtp_server = "";
    public static final int mocha_smtp_port = 465;
    public static final String mocha_smtp_username = "";
    public static final String mocha_smtp_password = "";
    public static final String mocha_smtp_email = "";

    public static final String mocha_firewall_log = "";
}
