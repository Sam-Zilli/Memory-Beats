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
    void setFrequencies(float frequency1, float frequency2);

private:
    std::atomic<bool> isWaveOn_{false};
    int32_t sampleRate_ = 44100;

    float frequency1_;
    float frequency2_;
    double phase1_ = 0.0;
    double phase2_ = 0.0;
    double phaseIncrement1_ = 0.0;
    double phaseIncrement2_ = 0.0;
    // create method that will calculate the new phase increment based on frequency
    void calculatePhaseIncrement();
};


#endif //WAVEMAKER_OSCILLATOR_H
