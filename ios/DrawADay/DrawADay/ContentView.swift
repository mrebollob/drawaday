//
//  ContentView.swift
//  DrawADay
//
//  Created by Manuel Baez on 20/06/2021.
//

import SwiftUI
import shared

struct ContentView: View {
    
    
    
    var body: some View {
        Text("Hello, world!")
            .padding()
    }
}

struct PersonView: View {
    var person: DrawImage
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("Hola").font(.headline)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
