//
//  SwapLanguagesButton.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SwapLanguagesButton: View {
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            Image(uiImage: UIImage(named: "swap_languages")!)
                .padding()
                .background(Color.primaryColor)
                .clipShape(Circle())
        }
    }
}

struct SwapLanguagesButton_Previews: PreviewProvider {
    static var previews: some View {
        SwapLanguagesButton(onClick: {})
    }
}
