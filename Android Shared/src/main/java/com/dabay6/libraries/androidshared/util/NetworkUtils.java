/*
 * Copyright (c) 2014 Remel Pugh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dabay6.libraries.androidshared.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.dabay6.libraries.androidshared.enums.NetworkTypes;

import java.io.IOException;
import java.net.InetAddress;

/**
 * NetworkUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class NetworkUtils {
    private static final String IP_FORMAT = "%d.%d.%d.%d";

    /**
     * Formats the given ip address.
     *
     * @param ip The ip address to format.
     *
     * @return The formatted ip address.
     */
    public static String formatIpAddress(final int ip) {
        return StringUtils.format(IP_FORMAT, (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
    }

    /**
     * Calculate the broadcast IP we need to send the packet along. If we send it to 255.255.255.255,
     * it never gets sent. I guess this has something to do with the mobile network not wanting to do broadcast.
     *
     * @param context The {@link Context} used to retrieve the {@link WifiManager} system service.
     * @param type    The type of network to retrieve the broadcast address.
     *
     * @return The broadcast address for the network type or null.
     */
    public static InetAddress getBroadcastAddress(final Context context, final NetworkTypes type) throws IOException {
        final InetAddress broadcast;
        final int mask;
        final byte[] quads = new byte[4];

        switch (type) {
            case WIFI: {
                final DhcpInfo info;
                final WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                info = manager.getDhcpInfo();
                if (info == null) {
                    return null;
                }

                mask = (info.ipAddress & info.netmask) | ~info.netmask;

                for (int k = 0; k < 4; k++) {
                    quads[k] = (byte) ((mask >> k * 8) & 0xFF);
                }

                broadcast = InetAddress.getByAddress(quads);
                break;
            }
            default: {
                broadcast = null;
            }
        }

        return broadcast;
    }

    /**
     * Retrieve the assigned ip address for the given network type.
     *
     * @param context The {@link Context} used to retrieve system related services.
     * @param type    The type of network to retrieve the ip address.
     *
     * @return The ip address or null.
     */
    public static String getIpAddress(final Context context, final NetworkTypes type) {
        final Integer ip;

        switch (type) {
            case WIFI: {
                final WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                final WifiInfo info = manager.getConnectionInfo();

                if (info == null) {
                    return null;
                }

                ip = info.getIpAddress();
                break;
            }
            default: {
                return null;
            }
        }

        return formatIpAddress(ip);
    }
}