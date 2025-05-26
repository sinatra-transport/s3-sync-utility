package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.models.FileInfo
import cl.emilym.s3syncutil.models.proto.FileIndex
import kotlin.io.path.Path

class WriteTripIndexJob(
    private val manager: FileManager
) {

    operator fun invoke(path: String, index: List<FileInfo>) {
        manager.write(
            path,
            FileIndex.Index.newBuilder()
                .addAllFiles(index.map { it.toPb() })
                .build()
                .toByteArray()
        )
    }

}