//
// Created by sam on 7/17/23.
//

#include "Oscillator.h"
#include <math.h>

#define TWO_PI (3.14159 * 2)
#define AMPLITUDE 0.3


// numFrames is the number of audio frames which we must render
// phase_ stores the current wave phase, and it is incremented by phaseIncrement_ after each sample is generated
// if isWaveOn_is false -> just output zeroes (silence)

void Oscillator::setSampleRate(int32_t sampleRate) {
    sampleRate_ = sampleRate;

    // phase increment is also dependent on sample rate
    calcPhaseIncrement();
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

void Oscillator::setFrequency(float frequency) {
    frequency_ = frequency;

    // after updating frequency to input, we update phase increment accordingly since
    // phase increment is dependent on both the frequency and sample rate
    calcPhaseIncrement();
}


void Oscillator::calcPhaseIncrement() {
    phaseIncrement_ = (TWO_PI * frequency_) / (double) sampleRate_;
}