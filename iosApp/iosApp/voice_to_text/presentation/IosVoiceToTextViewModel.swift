//
//  IosVoiceToTextViewModel.swift
//  iosApp
//
//  Created by Parth Takkar on 24/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

@MainActor class IosVoiceToTextViewModel : ObservableObject {
    private var parser: any VoiceToTextParser
    private let languageCode: String
    
    private let viewModel: VoiceToTextViewModel
    
    @Published var state = VoiceToTextState(powerRatios: [], spokenTextResult: "", canRecord: false, recordError: nil, displayState: .waitingToSpeak)
    private var handle: DisposableHandle?
    
    init(parser: VoiceToTextParser, languageCode: String) {
        self.parser = parser
        self.languageCode = languageCode
        self.viewModel = VoiceToTextViewModel(parser: parser, coroutineScope: nil)
        self.viewModel.onEvent(event: VoiceToTextEvent.PermissionsUpdated(isGranted: true, isPermanentlyDenied: false)
        )
    }
    
    func onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event: event)
    }
    
    func startObserving() {
        handle = viewModel.state.subscribe { [weak self] state in
            if let state {
                self?.state = state
            }
        }
    }
    
    func dispose() {
        handle?.dispose()
        onEvent(event: VoiceToTextEvent.Reset())
    }
    
}
