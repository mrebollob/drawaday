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
        VStack{
            Text("Hello, world!")
                .padding()
            
            
            ImageCard()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
