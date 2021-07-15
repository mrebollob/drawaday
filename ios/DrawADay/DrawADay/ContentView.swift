//
//  ContentView.swift
//  DrawADay
//
//  Created by Manuel Baez on 20/06/2021.
//

import SwiftUI
import shared

struct ContentView: View {
    
    @StateObject var viewModel = FeedViewModel(repository: DrawADayRepositoryNativeImp())
    
    var body: some View {
        FeedView(viewModel: viewModel)
    }
}

struct FeedView: View {
    @ObservedObject var viewModel: FeedViewModel
    
    var body: some View {
        NavigationView {
            List(viewModel.images, id: \.id) { drawing in
                DrawingView(viewModel: viewModel, drawing: drawing)
            }
            .navigationBarTitle(Text("Draw a day"))
            .onAppear {
                viewModel.startObservingDrawImagesUpdates()
            }.onDisappear {
                viewModel.stopObservingDrawImagesUpdates()
            }
        }
    }
}

struct DrawingView: View {
    var viewModel: FeedViewModel
    var drawing: DrawImage
    
    var body: some View {
        ImageCard()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
