//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by Parth Takkar on 22/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDownItem: View {
    var language: UiLanguage
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            HStack {
                if let image = UIImage(named: language.imageName.lowercased()) {
                    Image(uiImage: image)
                        .resizable()
                        .frame(width: 34, height: 34)
                        .padding(.trailing, 4)
                    Text(language.language.languageName)
                        .foregroundColor(.textBlackOnWhite)
                }
            }
        }
    }
}

struct LanguageDropDownItem_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDownItem(
            language: UiLanguage(language: .german, imageName: "german"), onClick: {})
    }
}
