LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := system_properties
LOCAL_SRC_FILES := system_properties.c
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)