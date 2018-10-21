package jp.gr.java_conf.koreander.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class ItemListFragment : ListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // リストに表示する文字列を設定
        val itemTitleList: List<String> = listOf(
                context!!.resources.getString(R.string.weather_forecast_viewr_fragment_title)
        )
        listAdapter = ArrayAdapter(context!!, android.R.layout.simple_expandable_list_item_1,
                itemTitleList)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val fragment = when (id.toInt()){
            0 -> {
                WeatherForecastViewerFragment()
            }
            else -> {
                null
            }
        }

        if (fragment is Fragment){
            fragmentManager!!
                    .beginTransaction()
                    .replace(R.id.item_fragment, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }
}
