package cl.emilym.s3syncutil.commands

import cl.emilym.s3syncutil.files.FilesystemScanner
import cl.emilym.s3syncutil.domain.GenerateShaJob
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "build-index",
    description = ["Builds an index of a file structure."]
)
class BuildIndexCommand: Callable<Void> {

    @CommandLine.Option(
        names = ["-i", "--input"],
        required = true
    )
    lateinit var inputPath: String

    @CommandLine.Option(
        names = ["-o", "--output"],
        required = true
    )
    lateinit var outputPath: String

    override fun call(): Void? {
        val scanner = FilesystemScanner.get(inputPath)
        val sha = GenerateShaJob(scanner)(inputPath)
        println(sha)
        return null
    }

}