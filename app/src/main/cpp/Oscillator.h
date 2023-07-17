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

private:
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double phaseIncrement_ = 0.0;

};


#endif //WAVEMAKER_OSCILLATOR_H
