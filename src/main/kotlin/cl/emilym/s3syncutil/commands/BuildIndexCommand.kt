package cl.emilym.s3syncutil.commands

import cl.emilym.s3syncutil.domain.GenerateShaJob
import cl.emilym.s3syncutil.domain.WriteTripIndexJob
import cl.emilym.s3syncutil.files.FileContext
import cl.emilym.s3syncutil.files.FileManager
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
        val sha = GenerateShaJob(
            FileManager.get(FileContext.forPath(inputPath))
        )(inputPath)
        WriteTripIndexJob(
            FileManager.get(FileContext.forPath(outputPath))
        )(outputPath, sha)

        return null
    }

}