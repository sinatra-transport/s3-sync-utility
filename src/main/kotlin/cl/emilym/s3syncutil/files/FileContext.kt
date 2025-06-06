package cl.emilym.s3syncutil.files

enum class FileContext {
    LOCAL, S3;

    companion object {

        fun forPath(path: String): FileContext = when {
            path.startsWith("s3://") -> S3
            else -> LOCAL
        }

    }

}