import com.github.ajalt.clikt.core.subcommands
import org.kodein.di.generic.instance
import org.kodein.di.newInstance
import presentation.Add
import presentation.Cli
import usecases.getStats.GetStats

fun main(args: Array<String>) {

    val dependencyInjector = DependencyInjector()
    val getStats by dependencyInjector.kodein.newInstance { GetStats(instance(), instance(), instance()) }
    getStats()

    return Cli().subcommands(Add()).main(args)
}


