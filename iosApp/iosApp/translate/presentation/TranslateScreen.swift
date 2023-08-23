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
                
                TranslateTextField(
                    fromText: Binding(get: { viewModel.state.fromText }, set: { value in
                        viewModel.onEvent(event: TranslateEvent.TranslationTextChanged(fromText: value))
                    }),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromUiLanguage: viewModel.state.fromUiLanguage,
                    toUiLanguage: viewModel.state.toUiLanguage,
                    onTranslateEvent: { event in viewModel.onEvent(event: event)}
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.historyList.isEmpty {
                    Text("History")
                        .font(.title2)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                }
                
                ForEach(viewModel.state.historyList, id: \.self.id) { historyItem in
                    TranslateHistoryItem(historyItem: historyItem, onClick: {
                        viewModel.onEvent(event: TranslateEvent.HistoryItemChosen(historyItem: historyItem))
                    })
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack {
                Spacer()
                NavigationLink(destination: Text("Voice to text screen")) {
                    ZStack {
                        Circle()
                            .foregroundColor(.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundColor(.onPrimary)
                    }
                    .frame(maxWidth: 100, maxHeight: 100)
                }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
