//
// Created by sam on 7/17/23.
//

#include "Oscillator.h"
#include <math.h>

#define TWO_PI (3.14159 * 2)
#define AMPLITUDE 0.3
#define FREQUENCY 440.0

// numFrames is the number of audio frames which we must render
// phase_ stores the current wave phase, and it is incremented by phaseIncrement_ after each sample is generated
// if isWaveOn_is false -> just output zeroes (silence)

void Oscillator::setSampleRate(int32_t sampleRate) {
    phaseIncrement_ = (TWO_PI * FREQUENCY) / (double) sampleRate;
}

void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {
    if(!isWaveOn_.load()) phase_ = 0;

    for(int i = 0; i < numFrames; i++) {
        if(isWaveOn_.load()) {
            // calculate the next sample value for the sine wave
            audioData[i] = (float) (sin(phase_) * AMPLITUDE);

            // Increments the phase, handling wrap around
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;
        } else {
            // Outputs silence by setting sample value to zero
            audioData[i] = 0;
        }
    }
}
