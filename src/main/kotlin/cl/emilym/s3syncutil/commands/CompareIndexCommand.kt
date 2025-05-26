package cl.emilym.s3syncutil.commands

import cl.emilym.s3syncutil.domain.CompareIndexJob
import cl.emilym.s3syncutil.domain.ReadTripIndexJob
import cl.emilym.s3syncutil.files.FileContext
import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.models.FileChange
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "compare-index",
    description = ["Compares an index of two file structures."]
)
class CompareIndexCommand: Callable<Void> {

    @CommandLine.Option(
        names = ["-o", "--old"],
        required = true
    )
    lateinit var oldPath: String

    @CommandLine.Option(
        names = ["-n", "--new"],
        required = true
    )
    lateinit var newPath: String

    override fun call(): Void? {
        val old = ReadTripIndexJob(FileManager.get(FileContext.forPath(oldPath)))(oldPath)
        val new = ReadTripIndexJob(FileManager.get(FileContext.forPath(newPath)))(newPath)

        val changes = CompareIndexJob()(old, new)

        for (change in changes) {
            println("${
                when (change) {
                    is FileChange.Create -> "CREATE"
                    is FileChange.Update -> "UPDATE"
                    is FileChange.Delete -> "DELETE"
                }
            }: ${change.path}")
        }

        return null
    }
}