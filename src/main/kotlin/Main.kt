import org.kodein.di.generic.instance
import org.kodein.di.newInstance
import usecases.getStats.GetStats

fun main(args: Array<String>) {

    val dependencyInjector = DependencyInjector()
    val getStats by dependencyInjector.kodein.newInstance { GetStats(instance(), instance(), instance()) }
    getStats()
}


