package krtl.xposed.iflytekt10.module

import android.annotation.SuppressLint
import android.os.LocaleList
import android.widget.TextView
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import krtl.xposed.iflytekt10.util.NetworkTraffic
import krtl.xposed.iflytekt10.util.bytesToHumanReadable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SystemUI: BaseHook {
    // TODO: rewrite this whole class
    fun getCurrentLocale(): Locale {
        // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        return   LocaleList.getDefault().get(0)
//        } else {
//            Locale.getDefault()
//        }
    }
    fun getDateDisplay():String {

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy.MM.dd E (z)", getCurrentLocale())

        val time = calendar.time

        return dateFormat.format(time)
    }
    val networkTraffic = NetworkTraffic()
    var date = getDateDisplay()
    override fun initialize(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedHelpers.findAndHookMethod(
            "com.android.systemui.statusbar.policy.Clock",
            lpparam.classLoader,
            "updateClock",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    XposedHelpers.setBooleanField(param.thisObject, "mShowSeconds", true)
                }
                @SuppressLint("SetTextI18n")
                override fun afterHookedMethod(param: MethodHookParam) {
                    val textView = param.thisObject as TextView
                    val text = textView.text
                    textView.text = "↓${networkTraffic.getTraffic().joinToString(" ↑") { bytesToHumanReadable(it) + "/s" } } $date $text"
//                    tv.setTextColor(Color.RED)
                }
            })
    }
}