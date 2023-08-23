//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    let historyItem : UiTranslationHistoryItem
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading) {
                HStack(alignment: .center) {
                    Spacer()
                    Text(historyItem.timestamp.toFormattedString())
                        .font(.system(size: 12, weight: .light))
                    
                        .foregroundColor(Color.onSurface)
                }
                .frame(maxWidth: .infinity)
                HStack {
                    SmallLanguageIcon(language: historyItem.fromUiLanguage)
                        .padding(.trailing)
                    Text(historyItem.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom)
                HStack {
                    SmallLanguageIcon(language: historyItem.toUiLanguage)
                        .padding(.trailing)
                    Text(historyItem.toText)
                        .foregroundColor(.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(14)
            .shadow(radius: 4)
        }
    }
    
    struct TranslateHistoryItem_Previews: PreviewProvider {
        static var previews: some View {
            TranslateHistoryItem(
                historyItem: UiTranslationHistoryItem(
                    id: 0,
                    fromText: "Hello",
                    toText: "Hallo",
                    fromUiLanguage: UiLanguage(
                        language: .german,
                        imageName: "german"),
                    toUiLanguage: UiLanguage(
                        language: .english,
                        imageName: "english"),
                    timestamp: Int64(Date().timeIntervalSince1970)),
                onClick: {})
        }
    }
}
