package com.web.framework.test.thread;

/**
 * Created by zhengxianyou on 2018/4/1 0001.
 */
public class ClientThread extends Thread {
    private Sequence sequence;

    public ClientThread(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
       for (int i=0;i<3;i++){
           System.out.println(Thread.currentThread().getName()+"=>"+sequence.getNumber());
       }
    }
}
