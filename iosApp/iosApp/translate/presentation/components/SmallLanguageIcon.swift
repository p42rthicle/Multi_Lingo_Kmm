//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by Parth Takkar on 22/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLanguageIcon: View {
    var language: UiLanguage
    var body: some View {
        Image(uiImage: UIImage(named: language.imageName.lowercased())!)
            .resizable()
            .frame(width: 24, height: 24)
    }
}

struct SmallLanguageIcon_Previews: PreviewProvider {
    static var previews: some View {
        SmallLanguageIcon(
            language: UiLanguage(language: .german, imageName: "german")
        )
    }
}
