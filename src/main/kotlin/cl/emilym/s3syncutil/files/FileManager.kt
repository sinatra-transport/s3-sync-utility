package cl.emilym.s3syncutil.files

interface FileManager {

    fun read(path: String): ByteArray
    fun write(path: String, content: ByteArray)
    fun delete(path: String)

    fun joinPath(parent: String, child: String): String

    fun scan(root: String, visitors: List<FilesystemVisitor>)

    companion object {

        fun get(context: FileContext): FileManager {
            return LocalFileManager()
        }

    }

}

