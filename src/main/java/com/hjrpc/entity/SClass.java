package com.hjrpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.msgpack.annotation.Message;

import java.util.Set;

@Data
@AllArgsConstructor
@Message
@NoArgsConstructor
@ToString
public class SClass {
    private String className;
    private String classLever;
    private Set<Student> students;
}
