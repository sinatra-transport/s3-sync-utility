package cl.emilym.s3syncutil.models

import cl.emilym.s3syncutil.models.proto.FileIndex

data class FileInfo(
    val path: String,
    val sha: String
) {

    companion object {

        fun fromPb(pb: FileIndex.FileInfo): FileInfo {
            return FileInfo(
                pb.path,
                pb.sha
            )
        }

    }

    fun toPb(): FileIndex.FileInfo {
        return FileIndex.FileInfo.newBuilder()
            .setPath(path)
            .setSha(sha)
            .build()
    }

}
