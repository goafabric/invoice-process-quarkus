package org.goafabric.invoice.process.adapter.authorization

import jakarta.ws.rs.QueryParam


interface LockAdapter {
    fun acquireLockByKey(@QueryParam("lockKey") lockKey: String): Lock

    fun removeLockById(@QueryParam("lockId") lockId: String)
}