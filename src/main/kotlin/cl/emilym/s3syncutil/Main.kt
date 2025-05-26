package cl.emilym.s3syncutil

import cl.emilym.s3syncutil.commands.RootCommand
import picocli.CommandLine

fun main(args: Array<String>) {
    CommandLine(RootCommand()).execute(*args)
}