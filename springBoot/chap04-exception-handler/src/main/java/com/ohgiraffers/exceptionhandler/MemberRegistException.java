package com.ohgiraffers.exceptionhandler;

public class MemberRegistException extends RuntimeException {
    public MemberRegistException(String message) {
        super(message);
    }
}
