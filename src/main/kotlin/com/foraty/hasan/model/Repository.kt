package com.foraty.hasan.model

import java.util.*
import kotlin.collections.LinkedHashSet


val users = Collections.synchronizedSet<User>(LinkedHashSet())

val histories = Collections.synchronizedSet<History>(LinkedHashSet())