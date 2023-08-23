//
//  TimestampFormatter.swift
//  iosApp
//
//  Created by Parth Takkar on 23/08/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

extension Int64 {
    func toFormattedString() -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMM yy, h:mm a"
        formatter.locale = Locale(identifier: "en_US_POSIX")
        let date = Date(timeIntervalSince1970: TimeInterval(self) / 1000) // Convert milliseconds to seconds
        return formatter.string(from: date)
    }
}
