package com.example.demo.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Hoge {
    private String myName;
    private int seqNo;
    private MyClass myClass;
    private List<MyClass2> myClasses;
}
