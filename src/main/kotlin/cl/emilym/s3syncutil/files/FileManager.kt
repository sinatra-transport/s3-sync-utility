package cl.emilym.s3syncutil.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo

interface FileManager {

    fun read(path: String): ByteArray
    fun write(path: String, content: ByteArray)

    fun scan(root: String, visitors: List<FilesystemVisitor>)

    companion object {

        fun get(context: FileContext): FileManager {
            return LocalFileManager()
        }

    }

}

class LocalFileManager: FileManager {

    override fun read(path: String): ByteArray {
        return Files.readAllBytes(Path.of(path))
    }

    override fun write(path: String, content: ByteArray) {
        Files.write(Path.of(path), content)
    }

    override fun scan(root: String, visitors: List<FilesystemVisitor>) {
        _scan(Path.of(root), Path.of(root), visitors)
    }

    private fun _scan(root: Path, current: Path, visitors: List<FilesystemVisitor>) {
        for (file in current.listDirectoryEntries()) {
            when {
                file.isDirectory() -> _scan(root, file, visitors)
                else -> {
                    val content = Files.readAllBytes(file)
                    visitors.forEach { it.visit(
                        file
                            .relativeTo(root)
                            .toString(),
                        content
                    ) }
                }
            }
        }
    }

}