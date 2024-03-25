//
//  DetalleController.swift
//  P2_Maps_iOS
//
//  Created by Máster Móviles on 14/3/24.
//

import Foundation
import UIKit
import MapKit

class DetalleController: UIViewController{
    
    
    @IBOutlet weak var imagen: UIImageView!
    var imageName: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        if let image = UIImage(named: imageName!) {
            imagen.image = image
        
        } else {
            print("No se ha recibido ningún nombre de imagen")
        }
    }
    

    
}
