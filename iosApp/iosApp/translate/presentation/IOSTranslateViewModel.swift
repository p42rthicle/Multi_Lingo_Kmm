//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen {
    @MainActor class IOSTranslateViewModel: ObservableObject {
        private var historyDataSource: TranslationHistoryDataSource
        private var translateUseCase: Translate
        
        private let viewModel: TranslateViewModel
        
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            fromUiLanguage: UiLanguage(language: .english, imageName: "english"),
            toUiLanguage: UiLanguage(language: .hindi, imageName: "hindi"),
            isTranslating: false,
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            historyList: []
        )
        private var disposableHandle: DisposableHandle?
        
        init(historyDataSource: TranslationHistoryDataSource, translateUseCase: Translate) {
            self.historyDataSource = historyDataSource
            self.translateUseCase = translateUseCase
            self.viewModel = TranslateViewModel(
                translateUseCase: translateUseCase,
                historyDataSource: historyDataSource,
                coroutineScope: nil
            )
        }
        
        func onEvent(event: TranslateEvent) {
            self.viewModel.onEvent(event: event)
        }
        
        func startObserving() {
            disposableHandle = viewModel.state.subscribe(onCollect: { state in
                if let state = state {
                    self.state = state
                }
            })
        }
        
        func dispose() {
            disposableHandle?.dispose()
        }
    }
}
