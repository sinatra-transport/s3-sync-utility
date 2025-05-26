package cl.emilym.s3syncutil.models

import cl.emilym.s3syncutil.models.proto.FileIndex

data class Index(
    val files: Map<String, FileInfo>
) {

    companion object {

        fun fromPb(pb: FileIndex.Index): Index {
            return Index(
                pb.filesList.map { FileInfo.fromPb(it) }.associateBy { it.path }
            )
        }

        fun fromFiles(files: List<FileInfo>): Index {
            return Index(files.associateBy { it.path })
        }

    }

    fun toPb(): FileIndex.Index {
        return FileIndex.Index.newBuilder()
            .addAllFiles(files.values.map { it.toPb() })
            .build()
    }

}

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
