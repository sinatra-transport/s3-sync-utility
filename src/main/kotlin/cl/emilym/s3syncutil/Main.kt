package cl.emilym.s3syncutil

import cl.emilym.s3syncutil.commands.BuildIndexCommand
import picocli.CommandLine

fun main(args: Array<String>) {
    CommandLine(BuildIndexCommand()).execute(*args)
}