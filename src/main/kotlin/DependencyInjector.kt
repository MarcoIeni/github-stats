import data.persistence.settings.JsonSettingsRepository
import data.network.ScraperStatsNetworkSource
import data.persistence.PersistenceFilePaths
import data.persistence.stats.JsonStatsPersistenceSource
import org.kodein.di.Kodein.Companion.lazy
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import presentation.StatsChangeListener
import presentation.StatsPresenter
import presentation.view.StatsView
import repositories.settings.SettingsRepository
import repositories.StatsNetworkSource
import repositories.StatsPersistenceSource
import repositories.StatsRepository
import usecases.getStats.GetStats
import usecases.getStats.GetStatsOutput

class DependencyInjector : KodeinAware {
    override val kodein = lazy {
        bind<StatsChangeListener>() with provider { StatsView() }
        bind() from singleton { PersistenceFilePaths("data/settings.json", "data/cache.json", "data/tracked.json") }
        bind<SettingsRepository>() with provider { JsonSettingsRepository(instance()) }
        bind<GetStatsOutput>() with provider { StatsPresenter(instance()) }
        bind() from provider { GetStats(instance(), instance(), instance()) }
        bind<StatsNetworkSource>() with provider { ScraperStatsNetworkSource() }
        bind<StatsPersistenceSource>() with provider { JsonStatsPersistenceSource(instance()) }
        bind() from singleton { StatsRepository(instance(), instance(), instance()) }
    }
}