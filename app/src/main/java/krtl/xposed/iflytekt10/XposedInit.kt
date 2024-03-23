package krtl.xposed.iflytekt10

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import krtl.xposed.iflytekt10.module.PackageInstaller
import krtl.xposed.iflytekt10.module.SystemUI

class XposedInit: IXposedHookLoadPackage {
    private val systemUI = SystemUI()
    private val packagerInstaller = PackageInstaller()
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        when (lpparam.packageName) {
            "com.android.packageinstaller" -> packagerInstaller.initialize(lpparam)
            "com.android.systemui" -> systemUI.initialize(lpparam)
            else -> return
        }
    }
}