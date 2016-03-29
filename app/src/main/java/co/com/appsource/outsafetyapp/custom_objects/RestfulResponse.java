package co.com.appsource.outsafetyapp.custom_objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JANUS on 20/02/2016.
 */
public class RestfulResponse {

    @SerializedName("@odata.context")
    public String oDataContext;

    @SerializedName("value")
    public String value;
}
