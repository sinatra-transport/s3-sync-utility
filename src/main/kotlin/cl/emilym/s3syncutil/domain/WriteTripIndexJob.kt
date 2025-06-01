package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.files.FileManager
import cl.emilym.s3syncutil.models.Index

class WriteTripIndexJob(
    private val manager: FileManager
) {

    operator fun invoke(path: String, index: Index) {
        manager.write(
            path,
            index.toPb().toByteArray()
        )
    }

}