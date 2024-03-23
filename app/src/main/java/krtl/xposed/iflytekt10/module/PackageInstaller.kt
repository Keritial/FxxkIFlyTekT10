package krtl.xposed.iflytekt10.module

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class PackageInstaller: BaseHook {
    override fun initialize(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedHelpers.findAndHookMethod(
            "com.android.packageinstaller.PackageInstallerActivity",
            lpparam.classLoader,
            "showDialogInner",
            Int::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val dialogId = param.args[0] as Int
                    if (dialogId != 8) {
                        return
                    }
                    val activityInstance = param.thisObject
                    XposedHelpers.callMethod(activityInstance, "initiateInstall")
                    param.result = null
                }

//                override fun afterHookedMethod(param: MethodHookParam) {
//                    Toast.makeText(param.thisObject as Activity, R.string._loaded_toast, Toast.LENGTH_SHORT).show()
//                }
            }
        )
    }

}