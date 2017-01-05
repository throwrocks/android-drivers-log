package athrow.rocks.android_drivers_log.data;

import org.json.JSONArray;

/**
 * APIResponse
 * Created by joselopez on 10/19/16.
 */

public final class APIResponse {

    private String responseText;
    private JSONArray responseJSONArray;
    private int responseCode;
    private String meta;

    APIResponse() {
    }

    void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    void setResponseText(String responseText) {
        this.responseText = responseText;
    }
    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public String getMeta() {
        return meta;
    }

    void setMeta(String meta) {
        this.meta = meta;
    }
}
