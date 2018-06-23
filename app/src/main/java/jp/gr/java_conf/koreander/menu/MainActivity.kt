package jp.gr.java_conf.koreander.menu

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity() {

    private val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.main_view_pager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = viewPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.main_tab_layout)
        for (i in 1..(viewPagerAdapter.count)) tabLayout.addTab(tabLayout.newTab())
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
    }

    private class ViewPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
        private val tabTitleList: List<String> = listOf(
                context.resources.getString(R.string.item_fragment_title),
                context.resources.getString(R.string.equipment_fragment_title),
                context.resources.getString(R.string.status_fragment_title),
                context.resources.getString(R.string.skill_fragment_title),
                context.resources.getString(R.string.quest_fragment_title),
                context.resources.getString(R.string.community_fragment_title),
                context.resources.getString(R.string.save_fragment_title)
        )

        private val fragmentList: List<Fragment> = listOf(
                ItemFragment(), EquipmentFragment(), StatusFragment(), SkillFragment(),
                QuestFragment(), CommunityFragment(), SaveFragment())

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
