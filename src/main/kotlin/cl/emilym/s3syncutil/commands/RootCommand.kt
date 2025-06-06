package cl.emilym.s3syncutil.commands

import picocli.CommandLine

@CommandLine.Command(
    subcommands = [
        BuildIndexCommand::class,
        CompareIndexCommand::class,
        CopyChangedCommand::class
    ]
)
class RootCommand