package com.hjrpc.util

import com.hjrpc.entity.SClass
import com.hjrpc.entity.Student
import com.hjrpc.util.MessageUtil.random

import java.util.HashSet
import java.util.Random

object MessageUtil {
    internal var random = Random()

    val classInfo: SClass
        get() {
            val set = HashSet<Student>()
            for (i in 0..2) {
                val student = Student("zhangsan" + random.nextInt(10), (15 + random.nextInt(10)).toString())
                set.add(student)
            }

            return SClass(random.nextInt(10).toString() + "班", random.nextInt(10).toString() + "年级", set)
        }
}
