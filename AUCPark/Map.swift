//
//  Map.swift
//  AUCPark
//
//  Created by Feras Awaga on 1/18/22.
//

import Foundation

import UIKit
import MapKit

class ViewController: UIViewController {

 
    @IBOutlet weak var map: MKMapView!
 
    override func viewDidLoad() {
        super.viewDidLoad()
      
        
        let gate4 = MKPointAnnotation()
        gate4.coordinate = CLLocationCoordinate2D(latitude: 30.0166705, longitude: 31.5016696)
        map.addAnnotation(gate4)
        
        let gate5 = MKPointAnnotation()
        gate5.coordinate = CLLocationCoordinate2D(latitude:  30.0220150, longitude: 31.4971187)
        map.addAnnotation(gate5)

        let busgate = MKPointAnnotation()
        busgate.coordinate = CLLocationCoordinate2D(latitude: 30.0176783, longitude: 31.4997065)
        map.addAnnotation(busgate)

        let watson = MKPointAnnotation()
        watson.coordinate = CLLocationCoordinate2D(latitude: 30.0231389, longitude: 31.5011342)
        map.addAnnotation(watson)

        let gardens = MKPointAnnotation()
        gardens.coordinate = CLLocationCoordinate2D(latitude: 30.0218412, longitude: 31.5011342)
        map.addAnnotation(gardens)

        
        
        
        // Do any additional setup after loading the view.
    }


}

