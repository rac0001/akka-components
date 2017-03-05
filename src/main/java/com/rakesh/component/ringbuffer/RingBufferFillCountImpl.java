package com.rakesh.component.ringbuffer;

/**
 * Created by mac on 3/4/17.
 */
public class RingBufferFillCountImpl {

    public static void main(String args[]){


        RingBufferFillCount<String> ringBufferFillCount = new RingBufferFillCount<String>(9);


        ringBufferFillCount.put("A");
        ringBufferFillCount.put("C");
        ringBufferFillCount.put("B");
        ringBufferFillCount.put("F");
        ringBufferFillCount.put("D");
        ringBufferFillCount.put("E");
        ringBufferFillCount.put("H");
        ringBufferFillCount.put("I");
        ringBufferFillCount.put("G");

        System.out.println("---getting---");

        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());
        ringBufferFillCount.put("Z");
        System.out.println(ringBufferFillCount.get());
        System.out.println(ringBufferFillCount.get());


    }


}
