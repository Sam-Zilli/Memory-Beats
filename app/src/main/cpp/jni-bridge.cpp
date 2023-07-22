#include <jni.h>
#include <string>
#include <android/input.h>
#include "AudioEngine.h"
static AudioEngine *audioEngine = new AudioEngine();


extern "C" {

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_GameplayActivity_touchEvent(JNIEnv *env, jobject obj, jint action) {
    jclass gameplayActivityClass = env->GetObjectClass(obj);

    // the three parameters are class reference, name is the name of the variable we want to access (name is the same as one defined in java class)
    // sig is the signature of field's type
    jfieldID frequency1FieldId = env->GetFieldID(gameplayActivityClass, "frequency1", "F");
    jfieldID frequency2FieldId = env->GetFieldID(gameplayActivityClass, "frequency2", "F");

    // obj -> reference to java object from which we want to retrieve float value
    // fieldID -> corresponds to the specific float variable which want to get the value of
    float frequency1 = env->GetFloatField(obj, frequency1FieldId);
    float frequency2 = env->GetFloatField(obj,frequency2FieldId);

    switch (action) {
        case AMOTION_EVENT_ACTION_DOWN:
            audioEngine->setToneOn(true);
            audioEngine->setFrequencies(frequency1, frequency2);
            break;
        case AMOTION_EVENT_ACTION_UP:
            audioEngine->setToneOn(false);
            break;
        default:
            break;
    }
}

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_GameplayActivity_startEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->start();
}

JNIEXPORT void JNICALL
Java_edu_northeastern_cs5520finalproject_GameplayActivity_stopEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->stop();
}
}
