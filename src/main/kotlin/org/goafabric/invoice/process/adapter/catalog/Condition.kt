package org.goafabric.invoice.process.adapter.catalog

@JvmRecord
data class Condition(
    val id: String?,
    val version: Long?,
    val code: String,
    val display: String,
    val shortname: String
) 