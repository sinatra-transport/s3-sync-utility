package cl.emilym.s3syncutil.domain

import cl.emilym.s3syncutil.models.FileChange
import cl.emilym.s3syncutil.models.Index

class CompareIndexJob {

    operator fun invoke(
        old: Index,
        _new: Index
    ): List<FileChange> {
        val keys = (old.files.keys + _new.files.keys).distinct()

        return keys.mapNotNull { k ->
            when {
                old.files.containsKey(k) && !_new.files.containsKey(k) ->
                    FileChange.Delete(k)
                !old.files.containsKey(k) && _new.files.containsKey(k) ->
                    FileChange.Create(k, _new.files[k]!!.sha)
                old.files[k]!!.sha != _new.files[k]!!.sha ->
                    FileChange.Update(k, _new.files[k]!!.sha)
                else -> null
            }
        }
    }

}