package cl.emilym.s3syncutil.commands

import cl.emilym.s3syncutil.domain.CompareIndexJob
import cl.emilym.s3syncutil.domain.GenerateIndexJob
import cl.emilym.s3syncutil.domain.ReadTripIndexJob
import cl.emilym.s3syncutil.files.FileContext
import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.models.FileChange
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "cp",
    description = ["Copies changed files to a destination"]
)
class CopyChangedCommand: Callable<Void> {

    @CommandLine.Parameters(
        index = "0",
        description = ["Source directory/file"]
    )
    lateinit var inputPath: String

    @CommandLine.Parameters(
        index = "1",
        description = ["Destination directory/file"]
    )
    lateinit var outputPath: String

    @CommandLine.Option(
        names = ["-o", "--old-index"],
        required = true
    )
    lateinit var oldIndexPath: String

    @CommandLine.Option(
        names = ["-n", "--new-index"],
        required = false
    )
    var newIndexPath: String? = null

    @CommandLine.Option(
        names = ["--ignore-delete"],
        required = false
    )
    var ignoreDelete: Boolean = false

    override fun call(): Void? {
        val newIndex = when (newIndexPath) {
            null -> GenerateIndexJob(FileManager.get(FileContext.forPath(inputPath)))(inputPath)
            else -> ReadTripIndexJob(FileManager.get(
                FileContext.forPath(newIndexPath!!))
            )(newIndexPath!!)
        }
        val oldIndex = ReadTripIndexJob(FileManager.get(FileContext.forPath(oldIndexPath)))(oldIndexPath)
        val changes = CompareIndexJob()(oldIndex, newIndex)

        val outputFileManager = FileManager.get(FileContext.forPath(outputPath))
        val inputFileManager = FileManager.get(FileContext.forPath(inputPath))

        for (change in changes) {
            when (change) {
                is FileChange.Delete -> {
                    if (ignoreDelete) continue
                    outputFileManager.delete(change.path)
                }
                else -> outputFileManager.write(change.path, inputFileManager.read(change.path))
            }
        }

        return null
    }
}