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
//    @Published var images = Result<DrawImage>()
    
    private let repository: DrawADayRepositoryNative
    
    init(repository: DrawADayRepositoryNative) {
        self.repository = repository
    }
    
    func startObservingDrawImagesUpdates() {
        repository.startObservingDrawImagesUpdates(index: 1, refresh: false, success: { data in
//            self.images = data
        })
    }
    
    func stopObservingDrawImagesUpdates() {
        repository.stopObservingDrawImagesUpdates()
    }
}
