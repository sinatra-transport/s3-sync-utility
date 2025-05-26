package cl.emilym.s3syncutil.models

sealed interface FileChange {
    val path: String

    data class Create(
        override val path: String,
        val sha: String
    ): FileChange

    data class Update(
        override val path: String,
        val sha: String
    ): FileChange

    data class Delete(
        override val path: String
    ): FileChange
}