package krtl.xposed.iflytekt10.util

import android.net.TrafficStats

class NetworkTraffic {

    var lastTraffic = getTotalTraffic()
    var lastTime = System.currentTimeMillis()
    fun getTotalTraffic(): List<Long> {
        return listOf(TrafficStats.getTotalRxBytes(), TrafficStats.getTotalTxBytes())
    }

    private fun recordLastTraffic() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime < 1000) {
            return
        }
        lastTraffic = getTotalTraffic()
        lastTime = currentTime
    }

    private fun calculateTrafficDelta(): List<Long> {
        val currentTraffic = getTotalTraffic()

        return listOf(currentTraffic[0]-lastTraffic[0],currentTraffic[1]-lastTraffic[1])
    }

    fun getTraffic(): List<Long> {
        val result = calculateTrafficDelta()
        recordLastTraffic()
        return result
    }
}