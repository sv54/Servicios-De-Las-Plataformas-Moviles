//
//  ViewController.swift
//  P2_Maps_iOS
//
//  Created by Máster Móviles on 8/3/24.
//
import MapKit
import UIKit
import CoreLocation

class ViewController: UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {
    var locationManager = CLLocationManager()
    var numPin = 1
    @IBOutlet var mapView: MKMapView!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        super.viewDidLoad()
        mapView = MKMapView(frame:
                                CGRect(x: 0, y: 0,
                                       width: self.view.frame.width,
                                       height: self.view.frame.height))
        self.view.addSubview(mapView)
        mapView.delegate = self
        
        let alicanteLocation = CLLocationCoordinate2D(latitude: 38.3452, longitude: -0.4810)
        let regionRadius: CLLocationDistance = 5000 // Definir el radio de la región (en metros)
        let region = MKCoordinateRegion(center: alicanteLocation, latitudinalMeters: regionRadius, longitudinalMeters: regionRadius)
        mapView.setRegion(region, animated: true)
        
        
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
        
        mapView.showsUserLocation = true
        mapView.userTrackingMode = .follow
        
        let userLocationButton = MKUserTrackingBarButtonItem(mapView: mapView)
            navigationItem.leftBarButtonItem = userLocationButton
    }
    
    
    
    @IBAction func mapTypeChanged(_ sender: UISegmentedControl) {
        guard let mapView = mapView else {
            return
        }
        print("Segment selected: \(sender.selectedSegmentIndex)")

        switch sender.selectedSegmentIndex {
        case 0:
            mapView.mapType = .standard // Mapa
        case 1:
            mapView.mapType = .satellite // Satélite
        default:
            break
        }
    }
    
    @IBAction func addPin(_ sender: Any) {
        let centerCoordinate = mapView.centerCoordinate
        let pin = Pin(title: "Lugar Bonito", subtitle: "pos eso", coordinate: centerCoordinate, thumbImage: nil)
        if numPin % 2 == 0 {
            pin.thumbImage = UIImage(named: "alicante2.jpeg")
        } else {
            pin.thumbImage = UIImage(named: "alicante3.jpeg")
        }
        numPin = numPin + 1
        
        mapView.addAnnotation(pin)
    }

    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        guard let pin = annotation as? Pin else {
            return nil
        }
        if annotation is MKUserLocation {
            return nil
        }
        
        let identifier = "pin"
        var annotationView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier)
        
        if annotationView == nil {
            annotationView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
            annotationView?.canShowCallout = true
            annotationView?.rightCalloutAccessoryView = UIButton(type: .infoLight)
        } else {
            annotationView?.annotation = annotation
        }
        
        // Agregar la miniatura de la imagen al lado izquierdo del callout
        if let thumbImage = pin.thumbImage {
            let thumbnailImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 59, height: 59))
            thumbnailImageView.image = thumbImage
            annotationView?.leftCalloutAccessoryView = thumbnailImageView
        }
        
        return annotationView
    }

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else { return }
        
        // Imprimir la ubicación en la salida estándar cada 10 metros
        if location.distance(from: CLLocation(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)) > 10 {
            print("Ubicación actual: \(location.coordinate.latitude), \(location.coordinate.longitude)")
        }
    }

}

