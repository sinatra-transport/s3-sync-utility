package cl.emilym.s3syncutil.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo

interface FilesystemVisitor {

    fun visit(
        path: String,
        content: ByteArray
    )

}