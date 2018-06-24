package jp.gr.java_conf.koreander.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class ItemFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemListView: ListView = view.findViewById(R.id.item_list_view)

        itemListView.onItemClickListener =
                AdapterView.OnItemClickListener {parent, view, position, id ->
                    // タップされた項目に対応したフラグメントに画面遷移させる
                    // 仮
                    Toast.makeText(this.context, parent.getItemAtPosition(position) as String,
                                    Toast.LENGTH_LONG).show()
                }
    }
}