//
//  ProgressButton.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ProgressButton: View {
    var text: String
    var isLoading: Bool
    var onClick: () -> Void
    
    var body: some View {
        Button(action: {
            if !isLoading {
                onClick()
            }
        }) {
            if isLoading {
                ProgressView()
                    .animation(.easeInOut, value: isLoading)
                    .padding(6)
                    .background(Color.primaryColor)
                    .cornerRadius(100)
                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
            } else {
                Text(text.uppercased())
                    .animation(.easeInOut, value: isLoading)
                    .padding(.horizontal)
                    .padding(.vertical, 6)
                    .font(.body.weight(.bold))
                    .background(Color.primaryColor)
                    .foregroundColor(Color.onPrimary)
                    .cornerRadius(100)
            }
        }
    }
}

struct ProgressButton_Previews: PreviewProvider {
    static var previews: some View {
        ProgressButton(text: "Translate", isLoading: false, onClick: {})
    }
}
