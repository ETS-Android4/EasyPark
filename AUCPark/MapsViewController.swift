//
//  MapsViewController.swift
//  AUCPark
//
//  Created by Feras Awaga on 1/18/22.
//

import UIKit
import MapKit
import CoreLocation

class MapsViewController: UIViewController, CLLocationManagerDelegate {
    var locationManager = CLLocationManager()
    @IBOutlet weak var mapview: MKMapView!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.locationManager.requestWhenInUseAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
        }
        
        //30.0191035632511, 31.501614146027755
        let auc = MKPointAnnotation()
        auc.coordinate = CLLocationCoordinate2D(latitude: 30.0191035632511, longitude: 31.501614146027755)
        
        //30.0166705, 31.5016696)
        let gate4 = MKPointAnnotation()
        gate4.coordinate = CLLocationCoordinate2D(latitude: 30.0166705, longitude: 31.5016696)
        mapview.addAnnotation(gate4)
        
        //30.0220150, 31.4971187
        let gate5 = MKPointAnnotation()
        gate5.coordinate = CLLocationCoordinate2D(latitude: 30.0220150, longitude: 31.4971187)
        mapview.addAnnotation(gate5)
        
        //30.0176783, 31.4997065
        let busgate = MKPointAnnotation()
        busgate.coordinate = CLLocationCoordinate2D(latitude: 30.0176783, longitude: 31.4997065)
        mapview.addAnnotation(busgate)
        
        //30.0231389, 31.5011342
        let watson = MKPointAnnotation()
        watson.coordinate = CLLocationCoordinate2D(latitude: 30.0231389, longitude: 31.5011342)
        mapview.addAnnotation(watson)
        
        //30.0218412, 31.5011342
        let gardens = MKPointAnnotation()
        gardens.coordinate = CLLocationCoordinate2D(latitude: 30.0218412, longitude: 31.5011342)
        mapview.addAnnotation(gardens)
        
        let region = MKCoordinateRegion(center: auc.coordinate,latitudinalMeters: 1200, longitudinalMeters: 1200)
       mapview.setRegion(region, animated: true)

        // Do any additional setup after loading the view.
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

//    @IBOutlet weak var coord1: UILabel!
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        let locValue:CLLocationCoordinate2D = manager.location!.coordinate
        print("locations = \(locValue.latitude) \(locValue.longitude)")
        let userLocation = locations.last
        let viewRegion = MKCoordinateRegion(center: (userLocation?.coordinate)!, latitudinalMeters: 600, longitudinalMeters: 600)
//        print("locations = \(locValue.latitude) \(locValue.longitude)")
//        coord1.text = "locations = \(locValue.latitude) \(locValue.longitude)"
//        coord1.text = "THIS IS MY LOCATION"
        //self.mapview.setRegion(viewRegion, animated: true)
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
