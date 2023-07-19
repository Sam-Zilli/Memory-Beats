#include <jni.h>
#include <string>
#include <android/input.h>
#include "AudioEngine.h"
static AudioEngine *audioEngine = new AudioEngine();


extern "C" {

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_MainActivity_touchEvent(JNIEnv *env, jobject obj, jint action) {
    switch (action) {
        case AMOTION_EVENT_ACTION_DOWN:
            audioEngine->setToneOn(true);
            //audioEngine->setFrequency(261.63f);
            audioEngine->setFrequencies(261.63f, 329.63f);
            //audioEngine->setFrequencies(293.665f, 329.00f);
            break;
        case AMOTION_EVENT_ACTION_UP:
            audioEngine->setToneOn(false);
            break;
        default:
            break;
    }
}

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_MainActivity_startEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->start();
}

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_MainActivity_stopEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->stop();
}
}