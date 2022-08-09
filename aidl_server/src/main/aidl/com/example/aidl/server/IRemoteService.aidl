// IRemoteService.aidl
package com.example.aidl.server;
import com.example.aidl.server.Rect;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getPid();

    void addRectInOut(inout Rect rect);
}
