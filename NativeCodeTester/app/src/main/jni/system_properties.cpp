//
// Created by pillar on 3/31/2016.
//
extern "C++"{
#include "system_properties.h"

JNIEXPORT jstring JNICALL Java_edu_ntu_android_learning_nativecode_MainActivity_getDeviceId(JNIEnv *env, jclass cls){
    //returns the string length of the value.
    int ir = __system_property_get("ro.gsm.imei", imei_start);

    if(ir > 0)
    {
        imei_start[15]=0;//strz end
        printf("method1 got imei %s len %d\r\n",imei_start,strlen(imei_start));
        strcpy(g_imei,imei_start);
    }
    else
    {
        printf("method1 imei failed - trying method2\r\n");
        //old dumpsys imei getter
        char* res = exec_get_out("dumpsys iphonesubinfo");
        const char* imei_start_match = "ID = ";
        int imei_start_match_len = strlen(imei_start_match);
        char* imei_start = strstr(res,imei_start_match);
        if(imei_start && strlen(imei_start)>=15+imei_start_match_len)
        {
            imei_start += imei_start_match_len;
            imei_start[15] = 0;
            printf("method2 IMEI [%s] len %d\r\n",imei_start,strlen(imei_start));
            strcpy(g_imei,imei_start);
        }
    }

}
}

