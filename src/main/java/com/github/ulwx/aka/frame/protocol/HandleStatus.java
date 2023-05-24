package com.github.ulwx.aka.frame.protocol;

public enum HandleStatus {
     init(0),// 初始
     suc(24),// 处理成功
     fail(23),// 处理失败
     doing(134); // 处理中
    private int status;

     HandleStatus(int status) {
        this.status = status;
    }

    public int value(){
        return status;
    }

    public static void main(String[] args) {
        System.out.println(init);
    }
}
