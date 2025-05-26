package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.models.FileChange
import cl.emilym.s3syncutil.models.Index

class CompareIndexJob {

    operator fun invoke(
        old: Index,
        new: Index
    ): List<FileChange> {
        val keys = (old.files.keys + new.files.keys).distinct()

        return keys.mapNotNull { k ->
            when {
                old.files.containsKey(k) && !new.files.containsKey(k) ->
                    FileChange.Delete(k)
                !old.files.containsKey(k) && new.files.containsKey(k) ->
                    FileChange.Create(k, new.files[k]!!.sha)
                old.files[k]!!.sha != new.files[k]!!.sha ->
                    FileChange.Update(k, new.files[k]!!.sha)
                else -> null
            }
        }
    }

}