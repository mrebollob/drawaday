//
//  FeedViewModel.swift
//  DrawADay
//
//  Created by Manuel Baez on 20/06/2021.
//

import Foundation
import Combine
import shared

class FeedViewModel: ObservableObject {
    @Published var isLoading = true
    @Published var hasError = false
    @Published var images = [DrawImage]()
    
    private let repository: DrawADayRepositoryNative
    
    init(repository: DrawADayRepositoryNative) {
        self.repository = repository
    }
    
    func startObservingDrawImagesUpdates() {
        repository.startObservingDrawImagesUpdates(index: 1, refresh: false, success: { result in


//            if let images = (result as? ResultSuccess)?.data as? [DrawImage] {
//                self.images = images
//                self.hasError = false
//                self.isLoading = false
//
//            } else if let images = (result as? ResultLoading)?.data as? [DrawImage] {
//                self.images = images
//                self.hasError = false
//                self.isLoading = true
//
//            } else if result is ResultError {
//                self.hasError = true
//                self.isLoading = false
//            }
        })
    }
    
    func stopObservingDrawImagesUpdates() {
        repository.stopObservingDrawImagesUpdates()
    }
}
