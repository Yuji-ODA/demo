package com.example.demo.controller.form;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HogeSend {
    String myName;
    int seqNo;
    ByteArrayResource file;
    MyClassSend myClass;
    List<MyClass2> myClasses;
}
