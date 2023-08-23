//
//  LanguageIconDisplay.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageIconDisplay: View {
    var language: UiLanguage
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 4)
            Text(language.language.languageName)
                .foregroundColor(.lightBlue)
        }
    }
}

struct LanguageIconDisplay_Previews: PreviewProvider {
    static var previews: some View {
        LanguageIconDisplay(language: UiLanguage(language: .german, imageName: "hindi"))
    }
}
