package krtl.xposed.iflytekt10.util

import java.io.OutputStreamWriter
fun bytesToHumanReadable(bytes: Long, si: Boolean = true): String {
    val unit = if (si) 1000 else 1024
    if (bytes < unit) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val prefix = "KMGTPE"[exp - 1] + (if (si) "" else "i")
    val result = bytes / Math.pow(unit.toDouble(), exp.toDouble())
    return String.format("%.1f %sB", result, prefix)
}

fun checkRoot(): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec("su")
        val out = OutputStreamWriter(process.outputStream)
        out.write("exit\n")
        out.flush()
        process.waitFor()
        process.exitValue() == 0
    } catch (e: Exception) {
        false
    } finally {
        process?.destroy()
    }
}

fun runAsRoot(commands: List<String>): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec("su")
        val out = OutputStreamWriter(process.outputStream)
        for (command in commands) {
            out.write("$command\n")
            out.flush()
        }
        out.write("exit\n")
        out.flush()
        process.waitFor()
        process.exitValue() == 0
    } catch (e: Exception) {
        false
    } finally {
        process?.destroy()
    }
}
fun restartSystemUI() {
    runAsRoot(listOf("killall com.android.systemui"))
}