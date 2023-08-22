//
//  Colors.swift
//  iosApp
//
//  Created by Parth Takkar on 22/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension Color {
    init(hex: Int64, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: alpha
        )
    }
    private static let sharedColors = SharedColors()
    static let lightBlue = Color(hex:sharedColors.LightBlue)
    static let lightBlueGrey = Color(hex:sharedColors.LightBlueGrey)
    static let darkGrey = Color(hex:sharedColors.DarkGrey)
    static let violetAccent = Color(hex:sharedColors.VioletAccent)
    static let textBlackOnWhite = Color(hex:sharedColors.TextBlackOnWhite)
    
    static let primary = Color(light: .accentColor, dark: .accentColor)
    static let onPrimary = Color(light: .white, dark: .white)
    static let background = Color(light: .lightBlueGrey, dark: .darkGrey)
    static let onBackground = Color(light: .textBlackOnWhite, dark: .white)
    static let surface = Color(light: .white, dark: .darkGrey)
    static let onSurface = Color(light: .textBlackOnWhite, dark: .white)
}

private extension Color {
    init(light: Self, dark: Self) {
        self.init(uiColor: UIColor(
            light: UIColor(light), dark: UIColor(dark)))
    }
}

private extension UIColor {
    convenience init(light: UIColor, dark: UIColor) {
        self.init { traits in
            switch traits.userInterfaceStyle {
            case .light, .unspecified:
                return light
            case .dark:
                return dark
            @unknown default:
                return light
            }
        }
    }
}
