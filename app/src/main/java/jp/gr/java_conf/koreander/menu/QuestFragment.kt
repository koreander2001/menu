package jp.gr.java_conf.koreander.menu

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class QuestFragment : Fragment() {

    // private val TAG: String = this.javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_quest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: NonSwipableViewPager = view.findViewById(R.id.quest_view_pager)
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, context!!)
        viewPager.adapter = viewPagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.quest_tab_layout)
        for (i in 1..(viewPagerAdapter.count)) tabLayout.addTab(tabLayout.newTab())
        tabLayout.setupWithViewPager(viewPager)
    }

    private class ViewPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
        private val tabTitleList: List<String> = listOf(
                context.resources.getString(R.string.ongoing_quest_fragment_title),
                context.resources.getString(R.string.achieved_quest_fragment_title),
                context.resources.getString(R.string.failed_quest_fragment_title)
        )

        private val fragmentList: List<Fragment> = listOf(
                OngoingQuestFragment(), AchievedQuestFragment(), FailedQuestFragment())

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitleList[position]
        }
    }
}
