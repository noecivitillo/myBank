package todo1.test.bank.data.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("currently")
    @Expose
    var currently: Currently? = null
}


