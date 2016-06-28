package com.anderson.categories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;

import com.anderson.AndroidUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;


public class web {

    /**
     * protected constructor
     */
    protected web() {
    }

    /**
     * Download a file to the sdcard
     * Please remember to
     * ive your application the permission to access the Internet and to
     * write to external storage Create the Folder (eg.
     * <code>File wallpaperDirectory = new File("/sdcard/Wallpaper/");
     * // have the object build the directory structure, if needed.
     * wallpaperDirectory.mkdirs();</code>
     *
     * @param downloadURL from where you want to download
     * @param sdcardPath  path to download the asset
     * @return has done
     */
    public static boolean downloadToSDCard(String downloadURL, String sdcardPath) {
        try {
            URL url = new URL(downloadURL);

            URLConnection connection = url.openConnection();
            connection.connect();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(sdcardPath);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }





    /**
     * Checks if the app has connectivity to the Internet
     *
     * @return true if has connection to the Internet and false if it doesn't
     */
    public static boolean hasInternetConnection() {
        ConnectivityManager connectivity = (ConnectivityManager) AndroidUtils.getContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    /**
     * Set wireless connectivity On, also this method will need the permissions
     * "android.permission.CHANGE_WIFI_STATE" and
     * "android.permission.ACCESS_WIFI_STATE"
     *
     * @param state - set enable or disable wireless connection
     * @return true if was set successfully and false if it wasn't
     */
    public static boolean changeWirelessState(boolean state) {
        try {
            WifiManager wifi = (WifiManager) AndroidUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(state);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set mobile data connectivity on/off.
     * API level 9+ only (Android 2.3+).
     * This method will need "android.permission.CHANGE_NETWORK_STATE" permission.
     *
     * @param dataEnabled true to enable and false to disable mobile data
     * @return true if set successfully, false otherwise
     */
    public static boolean changeMobileDataState(boolean dataEnabled) {
        if (Build.VERSION.SDK_INT < 9) {
            throw new UnsupportedOperationException("Unsupported SDK version. This operation is only available on SDK 9 or above.");
        }

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AndroidUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
            Method setMobileDataEnabled = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabled.setAccessible(true);
            setMobileDataEnabled.invoke(connectivityManager, dataEnabled);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check state of mobile data connectivity (on/off).
     * Please note that this method *does not* guarantee that a connection is available, it only
     * checks if mobile data is turned on/off.
     *
     * @return true if enabled, false if disabled
     * @throws  Exception exception
     */
    public static boolean checkMobileDataState() throws Exception {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AndroidUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
            Method getMobileDataEnabled = connectivityManagerClass.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabled.setAccessible(true);

            return (Boolean) getMobileDataEnabled.invoke(connectivityManager);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unexpected exception. Please check stack trace");
        }
    }

    /**
     * Check if can connect to the server, also this method will need the
     * permissions "android.permission.INTERNET"
     *
     * @param u - server url
     * @return true if the connection returned a successful code
     */
    public static boolean checkServerConnection(URL u) {
        boolean value = false;
        try {
            value = new RetrieveCheckServerConnection().execute(u).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;

    }

    /**
     * AsyncTask that will run the code responsible to try to connect to the
     * server url
     *
     * @author Pereira
     */
    private static class RetrieveCheckServerConnection extends AsyncTask<URL, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(URL... url) {
            try {
                HttpURLConnection huc = (HttpURLConnection) url[0].openConnection();
                huc.setRequestMethod("GET");
                huc.connect();
                int code = huc.getResponseCode();
                if (code == 200) {
                    return true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    /**
     * Check if can connect to the server, also this method will need the
     * permissions "android.permission.INTERNET"
     *
     * @param serverURL - server url
     * @return true if the connection returned a successful code
     */
    public static boolean checkServerConnection(String serverURL) {
        boolean value = false;
        try {
            value = new RetrieveCheckServerConnectionString().execute(serverURL).get();
        } catch (InterruptedException e) {
            AndroidUtils.log.e("InterruptedException", e);
        } catch (ExecutionException e) {
            AndroidUtils.log.e("ExecutionException", e);
        }
        return value;
    }

    /**
     * AsyncTask that will run the code responsible to try to connect to the
     * server url
     *
     * @author Pereira
     */
    private static class RetrieveCheckServerConnectionString extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(String... serverURL) {
            try {
                URL u = new URL(serverURL[0]);
                HttpURLConnection huc = (HttpURLConnection) u.openConnection();
                huc.setRequestMethod("GET"); // OR huc.setRequestMethod
                // ("HEAD");
                huc.connect();
                int code = huc.getResponseCode();
                if (code == 200) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

    }
}
