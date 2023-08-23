//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let isTranslating: Bool
    let fromUiLanguage: UiLanguage
    let toUiLanguage: UiLanguage
    let onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(fromText: $fromText, isTranslating: isTranslating, onTranslateEvent: onTranslateEvent)
                .gradientSurface()
                .cornerRadius(14)
                .animation(.easeInOut, value: isTranslating)
                .shadow(radius: 4)
        } else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromUiLanguage: fromUiLanguage,
                toUiLanguage: toUiLanguage,
                onTranslateEvent: onTranslateEvent)
            .padding()
            .gradientSurface()
            .cornerRadius(14)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

struct TranslateTextField_Previews: PreviewProvider {
    static var previews: some View {
        TranslateTextField(
            fromText: Binding(
                get: { "test value"},
                set: {value in }
            ),
            toText: "Translated text",
            isTranslating: false,
            fromUiLanguage: UiLanguage(language: .english, imageName: "english"),
            toUiLanguage: UiLanguage(language: .german, imageName: "german"),
            onTranslateEvent: { event in })
    }
}

private extension TranslateTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        let isTranslating: Bool
        let onTranslateEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(maxWidth: .infinity,
                minHeight: 180,
                       alignment: .topLeading)
                .padding()
                .foregroundColor(Color.onSurface)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(text: "Translate", isLoading: isTranslating, onClick: {
                        onTranslateEvent(TranslateEvent.Translate())
                    })
                    .padding(.trailing)
                    .padding(.bottom)
                }
                .onAppear {
                    UITextView.appearance().backgroundColor = .clear
                }
        }
    }
    
    struct TranslatedTextField: View {
        let fromText: String
        let toText: String
        let fromUiLanguage: UiLanguage
        let toUiLanguage: UiLanguage
        let onTranslateEvent: (TranslateEvent) -> Void
        
        private let tts = TextToSpeech()
        
        var body: some View {
            VStack(alignment: .leading) {
                LanguageIconDisplay(language: fromUiLanguage)
                Text(fromText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(fromText, forPasteboardType: UTType.plainText.identifier)
                    }) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    Button(action: {
                        onTranslateEvent(TranslateEvent.StopTranslation())
                    }) {
                        Image(systemName: "xmark")
                            .foregroundColor(.lightBlue)
                    }
                }
                Divider().padding()
                LanguageIconDisplay(language: toUiLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(Color.onSurface)
                
            HStack {
                Spacer()
                Button(action: {
                    UIPasteboard.general.setValue(toText, forPasteboardType: UTType.plainText.identifier)
                }) {
                    Image(uiImage: UIImage(named: "copy")!)
                        .renderingMode(.template)
                        .foregroundColor(.lightBlue)
                }
                Button(action: {
                    tts.speak(text: toText, language: toUiLanguage.language.languageCode)
                }) {
                    Image(systemName: "speaker.wave.2")
                        .foregroundColor(.lightBlue)
                }
            }
            }
        }
    }
}
