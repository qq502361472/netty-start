package com.hjrpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.msgpack.annotation.Message;

@Data
@AllArgsConstructor
@Message
@NoArgsConstructor
@ToString
public class Student {
    private String name;
    private String age;
}
