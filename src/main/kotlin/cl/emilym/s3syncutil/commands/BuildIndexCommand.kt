package cl.emilym.s3syncutil.commands

import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "build-index",
    description = ["Builds an index of a file structure."]
)
class BuildIndexCommand: Callable<Void> {

    @CommandLine.Parameters(
        index = "0"
    )
    lateinit var path: String

    override fun call(): Void? {
        println("Test")
        return null
    }

}