package com.hjrpc.util;

import com.hjrpc.entity.SClass;
import com.hjrpc.entity.Student;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MessageUtil {
    static Random random = new Random();

    public static SClass getClassInfo() {
        Set<Student> set = new HashSet<Student>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student("zhangsan" + random.nextInt(10), String.valueOf(15 + random.nextInt(10)));
            set.add(student);
        }

        SClass clazz = new SClass(random.nextInt(10) + "班", random.nextInt(10) + "年级", set);
        return clazz;
    }
}
