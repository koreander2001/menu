package jp.gr.java_conf.koreander.menu

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity() {

    // private val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.main_view_pager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this.resources)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = viewPagerAdapter.count

        val tabLayout: TabLayout = findViewById(R.id.main_tab_layout)
        for (i in 1..(viewPagerAdapter.count)) tabLayout.addTab(tabLayout.newTab())
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        val viewPager: ViewPager = findViewById(R.id.main_view_pager)
        val viewPagerAdapter = viewPager.adapter as ViewPagerAdapter
        val fragment = viewPagerAdapter.findFragmentByPosition(viewPager, viewPager.currentItem)

        if (!fragment.onBackPressed()){
            super.onBackPressed()
        }
    }

    private class ViewPagerAdapter(fm: FragmentManager, resources: Resources):
            FragmentPagerAdapter(fm) {
        private val tabTitleList: List<String> = listOf(
                resources.getString(R.string.item_fragment_title),
                resources.getString(R.string.equipment_fragment_title),
                resources.getString(R.string.status_fragment_title),
                resources.getString(R.string.skill_fragment_title),
                resources.getString(R.string.quest_fragment_title),
                resources.getString(R.string.community_fragment_title),
                resources.getString(R.string.save_fragment_title)
        )

        private val fragmentList: List<TabFragment> = listOf(
                ItemFragment(), EquipmentFragment(), StatusFragment(), SkillFragment(),
                QuestFragment(), CommunityFragment(), SaveFragment())

        override fun getItem(position: Int): TabFragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitleList[position]
        }

        fun findFragmentByPosition(viewPager: ViewPager, position: Int): TabFragment {
            return instantiateItem(viewPager, position) as TabFragment
        }
    }
}
