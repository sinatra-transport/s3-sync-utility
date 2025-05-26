package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.models.FileInfo
import cl.emilym.s3syncutil.models.Index
import cl.emilym.s3syncutil.models.proto.FileIndex
import kotlin.io.path.Path

class ReadTripIndexJob(
    private val manager: FileManager
) {

    operator fun invoke(path: String): Index {
        return Index.fromPb(FileIndex.Index.parseFrom(manager.read(path)))
    }

}