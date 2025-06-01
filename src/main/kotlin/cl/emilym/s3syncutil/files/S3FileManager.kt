package cl.emilym.s3syncutil.files

import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URI

class S3FileManager(
    private val s3: S3Client = S3Client.create()
): FileManager {

    override fun read(path: String): ByteArray {
        val uri = s3.utilities().parseUri(URI.create(path))
        return s3.getObject { builder ->
            builder.bucket(uri.bucket().get())
            builder.key(uri.key().get())
        }.readAllBytes()
    }

    override fun write(path: String, content: ByteArray) {
        val uri = s3.utilities().parseUri(URI.create(path))
        s3.putObject(
            PutObjectRequest.builder()
                .bucket(uri.bucket().get())
                .key(uri.key().get())
                .build(),
            RequestBody.fromBytes(content)
        )
    }

    override fun delete(path: String) {
        val uri = s3.utilities().parseUri(URI.create(path))
        s3.deleteObject { builder ->
            builder.bucket(uri.bucket().get())
            builder.key(uri.key().get())
        }
    }

    override fun joinPath(parent: String, child: String): String {
        return URI.create(parent).resolve(child).toString()
    }

    override fun scan(
        root: String,
        visitors: List<FilesystemVisitor>
    ) {
        val uri = s3.utilities().parseUri(URI.create(root))
        val objects = s3.listObjectsV2Paginator { builder ->
            builder.bucket(uri.bucket().get())
            builder.prefix(uri.key().orElse(""))
        }
        for (o in objects.contents()) {
            val path = "s3://${uri.bucket().get()}/${o.key()}"
            val content = read(path)
            visitors.forEach { it.visit(
                path.substring(root.length),
                content
            ) }
        }
    }

}