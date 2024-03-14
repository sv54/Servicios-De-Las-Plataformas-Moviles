//
//  Pin.swift
//  P2_Maps_iOS
//
//  Created by Máster Móviles on 8/3/24.
//

import Foundation
import MapKit

class Pin: NSObject, MKAnnotation {
    var coordinate: CLLocationCoordinate2D
    var title: String?
    var subtitle: String?
    var thumbImage: UIImage?
    var imageTitulo: String?

    init(title: String?, subtitle: String?, coordinate: CLLocationCoordinate2D, thumbImage: UIImage?) {
        self.title = title
        self.subtitle = subtitle
        self.coordinate = coordinate
        self.thumbImage = thumbImage

        super.init()
    }
}
