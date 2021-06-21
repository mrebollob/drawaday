//
//  ImageCard.swift
//  DrawADay
//
//  Created by Manuel Baez on 20/06/2021.
//

import SwiftUI
import shared

struct ImageCard: View {
    
    var body: some View {
        HStack {
            ImageWithURL("https://i.redd.it/mn5m2km7hmv01.jpg")
        }
        .padding(.trailing, 10)
        .frame(minWidth: 128, maxWidth: .infinity, idealHeight: 128)
        .background(Color(.systemGray6))
        .cornerRadius(10)
    }
}

struct ImageCard_Previews: PreviewProvider {
    static var previews: some View {
        ImageCard()
    }
}
