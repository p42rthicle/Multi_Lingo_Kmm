//
//  Translatescreen.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: TranslationHistoryDataSource
    private var translateUseCase: Translate
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSource: TranslationHistoryDataSource,
         translateUseCase: Translate) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource,
            translateUseCase: translateUseCase
         )
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropDown(
                        language: viewModel.state.fromUiLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        onLanguageSelected: { language in
                            viewModel.onEvent(
                                event: TranslateEvent.FromLanguageChosen(fromUiLanguage: language)
                            )
                        }
                    )
                    Spacer()
                    SwapLanguagesButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                        }
                    )
                    Spacer()
                    LanguageDropDown(
                        language: viewModel.state.toUiLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        onLanguageSelected: { language in
                            viewModel.onEvent(
                                event: TranslateEvent.ToLanguageChosen(toUiLanguage: language)
                            )
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
