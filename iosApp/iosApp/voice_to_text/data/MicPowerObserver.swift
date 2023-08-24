//
//  MicPowerObserver.swift
//  iosApp
//
//  Created by Parth Takkar on 24/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class MicPowerObserver: ObservableObject {
    private var cancellable: AnyCancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    private let powerRatiosPerSecond = 50.0
    
   func startObserving()
    {
        do {
            let recorderSettings : [String: Any] = [
                AVFormatIDKey : NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            let recorder = try AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null", isDirectory: true), settings: recorderSettings)
            recorder.isMeteringEnabled =  true
            recorder.record()
            self.audioRecorder =  recorder
            
            self.cancellable = Timer.publish(
                every: 1.0/powerRatiosPerSecond,
                tolerance: 1.0/powerRatiosPerSecond,
                on: .main, in: .common
            )
            .autoconnect()
            .sink { [weak self] _ in
                recorder.updateMeters()
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                } else {
                    let normalisedOffset = CGFloat(50 + powerOffset) / 50
                    self?.micPowerRatio = normalisedOffset
                }
            }
        } catch {
            print("An error occurred\(error.localizedDescription)")
        }
    }
    
    func release() {
        cancellable = nil
        audioRecorder = nil
        micPowerRatio = 0.0
    }
    
}
