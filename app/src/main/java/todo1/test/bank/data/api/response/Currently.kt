package todo1.test.bank.data.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Currently {
    @SerializedName("humidity")
    @Expose
    var humidity: Double? = null
    @SerializedName("precipProbability")
    @Expose
    var precipProbability: Double? = null
    @SerializedName("summary")
    @Expose
    var summary: String? = null
    @SerializedName("temperature")
    @Expose
    var temperature: Double? = null
}