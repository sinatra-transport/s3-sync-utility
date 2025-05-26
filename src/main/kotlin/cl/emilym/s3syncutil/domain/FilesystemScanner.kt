package cl.emilym.s3syncutil.domain

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries

interface FilesystemVisitor {

    fun visit(
        path: String,
        content: ByteArray
    )

}

interface FilesystemScanner {

    fun scan(root: String, visitors: List<FilesystemVisitor>)

}

class LocalFilesystemScanner: FilesystemScanner {

    override fun scan(root: String, visitors: List<FilesystemVisitor>) {
        for (file in Path.of(root).listDirectoryEntries()) {
            if (file.isDirectory()) return scan(file.toString(), visitors)

            val content = Files.readAllBytes(file)
            visitors.forEach { it.visit(
                file.toString(),
                content
            ) }
        }
    }

}