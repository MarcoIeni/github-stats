package presentation

import com.github.ajalt.clikt.core.CliktCommand
import usecases.getStats.GetStats

class Show(val getStats: GetStats) : CliktCommand(){

    override fun run() {
        getStats()
    }

}