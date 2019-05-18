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
import com.eclipsesource.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastViewerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_weather_forecast_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableLayout = view.findViewById<TableLayout>(R.id.weather_table_layout)
        parallelUpdateTableLayout(tableLayout)
    }

    /**
     * 天気予報APIにHTTP通信し、得たJsonをもとにtableLayoutを更新する。
     */
    @SuppressLint("SimpleDateFormat")
    private fun parallelUpdateTableLayout(tableLayout: TableLayout) = GlobalScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.Default) { httpGet(getString(R.string.weather_forecast_api_url)) }.let {
            val json = Json.parse(it).asObject()
            val results = json.get("daily").asObject().get("data").asArray()

            // 表示文字列のフォーマット定義
            val dateFormat = SimpleDateFormat(getString(R.string.only_month_and_date_format))
            val probabilityFormat = "%2d%%"
            val temperatureFormat = "%d°"

            for (i in 0 until results.size()) {
                // Jsonから日付、降水確率、最高気温、最低気温を抽出
                val data = results.get(i).asObject()
                val datetime = Date(data.get("time").asLong() * 1000)
                val precipProbability = (data.get("precipProbability").asDouble() * 100).toInt()
                val temperatureHigh = data.get("temperatureHigh").asDouble().toInt()
                val temperatureLow = data.get("temperatureLow").asDouble().toInt()

                // 取得情報をTableLayoutに表示
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
    }

    /**
     * 指定URLにHTTPのGETで通信し、レスポンスのbody要素を返す。
     */
    private fun httpGet(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return response.body()?.string()
    }
}
