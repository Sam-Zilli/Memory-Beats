//
// Created by sam on 7/17/23.
//

#ifndef CS5520_FINAL_PROJECT_OSCILLATOR_H
#define CS5520_FINAL_PROJECT_OSCILLATOR_H
#include <atomic>
#include <stdint.h>

class Oscillator {
public:
    void setWaveOn(bool isWaveOn);
    void setSampleRate(int32_t sampleRate);
    void render(float *audioData, int32_t numFrames);

    // add a new method that sets the frequency according to input
    //void setFrequency(float frequency);

//***********************************************************************************************//
    void setFrequencies(float frequency1, float frequency2);

private:
    std::atomic<bool> isWaveOn_{false};
    //double phase_ = 0.0;
    //double phaseIncrement_ = 0.0;

    // set default value for frequency_ and sampleRate_
    // 44100 is the standard sample rate
    //float frequency_ = 440.0;
    int32_t sampleRate_ = 44100;

    // create method that will calculate the new phase increment based on frequency
    void calcPhaseIncrement();

    //***********************************************************************************************//
    float frequency1_;
    float frequency2_;
    double phase1_ = 0.0;
    double phase2_ = 0.0;
    double phaseIncrement1_ = 0.0;
    double phaseIncrement2_ = 0.0;
    void calculatePhaseIncrement();
};


#endif //WAVEMAKER_OSCILLATOR_H
