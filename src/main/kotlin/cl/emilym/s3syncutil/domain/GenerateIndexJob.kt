package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.files.FilesystemVisitor
import cl.emilym.s3syncutil.models.FileInfo
import cl.emilym.s3syncutil.models.Index
import java.security.MessageDigest

class GenerateIndexJob(
    private val scanner: FileManager
) {

    companion object {
        private val digest = MessageDigest.getInstance("SHA-256")
    }

    operator fun invoke(path: String): Index {
        val files = mutableListOf<FileInfo>()
        scanner.scan(path, listOf(
            object : FilesystemVisitor {
                override fun visit(path: String, content: ByteArray) {
                    files += FileInfo(
                        path,
                        digest.digest(content).toString(Charsets.UTF_8)
                    )
                }
            }
        ))

        return Index.fromFiles(files)
    }

}