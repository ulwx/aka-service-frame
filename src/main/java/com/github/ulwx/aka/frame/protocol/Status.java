package com.github.ulwx.aka.frame.protocol;

public enum Status {
    FAIL(0),
    SUCCESS(1);
    private int status;

    Status(int status) {
        this.status = status;
    }
    public int value(){
        return status;
    }
    public static void main(String[] args) {
        System.out.println(SUCCESS.name());
    }
}
