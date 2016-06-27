//
// Created by pillar on 3/31/2016.
//

#ifndef NATIVECODE_SYSTEM_PROPERTIES_H
#define NATIVECODE_SYSTEM_PROPERTIES_H

extern "C++"{
JNIEXPORT jstring JNICALL Java_edu_ntu_android_learning_nativecode_MainActivity_getDeviceId(JNIEnv *env, jclass);
};

#endif //NATIVECODE_SYSTEM_PROPERTIES_H
