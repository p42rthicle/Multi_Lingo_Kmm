//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Parth Takkar on 22/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var onLanguageSelected: (UiLanguage) -> Void
    var body: some View {
        Menu {
            VStack {
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.languageCode) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: { onLanguageSelected(language) })
                    
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                Text(language.language.languageName)
                    .foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(.lightBlue)
            }
        }
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: UiLanguage(language: .german, imageName: "german"),
            isOpen: true,
            onLanguageSelected: {language in})
    }
}
