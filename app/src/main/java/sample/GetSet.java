
package sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSet {

    @SerializedName("ds")
    @Expose
    private Ds ds;

    public Ds getDs() {
        return ds;
    }

    public void setDs(Ds ds) {
        this.ds = ds;
    }

}
