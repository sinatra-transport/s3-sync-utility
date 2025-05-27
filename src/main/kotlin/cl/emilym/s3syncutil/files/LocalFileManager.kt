package cl.emilym.s3syncutil.files

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo

class LocalFileManager: FileManager {

    override fun read(path: String): ByteArray {
        return Files.readAllBytes(Path.of(path))
    }

    override fun write(path: String, content: ByteArray) {
        Files.createDirectories(Path.of(path).parent)
        Files.write(Path.of(path), content)
    }

    override fun delete(path: String) {
        Files.delete(Path.of(path))
    }

    override fun joinPath(parent: String, child: String): String {
        return Path.of(parent).resolve(child).toString()
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