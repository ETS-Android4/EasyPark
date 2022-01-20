//
//  ViewController.swift
//  test9
//
//  Created by Mariam  on 1/18/22.
//  Copyright © 2022 Mariamfarghaly.aucegypt. All rights reserved.
//

import UIKit

extension UIColor {
   convenience init(red: Int, green: Int, blue: Int) {
       assert(red >= 0 && red <= 255, "Invalid red component")
       assert(green >= 0 && green <= 255, "Invalid green component")
       assert(blue >= 0 && blue <= 255, "Invalid blue component")

       self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
   }

   convenience init(rgb: Int) {
       self.init(
           red: (rgb >> 16) & 0xFF,
           green: (rgb >> 8) & 0xFF,
           blue: rgb & 0xFF
       )
   }
}


class StatusViewController: UIViewController,UIPickerViewDelegate,UIPickerViewDataSource{
    var pid = 0
    var parking_id = 0
    var parking_status_perc = 0
    var currentStatus = ""
    var background_color: Int = 0
    var parking_name: String = ""
    @IBOutlet weak var pagetitle: UILabel!

    @IBOutlet weak var buttons: UIStackView!
    var pickerData: [String] = [String]()
    @IBOutlet weak var picker: UIPickerView!
    @IBOutlet weak var textView: UILabel!
    @IBOutlet weak var progressView: UIProgressView!
    @IBOutlet weak var reportBtn: UIButton!
    
    @IBOutlet weak var parkhere: UIButton!
    @IBAction func parkButton(_ sender: Any)  {
        //Connect to api
        
        if(parking_name == "Pepsi gate")
        {
            pid = 1
        }
        else if(parking_name == "Gardens gate")
        {
            pid = 6
        }
        else if(parking_name == "Watson Gate")
        {
            pid = 5
        }
        else if(parking_name == "Bus Gate(1)")
        {
            pid = 2
        }
        else if(parking_name == "Bus Gate(2)")
        {
            pid = 3
        }
        else
        {
            pid = 4
        }
        let Url = String(format: "http://localhost:3000/updateUserParking/900162227/"+String(pid))
            guard let serviceUrl = URL(string: Url) else { return }

            var request = URLRequest(url: serviceUrl)
            request.httpMethod = "POST"
            request.setValue("Application/json", forHTTPHeaderField: "Content-Type")
            request.timeoutInterval = 20
            let session = URLSession.shared
            session.dataTask(with: request) { (data, response, error) in
                if let response = response {
                    print(response)
                }
               
            }.resume()
        
            let Url2 = String(format:"http://localhost:3000/updateCurrentCount/1/"+String(pid))
            guard let serviceUrl = URL(string: Url2) else { return }

            var request2 = URLRequest(url: serviceUrl)
            request2.httpMethod = "POST"
            request2.setValue("Application/json", forHTTPHeaderField: "Content-Type")
            request2.timeoutInterval = 20
            let session2 = URLSession.shared
            session2.dataTask(with: request2) { (data, response, error) in
                if let response = response {
                    print(response)
                }
               
            }.resume()
        // create the alert
        let alert = UIAlertController(title: "Message", message: "Parking recorded successfully", preferredStyle: .alert)
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        // show the alert
        
        self.present(alert, animated: true, completion: nil)
    }
    
    @IBAction func reportMessage(_ sender: Any) {
        //Update status
        
        print("FERAS")
        print(parking_name)
        print(currentStatus)
        
        if(parking_name == "Pepsi gate")
        {
            parking_id = 1
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
        }
        else if(parking_name == "Gardens gate")
        {
            parking_id = 6
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
            
        }
        else if(parking_name == "Watson Gate")
        {
            parking_id = 5
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
        }
        else if(parking_name == "Bus Gate(1)")
        {
            parking_id = 2
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
        }
        else if(parking_name == "Bus Gate(2)")
        {
            parking_id = 3
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
        }
        else
        {
            parking_id = 4
            if(currentStatus == "Empty")
            {
                parking_status_perc = 0
            }
            else if(currentStatus == "Almost empty")
            {
                parking_status_perc = 25
            }
            else if(currentStatus == "Halfway filled")
            {
                parking_status_perc = 50
            }
            else if(currentStatus == "Almost filled")
            {
                parking_status_perc = 75
            }
            else{
                parking_status_perc = 100
            }
        }
        
        let Url = String(format: "http://localhost:3000/send_new_report/"+String(parking_id)+"/"+String(parking_status_perc))
            guard let serviceUrl = URL(string: Url) else { return }

            var request = URLRequest(url: serviceUrl)
            request.httpMethod = "POST"
            request.setValue("Application/json", forHTTPHeaderField: "Content-Type")
            request.timeoutInterval = 20
            let session = URLSession.shared
            session.dataTask(with: request) { (data, response, error) in
                if let response = response {
                    print(response)
                }
               
            }.resume()
        // create the alert
        let alert = UIAlertController(title: "Message", message: "Parking report status sent successfully", preferredStyle: .alert)
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        
        // show the alert
        self.present(alert, animated: true, completion: nil)
    }
    
    func numberOfComponents(in picker: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickerData.count
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        currentStatus = pickerData[row]
        return pickerData[row]
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if(pickerData[row]==""){
            parkhere.isEnabled = false
            reportBtn.isEnabled=false}
        else{
            parkhere.isEnabled = true
            reportBtn.isEnabled=true}
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Number of columns of data
     
        // Do any additional setup after loading the view, typically from a nib.
        let color_to_set = UIColor (rgb: background_color)
        pagetitle.text = parking_name
        self.view.backgroundColor = color_to_set
        print(parking_name)
        progressView.transform = progressView.transform.scaledBy(x: 1, y: 7)
        reportBtn.isEnabled=false
        parkhere.isEnabled = false
        pickerData = ["","Empty","Almost empty","Halfway filled","Almost filled","Completely full"]
        picker.dataSource = self
        picker.delegate = self
        //current count/total count
        progressView.setProgress(0.5,animated:false)
        //set status according to database
        textView.text = "The parking is halfway filled!"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
