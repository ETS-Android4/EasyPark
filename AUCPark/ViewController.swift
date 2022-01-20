//
//  ViewController.swift
//  AUCPark
//
//  Created by Feras Awaga on 1/17/22.
//

import UIKit
import Foundation

class ParkViewController: UIViewController {
    var send_back_color: Int = 0
    var send_parking_name: String = ""
    
    var gardens = 0
    var watson = 0
    var busone = 0
    var bustwo = 0
    var omar = 0
    
    @IBOutlet weak var pepsiCount: UILabel!
    @IBOutlet weak var gardensCount: UILabel!
    @IBOutlet weak var watsonCount: UILabel!
    @IBOutlet weak var busOneCount: UILabel!
    @IBOutlet weak var busTwoCount: UILabel!
    @IBOutlet weak var omarCount: UILabel!
    @IBOutlet weak var checkout: UIButton!
    @IBOutlet weak var tabBar: UITabBar!
    @IBOutlet weak var verticalStack: UIStackView!
    @IBOutlet weak var button1: UIButton!
    @IBOutlet weak var spots1: UILabel!
    @IBOutlet weak var button2: UIButton!
    @IBOutlet weak var spots2: UILabel!
    @IBOutlet weak var button3: UIButton!
    @IBOutlet weak var spots3: UILabel!
    @IBOutlet weak var button4: UIButton!
    
    

    @IBAction func checkAction(_ sender: Any) {
        let alert = UIAlertController(title: "Message", message: "Checked-out from parking successfully", preferredStyle: .alert)
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
    
    
        // show the alert
        self.present(alert, animated: true, completion: nil)
    }
    @IBOutlet weak var imgg: UIImageView!
    @IBOutlet weak var spots4: UILabel!
    @IBOutlet weak var button5: UIButton!
    @IBOutlet weak var spots5: UILabel!
    @IBOutlet weak var spots6: UILabel!
    @IBOutlet weak var button6: UIButton!
    @IBOutlet weak var image2: UIImageView!
    @IBAction func pepsibutton(_ sender: Any) {
        send_back_color = 0x3597d8
        send_parking_name = "Pepsi Gate"
        
    }
    @IBAction func gardens_button(_ sender: Any) {
        send_back_color = 0x48d183
        send_parking_name = "Gardens Gate"
    }
    @IBAction func watson_button(_ sender: Any) {
        send_back_color = 0x48d183
        send_parking_name = "Watson Gate"
    }
    
    @IBAction func bus1_gate(_ sender: Any) {
        send_back_color = 0xe54f40
        send_parking_name = "Bus Gate(1)"
    }
    
    @IBAction func bus2_button(_ sender: Any) {
        send_back_color = 0xeabf10
        send_parking_name = "Bus Gate(2)"
    }
    
    @IBAction func omar_mohsen(_ sender: Any) {
        send_back_color = 0xe47f25
        send_parking_name = "Omar Mohsen"
    }
    
    override func prepare (for segue: UIStoryboardSegue, sender: Any?)
    {
        if (segue.destination is StatusViewController)
        {
            let vs = segue.destination as? StatusViewController
            print(send_parking_name)
            vs?.background_color = send_back_color
            vs?.parking_name = send_parking_name
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        imgg.image = UIImage(named: "auc")
        
        imgg.contentMode = .scaleAspectFit
        button1.layer.cornerRadius = 10
        button1.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]

        spots1.layer.masksToBounds = true
        spots1.layer.cornerRadius = 10
        spots1.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        
        
        
        button2.layer.cornerRadius = 10
        button2.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]
        spots2.layer.masksToBounds = true
        spots2.layer.cornerRadius = 10
        spots2.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        

        button3.layer.cornerRadius = 10
        button3.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]
        spots3.layer.masksToBounds = true
        spots3.layer.cornerRadius = 10
        spots3.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        
        button4.layer.cornerRadius = 10
        button4.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]
        spots4.layer.masksToBounds = true
        spots4.layer.cornerRadius = 10
        spots4.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        

        button5.layer.cornerRadius = 10
        button5.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]
        spots5.layer.masksToBounds = true
        spots5.layer.cornerRadius = 10
        spots5.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        

        button6.layer.cornerRadius = 10
        button6.layer.maskedCorners = [.layerMinXMinYCorner, .layerMinXMaxYCorner]

        spots6.layer.masksToBounds = true
        spots6.layer.cornerRadius = 10
        spots6.layer.maskedCorners = [.layerMaxXMaxYCorner, .layerMaxXMinYCorner]
        
                
              
                
  


        
        // Do any additional setup after loading the view.
    }
    



}

