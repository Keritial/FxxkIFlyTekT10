package krtl.xposed.iflytekt10.module

import de.robv.android.xposed.callbacks.XC_LoadPackage

interface BaseHook {
    fun initialize(lpparam: XC_LoadPackage.LoadPackageParam)
}