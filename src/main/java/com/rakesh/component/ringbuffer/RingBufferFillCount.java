package com.rakesh.component.ringbuffer;

/**
 * Created by mac on 3/4/17.
 */
public class RingBufferFillCount<T> {

    public T[] ringBufferQueue;
    public int writePosition;
    public int noElems;
    public int size;

    RingBufferFillCount(int size){
        ringBufferQueue = (T[])new Object[size];
        this.size = size;
        writePosition = 0;
        noElems = 0;
    }

    private boolean isBufferFilled(){
        return noElems == size;
    }

    private boolean isBufferEmpty(){
        return noElems == 0;
    }

    public boolean put(T elem){

        if(isBufferFilled()){
            return false;
        }

        if(writePosition == size )
            writePosition = 0;

        ringBufferQueue[writePosition] = elem;

        noElems++;
        writePosition++;

        return true;

    }


    public T get(){

        if(isBufferEmpty())
            return null;

        int readPosition = writePosition - noElems;
        if(readPosition < 0){
            readPosition = readPosition + size;
        }

        noElems--;
        return ringBufferQueue[readPosition];
    }

}
