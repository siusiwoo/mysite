package com.study.mySite;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
 
@RequiredArgsConstructor
@Getter
public class LombokTest {
	private final String name;
	private final int lombok;

    public static void main(String[] args) {
    	LombokTest lombok = new LombokTest("테스트",10);
    	System.out.print(lombok.getName());
    }
}