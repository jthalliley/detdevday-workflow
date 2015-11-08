package com.riis.WorkflowExample;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Contains settings used throughout the application.
 */
public class Settings {

    private static final String TAG = "Settings";

    private static final String EMPTY_VALUE      = "";
    private static final String UNKNOWN_VALUE    = "UNKNOWN";
    private static final String HTTP_PROTOCOL    = "http";
    private static final String HTTPS_PROTOCOL   = "https";
    private static final String DEFAULT_PORT     = "8080";

    private Map<SettingsKeys,String> values;
    private List<ApiUrl>             apiUrlList;


    public Settings() {
        String[] apiURLs     = WorkflowExampleApplication.getStringArrayResource(R.array.api_urls);
        String[] apiURLNames = WorkflowExampleApplication.getStringArrayResource(R.array.api_urls_spinner);

        if (apiURLs.length != apiURLNames.length) {
            throw new RuntimeException("strings.xml is incorrect re: api_urls and api_urls_spinner");
        }

        values = new HashMap<SettingsKeys,String>();

        apiUrlList = new ArrayList<ApiUrl>();
        for (int i = 0; i < apiURLs.length; i++) {
            apiUrlList.add(new ApiUrl(apiURLs[i], apiURLNames[i]));
        }

        // Set default, if settings screen is not touched.
        setBaseURL(false, 0);
    }

    public void setBaseURL(final boolean isHttps, final int pos) {
        String newValue =
            (isHttps ? "https" : "http") +
            ((0 <= pos && pos < apiUrlList.size()) ? apiUrlList.get(pos).url : UNKNOWN_VALUE);
        setBaseURL(newValue);
        Log.i(TAG, "Base URL is now " + newValue + " (" + apiUrlList.get(pos).description + ")");
    }

    private void setBaseURL(final String theValue) {
        values.put(SettingsKeys.BASE_URL, theValue);
    }

    public String getBaseURL() {
        return values.containsKey(SettingsKeys.BASE_URL)
            ? values.get(SettingsKeys.BASE_URL)
            : EMPTY_VALUE;
    }

    public void addHost(final boolean isHttps, final String hostname, final String description) {
        ApiUrl newUrl = new ApiUrl((isHttps ? HTTPS_PROTOCOL : HTTP_PROTOCOL) + "://" + hostname + ":" + DEFAULT_PORT,
                                   description);
        apiUrlList.add(newUrl);

        setBaseURL(newUrl.url);
    }

    public int numberOfHosts() {
        return apiUrlList.size();
    }

    enum SettingsKeys {
        BASE_URL
    }

    class ApiUrl {
        public String url;
        public String description;

        public ApiUrl(final String url, final String description) {
            this.url = url;
            this.description = description;
        }
    }

}
