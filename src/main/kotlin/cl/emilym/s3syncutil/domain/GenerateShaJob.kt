package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.models.FileInfo
import java.security.MessageDigest

class GenerateShaJob(
    private val scanner: FilesystemScanner
) {

    operator fun invoke(path: String): List<FileInfo> {
        val files = mutableListOf<FileInfo>()
        val digest = MessageDigest.getInstance("SHA-256")

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

        return files.toList()
    }

}