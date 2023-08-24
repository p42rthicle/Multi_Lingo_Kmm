//
//  VoiceToTextScreen.swift
//  iosApp
//
//  Created by Parth Takkar on 25/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct VoiceToTextScreen: View {
    private let onVoiceResult: (String) -> Void
    
    @ObservedObject var viewModel: IosVoiceToTextViewModel
    private let parser: any VoiceToTextParser
    private let languageCode: String
    
    @Environment(\.presentationMode) var presentation
    
    init(onVoiceResult: @escaping (String) -> Void, parser: any VoiceToTextParser, languageCode: String) {
        self.onVoiceResult = onVoiceResult
        self.parser = parser
        self.languageCode = languageCode
        self.viewModel = IosVoiceToTextViewModel(parser: parser, languageCode: languageCode)
    }
    
    var body: some View {
        VStack {
            Spacer()
            mainView
            Spacer()
            
            HStack {
                Spacer()
                VoiceRecorderActionButton(
                    displayState: viewModel.state.displayState,
                    onClick: {
                        if viewModel.state.displayState != .displayingResults {
                            viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))
                        } else {
                            onVoiceResult(viewModel.state.spokenTextResult ?? "")
                            self.presentation.wrappedValue.dismiss()
                        }
                    }
                )
                if viewModel.state.displayState == .displayingResults {
                    Button(action: {
                        viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))
                    }) {
                        Image(systemName: "arrow.clockwise")
                            .foregroundColor(.lightBlue)
                    }
                }
                Spacer()
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
        .background(Color.background)
    }
    
    var mainView: some View {
        switch viewModel.state.displayState {
            case .waitingToSpeak:
                return AnyView(
                    Text("Click record and start talking.")
                        .font(.title2)
                )
            case .displayingResults:
                return AnyView(
                    Text(viewModel.state.spokenTextResult ?? "")
                        .font(.title2)
                )
            case .error:
                return AnyView(
                    Text(viewModel.state.recordError ?? "Unknown error")
                        .font(.title2)
                        .foregroundColor(.red)
                )
            case .speaking:
                return AnyView(
                    VoiceRecorderWaveDisplay(
                        powerRatios: viewModel.state.powerRatios.map { Double(truncating: $0) }
                    )
                    .frame(maxHeight: 100)
                    .padding()
                )
            default: return AnyView(EmptyView())
        }
    }
}
