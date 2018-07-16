package jp.gr.java_conf.koreander.menu

import android.support.v4.app.Fragment

abstract class TabFragment : Fragment() {
    open fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount > 0){
            childFragmentManager.popBackStack()
            return true
        } else {
            return false
        }
    }
}