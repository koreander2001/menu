package jp.gr.java_conf.koreander.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastViewerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_weather_forecast_viewer, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableLayout = view.findViewById<TableLayout>(R.id.weather_table_layout)

        getString(R.string.weather_forecast_api_url).httpGet().responseJson { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val json: JSONObject = result.value.obj()
                    val results = json.getJSONObject("daily").getJSONArray("data")

                    val dateFormat = SimpleDateFormat(getString(R.string.only_month_and_date_format))
                    val probabilityFormat = "%2d%%"
                    val temperatureFormat = "%d°"
                    for (i in 0 until results.length()){
                        // 日ごとの日付、降水確率、最高気温、最低気温を取得
                        val data = results.getJSONObject(i)
                        val datetime = Date(data.getLong("time") * 1000)
                        val precipProbability = (data.getDouble("precipProbability") * 100).toInt()
                        val temperatureHigh = data.getDouble("temperatureHigh").toInt()
                        val temperatureLow = data.getDouble("temperatureLow").toInt()

                        layoutInflater.inflate(R.layout.table_row_weather, tableLayout)
                        val tableRow = tableLayout.getChildAt(i) as TableRow
                        (tableRow.getChildAt(0) as TextView).text = dateFormat.format(datetime)
                        (tableRow.getChildAt(1) as TextView).text = probabilityFormat
                                .format(precipProbability)
                        (tableRow.getChildAt(2) as TextView).text = temperatureFormat
                                .format(temperatureHigh)
                        (tableRow.getChildAt(3) as TextView).text = temperatureFormat
                                .format(temperatureLow)
                    }
                }
                is Result.Failure -> {
                    // TODO: JSON取得失敗時の処理を追加
                }
            }
        }
    }
}
