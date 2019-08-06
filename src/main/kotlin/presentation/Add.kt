package presentation

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

class Add: CliktCommand(help="Track repository"){
    val name by argument()

    override fun run() {
        echo("hello $name")
    }
}